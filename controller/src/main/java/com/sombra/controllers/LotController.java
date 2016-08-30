package com.sombra.controllers;

import com.sombra.jdbc.JdbcDaoFactory;
import com.sombra.model.Category;
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
import java.util.*;

/**
 * Created by PasichnykMV on 18.08.2016.
 */
@WebServlet(urlPatterns = {"/lot"})
@MultipartConfig
public class LotController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LotController.class.getName());

    private LotService lotService;
    private UserService userService;
    private CategoryService categoryService;
    private CityService cityService;
    private ImageService imageService;

    public void init() throws ServletException {
        this.lotService = new JdbcLotService();
        this.categoryService = new JdbcCategoryService();
        this.userService = new JdbcUserService();
        this.cityService = new JdbcCityService();
        this.imageService = new JdbcImageService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Lot lot = new Lot();
        Optional<Integer> lotId = Optional.ofNullable(Integer.parseInt(request.getParameter("id")));
        if (lotId.isPresent()) {
            lot = JdbcDaoFactory.getInstance().getLotDao().getById(lotId.get());
            request.setAttribute("lot", lot);
        }
        completeCategoryTree(lot, request);
        getLotsInCartCount(request);
        LOGGER.info("Passed lot object to lot.jsp, lot.title= " + lot.getTitle());
        request.getRequestDispatcher("/views/lot.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Lot lot;
        Optional<String> action = Optional.ofNullable(request.getParameter("type"));

        if (action.isPresent() && !action.get().equals("")) {
            LOGGER.info(action.get());
            if (action.get().equals("create")){
                Optional<String> edit = Optional.ofNullable(request.getParameter("edit"));
                if (edit.isPresent()){
                    LOGGER.info("Editing just created lot");
                    lot = parseLot(request, "edit");
                    lotService.update(lot);
                    LOGGER.info("Lot updated!");
                } else {
                    lot = lotService.save(parseLot(request, action.get()));
                    request.getSession().setAttribute("justSavedLotId", lot.getId());
                }
            } else if (action.get().equals("update")){
                lotService.update(parseLot(request, action.get()));
                LOGGER.info("Lot updated");
            } else if (action.get().equals("cover")) {
                LOGGER.info("Start cover uploading");
                Integer justSavedLotId = (Integer) request.getSession().getAttribute("justSavedLotId");
                imageService.addLotImages(justSavedLotId, parseFiles(request, true).get(0).getId());
                LOGGER.info("Finish cover uploading");
            } else if (action.get().equals("images")){
                LOGGER.info("Start files uploading");
                Integer justSavedLotId = (Integer) request.getSession().getAttribute("justSavedLotId");
                List<Image> list = parseFiles(request, false);
                for (Image image : list ) {
                    imageService.addLotImages(justSavedLotId, image.getId());
                    LOGGER.info("File added - " + image.getName());
                }
                LOGGER.info("Finish files uploading");
            }
        }
    }


    private Lot parseLot(HttpServletRequest request, String action) throws IOException, ServletException {
        LOGGER.info("Start parseLot method");
        Lot lot = null;
        if (action.equals("create")){
            lot = new Lot();
            lot.setTitle(request.getParameter("title"));
            lot.setPrice(Double.parseDouble(request.getParameter("price")));
            lot.setDescription(request.getParameter("description"));

            Integer categoryId = Integer.valueOf(request.getParameter("categoryId"));
            lot.setCategory(categoryService.getById(categoryId));

            Integer cityId = Integer.valueOf(request.getParameter("cityId"));
            lot.setCity(cityService.getById(cityId));

            lot.setCreationDate(new Date());
            LOGGER.info("Lot "+lot.getTitle()+" successfully parsed");
        } else if (action.equals("edit")){
            Integer justSavedLotId = (Integer) request.getSession().getAttribute("justSavedLotId");
            lot = lotService.getById(justSavedLotId);
            lot.setTitle(request.getParameter("title"));
            lot.setPrice(Double.parseDouble(request.getParameter("price")));
            lot.setDescription(request.getParameter("description"));

            Integer categoryId = Integer.valueOf(request.getParameter("categoryId"));
            lot.setCategory(categoryService.getById(categoryId));

            Integer cityId = Integer.valueOf(request.getParameter("cityId"));
            lot.setCity(cityService.getById(cityId));

            LOGGER.info("Edited Lot successfully parsed");
        }
        return lot;
    }

    private void completeCategoryTree(Lot lot, HttpServletRequest request){
        Optional<Integer>  topCategoryId = Optional.ofNullable(lot.getCategory().getParentId());
        if (topCategoryId.isPresent() && topCategoryId.get() != 0) {
            Category category = categoryService.getById(topCategoryId.get());
            request.setAttribute("topCategory", category);
            if (category.getParentId() != 0 || category.getParentId() != null){
                Optional<Integer> topestCategoryId = Optional.ofNullable(category.getParentId());
                if (topestCategoryId.isPresent() && topestCategoryId.get() != 0) {
                    Category topestCategory = categoryService.getById(topestCategoryId.get());
                    request.setAttribute("topestCategory", topestCategory);
                }
            }
        }
    }

    private void getLotsInCartCount(HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId != null) {
            Integer lotsCount = userService.getLotsIncartCount(userId);
            request.setAttribute("lotsCount", lotsCount);
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

}
