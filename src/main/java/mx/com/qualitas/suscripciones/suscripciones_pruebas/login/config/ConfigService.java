package mx.com.qualitas.suscripciones.suscripciones_pruebas.login.config;

import lombok.RequiredArgsConstructor;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.handler.exception.SecurityKeyNotFoundException;
import mx.com.qualitas.suscripciones.suscripciones_pruebas.login.repository.CconfigRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigService {

    private final CconfigRepository cconfigRepository;
    private String cachedSecurityKey;

    /**
     * Retorna la clave de seguridad desde caché o BD.
     * el caché no se invalida automáticamente; se requiere reinicio para reflejar cambios en BD.
     * No entiendó del bien como funciona lo del caché pero funciona xd.
     */
    public String getSecurityKey() {
        if (cachedSecurityKey == null) {
            synchronized (this) {
                if (cachedSecurityKey == null) {
                    cachedSecurityKey = cconfigRepository.findByClave("SECURITY_KEY")
                            .orElseThrow(() -> new SecurityKeyNotFoundException(
                                    "No se encontró la clave"))
                            .getDescripcion();
                }
            }
        }
        return cachedSecurityKey;
    }
}

