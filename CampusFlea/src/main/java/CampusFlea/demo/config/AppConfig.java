package CampusFlea.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

import javax.swing.*;

@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setPrefix("src/main/webapp/WEB-INF/view");
//        templateResolver.setSuffix(".html");
//
//        return templateResolver;
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        registry.addResourceHandler("/URLtoReachResources/**")
//                .addResourceLocations("/resources/");
//    }
}
