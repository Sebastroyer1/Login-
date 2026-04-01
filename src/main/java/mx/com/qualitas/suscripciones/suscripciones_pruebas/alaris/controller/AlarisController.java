package mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.controller;

import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.service.impl.AlarisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/suscripciones/alaris")
public class AlarisController {
    private static final Logger logger = LoggerFactory.getLogger(AlarisController.class);

    private final AlarisServiceImpl alarisService;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR')")
    public ResponseEntity<?> uploadPdf(@RequestParam("files") List<MultipartFile> files) {
        logger.info("----> Iniciando carga de archivo(s)");

        java.util.Map<String, Object> response = alarisService.uploadFiles(files);
        return ResponseEntity.ok(response);
    }

}
