package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins:http://localhost:8080}")
    private String[] allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes específicos desde configuración
        // probablemente se cambie xd
        for (String origin : allowedOrigins) {
            config.addAllowedOrigin(origin);
        }
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");

        // Habilitar credenciales si se necesitan cookies o auth headers
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
