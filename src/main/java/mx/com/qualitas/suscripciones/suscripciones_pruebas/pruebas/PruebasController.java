package mx.com.qualitas.suscripciones.suscripciones_pruebas.pruebas;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.controller.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/prueba")
public class PruebasController {

    private static final Logger logger = LoggerFactory.getLogger(PruebasController.class);

    @PostMapping("/upload")
    public ResponseEntity<ResponsePruebaDto> uploadPdf(@RequestParam("file") MultipartFile file) {

        logger.info("----> Iniciando carga de archivo(s)");
        if (file.isEmpty()) {
            ResponsePruebaDto response = new ResponsePruebaDto();
            response.setCode("400");
            response.setMessage("Archivo no encontrado");
            return ResponseEntity.badRequest().body(response);
        }
        try {

            byte[] bytes = file.getBytes();
            System.out.println("Bytes recibidos: " + bytes.length);

            ResponsePruebaDto response = new ResponsePruebaDto();
            response.setCode("200");
            response.setMessage("Archivo recibido correctamente");
            response.setNombre(file.getOriginalFilename());
            response.setTipo(file.getContentType());
            response.setPeso(String.valueOf(file.getSize()));
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}