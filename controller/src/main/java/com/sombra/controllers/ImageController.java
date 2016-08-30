package com.sombra.controllers;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PasichnykMV on 23.08.2016.
 */
@WebServlet(urlPatterns = "/image")
@MultipartConfig
public class ImageController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(ImageController.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Start doPost in ImageController");
        parseFiles(request);
        LOGGER.info("Finish doPost in ImageController");
    }

    public List<String> parseFiles(HttpServletRequest request) throws IOException, ServletException {
        List<String> files = new ArrayList<>();
        for (Part filePart : request.getParts()) {
            String fileName = filePart.getSubmittedFileName();
            String path = getServletContext().getRealPath("") +"\\"+fileName;
            if (fileName != null) {
                filePart.write(path);
                }

                files.add(path);
            }

        return files;
    }

}
