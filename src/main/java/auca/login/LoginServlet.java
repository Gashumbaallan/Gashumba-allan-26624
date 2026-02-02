package auca.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    // Handles direct browser access (GET request)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Login</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;text-align:center;padding:60px;}");
        out.println("input,button{padding:10px;margin:8px;width:220px;}");
        out.println("</style></head><body>");

        out.println("<h2>Login Form</h2>");
        out.println("<form method='post' action='LoginServlet'>");
        out.println("<input type='text' name='username' placeholder='Username' required><br>");
        out.println("<input type='password' name='password' placeholder='Password' required><br>");
        out.println("<button type='submit'>Login</button>");
        out.println("</form>");

        out.println("</body></html>");
        out.close();
    }

    // Handles form submit (POST request)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty()) {
            username = "Guest";
        }
        if (password == null) {
            password = "";
        }

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>Login Result</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;text-align:center;padding:60px;}");
        out.println("</style></head><body>");

        if (password.length() < 8) {
            out.println("<h1>Hello " + username + ",</h1>");
            out.println("<p style='color:red;font-size:1.4em;'>Weak password ❌</p>");
            out.println("<p>Password must be at least 8 characters.</p>");
        } else {
            out.println("<h1 style='color:green;'>Welcome " + username + " ✅</h1>");
            out.println("<p>You have a strong password.</p>");
        }

        out.println("<br><a href='LoginServlet'>Try again</a>");
        out.println("</body></html>");
        out.close();
    }
}
