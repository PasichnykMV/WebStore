package com.sombra.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sombra.jdbc.JdbcDaoFactory;
import com.sombra.model.Lot;
import com.sombra.model.User;
import com.sombra.services.JdbcLotService;
import com.sombra.services.JdbcUserService;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by PasichnykMV on 18.08.2016.
 */
@WebServlet(urlPatterns = {"/payment"})
public class PaymentController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(PaymentController.class.getName());

    private JdbcLotService lotService;
    private JdbcUserService userService;

    public void init() throws ServletException {
        this.lotService = new JdbcLotService();
        this.userService = new JdbcUserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/payment.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Payment post method activated!");
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        List<Lot> lotsToOrder = lotService.getAllLotsInUserCart(userId);
        for (Lot lot : lotsToOrder){
            lotService.addLotToUsersOrder(userId, lot.getId());
        }
        LOGGER.info("All lot added from cart to order history");
        lotService.deleteAllFromCart(userId);
        LOGGER.info("cart cleared!");
        String cardNumber = request.getParameter("number");
        String date = String.valueOf(new Date().getTime());
        EmailService.getInstance().sendMessage ("purchase", "Successful transaction!", userService.getById(userId)
        , makePaymentCheck(lotsToOrder, userId, cardNumber, date));
        LOGGER.info("Check successfuly send to user email");
        request.getSession().setAttribute("lotsToOrder",lotsToOrder);
        request.getSession().setAttribute("date",date);
        response.sendRedirect("/check");
    }


    private String makePaymentCheck(List<Lot> lots, Integer userId, String number, String date){
        LOGGER.info("Check completing started...");
        User user = userService.getById(userId);
        Double sum = 0.0;
        String relativePath = getServletContext().getRealPath("");
        String filePath = relativePath + "\\Check"+userId.toString()
                +"_date-"+date+".pdf";
        try{
            OutputStream file = new FileOutputStream(new File(filePath));
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            StringBuilder htmlString = new StringBuilder();
            htmlString.append("<html><body> <h1 style=").append("'text-align:center;'")
                    .append("> Payment check ").append(new Date()).append(" </h1><table border='2' align='center'> ")
                    .append("<tr><td>").append(user.getName()).append("</td><td>")
                    .append(user.getLastName()).append("</td></tr>")
                    .append("<tr> <td>Lot</td> <td>Price</td> </tr>");
            for (Lot lot : lots){
                htmlString.append("<tr> <td>").append(lot.getTitle()).append(" </td> <td>")
                          .append(lot.getPrice()).append("</td> </tr>");
                sum += lot.getPrice();
            }
            htmlString.append("<tr> <td></td> <td></td> </tr>")
                      .append("<tr> <td>Total</td> <td>").append(sum)
                      .append("</td> </tr></table>").append("<br /> Payed with card:").append(number.substring(0,4))
                      .append(" **** **** ").append(number.substring(15, 19))
                      .append("</body></html>");

            document.open();
            InputStream is = new ByteArrayInputStream(htmlString.toString().getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
            document.close();
            file.close();
            LOGGER.info("Check successfuly completed");
        } catch (Exception e)
        {
            LOGGER.error("Exception in class " + this.getClass().getName(), e);
        }
        return filePath;
    }

}
