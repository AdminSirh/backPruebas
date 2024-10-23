package com.backB.backB;

import javax.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@ComponentScan("com.backB")
@SpringBootApplication
@EnableJpaRepositories
@EnableAutoConfiguration
@EnableScheduling
public class BackbApplication extends SpringBootServletInitializer{

    @PostConstruct
    void init()  {
    }

    public static void main(String[] args) {
        SpringApplication.run(BackbApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
   
    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
        // Redirigir todas las rutas no manejadas a index.html
        return "forward:/index.html";
    }
}
