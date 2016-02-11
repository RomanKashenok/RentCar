package com.kashenok.rentcar.controller;

import com.kashenok.rentcar.resource.ConfigurationManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * The Class FileUploadServlet. Receive requests on /upload direction, causes
 * the needed action and forwards to the result page. It serves only to upload
 * images.
 */
@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 665474555633227108L;
    private static final String UPLOAD_PATH = "C:/projects/RentCar/web/img/cars";
    private static final String FILE = "file";
    private static final String FILE_NAME = "filename";
    private static final String IMAGE_UPLOADED = "imageUploaded";
    private static final String IMAGE_UPLOAD_FAILED = "imageUploadFailed";

    /**
     * The method Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request is request
     * @param response is response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String path = UPLOAD_PATH;
        final Part filePart = request.getPart(FILE);
        final String fileName = request.getParameter(FILE_NAME) + ".png";
        String page = null;

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();

        try {
            out = new FileOutputStream(new File(path + File.separator + fileName));
            filecontent = filePart.getInputStream();

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            page = ConfigurationManager.getInstance().getProperty("page.main");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            request.setAttribute(IMAGE_UPLOADED, "image.upload.successfully");
            dispatcher.forward(request, response);

        } catch (FileNotFoundException fne) {
            request.setAttribute(IMAGE_UPLOAD_FAILED, "image.upload.failed");

        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * The method receive HTTP <code>GET</code> and call processRequest method
     *
     * @param request is the request
     * @param response is the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * The method receive HTTP <code>POST</code> and call processRequest method
     *
     * @param request is the request
     * @param response is the response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
