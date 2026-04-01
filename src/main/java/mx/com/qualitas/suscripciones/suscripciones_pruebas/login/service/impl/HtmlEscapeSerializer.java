package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.service.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.owasp.encoder.Encode;

import java.io.IOException;

public class HtmlEscapeSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(Encode.forHtml(value));
        }
    }
}
