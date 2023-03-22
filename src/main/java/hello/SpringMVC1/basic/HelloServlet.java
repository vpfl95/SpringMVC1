package hello.SpringMVC1.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest reqest, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("reqest = " + reqest);
        System.out.println("response = " + response);

        String username = reqest.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain");  //content-type에 들어감
        response.setCharacterEncoding("utf-8"); //content-type에 들어감
        response.getWriter().write("hello " + username); // http 메시지 바디에 들어감


    }
}
