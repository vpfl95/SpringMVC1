package hello.SpringMVC1.web.frontcontroller.v1;

import hello.SpringMVC1.web.frontcontroller.v1.controller.MemberFormcontrollerV1;
import hello.SpringMVC1.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.SpringMVC1.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormcontrollerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        //URI 얻기
        String requestURI = request.getRequestURI();

        //controllerMap에서 uri로 해당 controller 얻기
        ControllerV1 controller = controllerMap.get(requestURI);

        if(controller==null){
            // 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request,response);

    }
}
