package hello.SpringMVC1.web.frontcontroller.v2;

import hello.SpringMVC1.web.frontcontroller.MyView;
import hello.SpringMVC1.web.frontcontroller.v1.ControllerV1;
import hello.SpringMVC1.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.SpringMVC1.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.SpringMVC1.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        //URI 얻기
        String requestURI = request.getRequestURI();

        //controllerMap에서 uri로 해당 controller 얻기
        ControllerV2 controller = controllerMap.get(requestURI);

        if(controller==null){
            // 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //new MyView("/WEB-INF/views/new-form.jsp");
        MyView view = controller.process(request, response);
        view.render(request,response);

    }
}
