package com.imagegallery.util;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletRegistration;

public class MainWebAppInitializer implements WebApplicationInitializer {

    private String TMP_FOLDER = "/tmp";
    private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

    @Override
    public void onStartup(jakarta.servlet.ServletContext sc) throws jakarta.servlet.ServletException {

        ServletRegistration.Dynamic appServlet = sc.addServlet("mvc", new DispatcherServlet(
            new GenericWebApplicationContext()));

    appServlet.setLoadOnStartup(1);
    MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TMP_FOLDER,
            MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);

    appServlet.setMultipartConfig(multipartConfigElement);

    }
}
