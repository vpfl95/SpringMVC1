package hello.SpringMVC1.web.frontcontroller.v1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;

public interface ControllerV1 {

    //서블릿이랑 모양 똑같이
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
}
