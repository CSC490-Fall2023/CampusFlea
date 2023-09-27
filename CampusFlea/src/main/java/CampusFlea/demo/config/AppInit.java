package CampusFlea.demo.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected Class<?>[] getRootConfigClasses(){
        //todo
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        Class[] configFiles = {AppConfig.class}; //allows multiple configs to be initialized
        return configFiles;
    }

    @Override
    protected String[] getServletMappings() {
        String[] mappings = {"/"}; //allows multiple mappings to be placed into string array
        return mappings;
    }
}
