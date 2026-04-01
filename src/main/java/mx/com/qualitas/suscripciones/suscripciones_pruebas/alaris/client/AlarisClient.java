package mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.client;

import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.request.ClassifyRequest;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.response.ClassifyResponse;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.alaris.dto.response.FileUploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "alarisClient", url = "${host.alaris}")
public interface AlarisClient {

    @GetMapping("/server/rest/v2.0/restservices/session")
    String createSession(@RequestParam("apiKey") String apiKey, @RequestParam(value = "batchCreation", required = false) Boolean batchCreation);

    @PostMapping(value = "/server/rest/v2.0/restservices/files", consumes = "application/octet-stream")
    FileUploadResponse uploadFile(@RequestParam("sessionId") String sessionId, @RequestBody byte[] fileContent);

    // classifyPages,  classifyAndIdentify
    @PostMapping("/server/rest/v2.0/restservices/classifyAndIdentify/job/{jobId}/step/{stepName}")
    ClassifyResponse classify(@PathVariable("jobId") Long jobId, @PathVariable("stepName") String stepName,
                              @RequestParam("sessionId") String sessionId, @RequestBody ClassifyRequest request);

    @DeleteMapping("/server/rest/v2.0/restservices/session")
    void closeSession(@RequestParam("sessionId") String sessionId);
}