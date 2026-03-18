package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.dto.response.CustomException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException ex) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        CustomException error = new CustomException();
        error.setCode("UNAUTHORIZED");
        error.setMessage("Debes autenticarte para acceder a este recurso");

        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }
}
