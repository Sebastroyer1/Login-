package mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AlarisService {

    Map<String, Object> uploadFiles(List<MultipartFile> files);
}
