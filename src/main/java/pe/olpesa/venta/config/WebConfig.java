/* package pe.olpesa.venta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/");
        
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/");
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/");
        
        registry.addResourceHandler("/estilo.css")
                .addResourceLocations("classpath:/static/");
    }
} */