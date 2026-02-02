package auca.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/google")
public class GoogleRedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("q");

        if (query == null || query.trim().isEmpty()) {
            response.sendRedirect("https://www.google.com");
            return;
        }

        String encoded = java.net.URLEncoder.encode(query.trim(), "UTF-8");
        response.sendRedirect("https://www.google.com/search?q=" + encoded);
    }
}