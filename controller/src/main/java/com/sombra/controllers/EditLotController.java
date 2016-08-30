package com.sombra.controllers;

import com.sombra.model.Image;
import com.sombra.model.Lot;
import com.sombra.services.*;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * Created by PasichnykMV on 24.08.2016.
 */
@WebServlet(urlPatterns = "/edit")
@MultipartConfig
public class EditLotController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(EditLotController.class.getName());

    private CategoryService categoryService;
    private LotService lotService;
    private CityService cityService;
    private ImageService imageService;

    public void init() throws ServletException {
        this.categoryService = new JdbcCategoryService();
        this.lotService = new JdbcLotService();
        this.cityService = new JdbcCityService();
        this.imageService = new JdbcImageService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Entering to doGet method");
        Integer lotId = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("lot", lotService.getById(lotId));
        request.setAttribute("categories", categoryService.getAllWithSubcategories());
        request.setAttribute("cities", cityService.getAll());
        request.getRequestDispatcher("/views/admin/editLot.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Entering to doPost method");

        Optional<Integer> lotId = Optional.ofNullable(Integer.valueOf(request.getParameter("lotId")));
        if (lotId.isPresent() && lotId.get() != null) {
            Optional<String> action = Optional.ofNullable(request.getParameter("type"));
            if (action.isPresent()) {
                LOGGER.info(action.get());
                if (action.get().equals("cover")) {
                    LOGGER.info("Start cover updating");
                    Integer imageId = Integer.parseInt(request.getParameter("coverId"));
                    updateFiles(request, imageId);
                    LOGGER.info("Finish cover updating");
                } else if (action.get().equals("newcover")) {
                    LOGGER.info("Start file uploading");
                    imageService.addLotImages(lotId.get(), parseFiles(request, true).get(0).getId());
                    LOGGER.info("Finish file uploading");
                } else if (action.get().equals("addimage")) {
                    LOGGER.info("Start file uploading");
                    List<Image> list = parseFiles(request, false);
                    for (Image image : list ) {
                        imageService.addLotImages(lotId.get(), image.getId());
                        LOGGER.info("File added - " + image.getName());
                    }
                    LOGGER.info("Finish file uploading");
                } else if (action.get().equals("deleteimage")) {
                    LOGGER.info("Start file uploading");
                    Integer imageId = Integer.parseInt(request.getParameter("imageId"));
                    imageService.delete(imageId);
                    LOGGER.info("Finish file uploading");
                } else if (action.get().equals("editlot")) {
                    lotService.update(parseLot(request, lotId.get()));
                }
            }
        }

        LOGGER.info("Finish to doPost method");
        response.sendRedirect(request.getContextPath()+"/edit?id="+lotId.get().toString());
    }

    public void updateFiles(HttpServletRequest request, Integer imageId)
            throws IOException, ServletException {
        LOGGER.info("Start file parsing");
        for (Part filePart : request.getParts()) {
            String fileName = filePart.getSubmittedFileName();
            LOGGER.info("Start file parsing" + fileName);
            if (fileName != null) {
                Image file = imageService.getById(imageId);
                file.setName(fileName);
                int count = 0;
                byte[] buf = new byte[1024*1024*100];
                BufferedInputStream is = new BufferedInputStream(filePart.getInputStream());
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                while((count = is.read(buf)) != -1) {
                    bs.write(buf, 0, count);
                }

                file.setFile(Base64.getEncoder().encodeToString(bs.toByteArray()));
                imageService.update(file);
            }
        }
    }

    public List<Image> parseFiles(HttpServletRequest request, Boolean is_cover) throws IOException, ServletException {
        LOGGER.info("Start file parsing");
        List<Image> files = new ArrayList<>();
        for (Part filePart : request.getParts()) {
            String fileName = filePart.getSubmittedFileName();
            LOGGER.info("Start file parsing" + fileName);
            if (fileName != null) {
                Image file = new Image();
                file.setName(fileName);
                int count = 0;
                byte[] buf = new byte[1024*1024*100];
                BufferedInputStream is = new BufferedInputStream(filePart.getInputStream());
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                while((count = is.read(buf)) != -1) {
                    bs.write(buf, 0, count);
                }

                file.setFile(Base64.getEncoder().encodeToString(bs.toByteArray()));
                file.setIs_cover(is_cover);
                file.setId(imageService.save(file).getId());
                files.add(file);
                LOGGER.info("Finish file parsing fileename="+file.getName() + " id="+file.getId());
            }
        }
        return files;
    }

    public Lot parseLot(HttpServletRequest request, Integer lotId) {
        Lot lot = lotService.getById(lotId);
        lot.setTitle(request.getParameter("title"));
        lot.setPrice(Double.parseDouble(request.getParameter("price")));
        lot.setCategory(categoryService.getById(Integer.parseInt(request.getParameter("categoryId"))));
        lot.setCity(cityService.getById(Integer.parseInt(request.getParameter("cityId"))));
        lot.setDescription(request.getParameter("description"));
        return lot;
    }
}
