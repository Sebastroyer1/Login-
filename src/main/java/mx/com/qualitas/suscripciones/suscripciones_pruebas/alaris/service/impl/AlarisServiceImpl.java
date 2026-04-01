package mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.service.impl;

import feign.FeignException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.client.AlarisClient;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.request.ClassifyRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.request.ResourceRef;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.response.ClassifyResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.response.FileUploadResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.response.PageInfoResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.exception.ClassificationException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.exception.FileProcessingException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.exception.SessionException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.service.AlarisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlarisServiceImpl implements AlarisService {
    private static final Logger logger = LoggerFactory.getLogger(AlarisServiceImpl.class);

    private final AlarisClient alarisClient;

    @Value("${API-Key}")
    private String apiKey;

    @Value("${Job-id}")
    private Long jobId;

    @Value("${Step-name}")
    private String stepName;

    @Override
    public Map<String, Object> uploadFiles(List<MultipartFile> files) {
        logger.info("----> Iniciando proceso de clasificación con Alaris");
        String sessionId = crearSesion();

        try {
            Map<String, String> refToOriginal = new HashMap<>();
            List<ResourceRef> resourceRefs = subirArchivos(files, sessionId, refToOriginal);
            ClassifyResponse classifyResponse = clasificar(sessionId, resourceRefs);
            return construirRespuesta(files.size(), resourceRefs, refToOriginal, classifyResponse);
        } finally {
            // Siempre se cierra la sesion falle o nel
            cerrarSesion(sessionId);
        }
    }

    private String crearSesion() {
        try {
            String sessionId = alarisClient.createSession(apiKey, null); // crear sesion con Alaris
            logger.info("----> Sesión creada: {}", sessionId);
            return sessionId;
        } catch (RetryableException e) {
            logger.error("----> Timeout al crear sesión: {}", e.getMessage());
            throw new SessionException("No se pudo conectar con Alaris: " + e.getMessage());
        } catch (FeignException e) {
            logger.error("----> Error HTTP al crear sesión: {}", e.getMessage());
            throw new SessionException("Error al crear sesión con Alaris: " + e.getMessage());
        }
    }

    private List<ResourceRef> subirArchivos(List<MultipartFile> files, String sessionId, Map<String, String> refToOriginal) {

        logger.info("----> Subiendo {} archivos", files.size());
        List<ResourceRef> resourceRefs = new ArrayList<>();

        for (MultipartFile file : files) { // iterar sobre los archivos
            try {
                byte[] fileBytes = file.getBytes(); // obtener bytes del archivo
                FileUploadResponse upload = alarisClient.uploadFile(sessionId, fileBytes); // subir al servidor de Alaris
                String ref = upload.getRef(); // obtener la ref del archivo
                resourceRefs.add(new ResourceRef(ref)); // agregar la ref al listado
                refToOriginal.put(ref, file.getOriginalFilename());// agregar la ref al mapa
                logger.info("----> Archivo subido: {} --> ref: {}", file.getOriginalFilename(), ref);
            } catch (IOException e) {
                logger.error("----> Error al leer archivo: {}", file.getOriginalFilename(), e);
                throw new FileProcessingException("Error al procesar archivo: " + file.getOriginalFilename());
            } catch (RetryableException e) {
                logger.error("----> Timeout al subir archivo: {}", file.getOriginalFilename(), e);
                throw new FileProcessingException("Timeout al subir archivo: " + file.getOriginalFilename());
            } catch (FeignException e) {
                logger.error("----> Error HTTP al subir archivo: {}", file.getOriginalFilename(), e);
                throw new FileProcessingException("Error al subir archivo a Alaris: " + file.getOriginalFilename());
            }
        }
        return resourceRefs;
    }

    private ClassifyResponse clasificar(String sessionId, List<ResourceRef> resourceRefs) {
        try {
            ClassifyRequest request = new ClassifyRequest();
            request.setResourceRefs(resourceRefs); // agregar las refs al request
            ClassifyResponse response = alarisClient.classify(jobId, stepName, sessionId, request); // enviar al servidor de Alaris
            logger.info("----> Clasificación recibida");
            return response;
        } catch (RetryableException e) {
            logger.error("----> Timeout en clasificación: {}", e.getMessage());
            throw new ClassificationException("Timeout en clasificación de documentos: " + e.getMessage());
        } catch (FeignException e) {
            logger.error("----> Error HTTP en clasificación: {}", e.getMessage());
            throw new ClassificationException("Error en clasificación de documentos: " + e.getMessage());
        }
    }

    private void cerrarSesion(String sessionId) {
        try {
            alarisClient.closeSession(sessionId);
            logger.info("----> Sesión cerrada");
        } catch (RetryableException e) {
            logger.warn("----> Timeout al cerrar sesión: {}", e.getMessage());
        } catch (FeignException e) {
            logger.warn("----> Advertencia al cerrar sesión: {}", e.getMessage());
        }
    }

    private Map<String, Object> construirRespuesta(int totalArchivos, List<ResourceRef> resourceRefs, Map<String, String> refToOriginal, ClassifyResponse classifyResponse) {

        List<Map<String, String>> resultados = new ArrayList<>();
        List<PageInfoResponse> pages = classifyResponse.getPages();

        for (int i = 0; i < resourceRefs.size(); i++) {
            ResourceRef ref = resourceRefs.get(i);
            PageInfoResponse page = pages.get(i);
            Map<String, String> item = new HashMap<>();
            item.put("nombre", refToOriginal.getOrDefault(ref.getRef(), "desconocido"));
            item.put("tipoDocumento", page.getType() != null ? page.getType() : "No identificado");
            item.put("matched", String.valueOf(page.isMatched()));
            item.put("confianza", String.format("%.2f", page.getConfidence()));
            resultados.add(item);
        }


        Map<String, Object> response = new HashMap<>();
        response.put("code", "200");
        response.put("Total de Archivos", totalArchivos);
        response.put("resultados", resultados);
        return response;
    }
}