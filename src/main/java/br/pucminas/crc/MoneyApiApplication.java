package br.pucminas.crc;

import br.pucminas.crc.config.property.AlgamoneyApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties(AlgamoneyApiProperty.class)
public class MoneyApiApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(MoneyApiApplication.class, args);
	}// end main()
}// end class
