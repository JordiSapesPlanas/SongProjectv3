package com.udl.softarch.springexample.init;

import javax.servlet.*;
import javax.servlet.ServletRegistration.Dynamic;

import com.udl.softarch.springexample.config.SongsAppContext;
import com.udl.softarch.springexample.filters.SimpleCORSFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.EnumSet;

public class Initializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SongsAppContext.class);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        servletContext.addFilter("CORSFilter", new SimpleCORSFilter()).addMappingForUrlPatterns(null, false, "/*");
        servletContext.addFilter("httpMethodFilter", new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null, false, "/*");
        servletContext.addListener(new ContextLoaderListener(rootContext));
    }

}
