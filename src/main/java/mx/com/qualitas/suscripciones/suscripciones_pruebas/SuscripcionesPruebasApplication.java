package mx.com.qualitas.suscripciones.suscripciones_pruebas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SuscripcionesPruebasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuscripcionesPruebasApplication.class, args);
	}

}
