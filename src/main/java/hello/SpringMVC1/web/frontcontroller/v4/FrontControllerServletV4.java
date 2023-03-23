package hello.SpringMVC1.web.frontcontroller.v4;

import hello.SpringMVC1.web.frontcontroller.ModelView;
import hello.SpringMVC1.web.frontcontroller.MyView;
import hello.SpringMVC1.web.frontcontroller.v3.ControllerV3;
import hello.SpringMVC1.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.SpringMVC1.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.SpringMVC1.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.SpringMVC1.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.SpringMVC1.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.SpringMVC1.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //URI 얻기
        String requestURI = request.getRequestURI();

        //controllerMap에서 uri로 해당 controller 얻기
        ControllerV4 controller = controllerMap.get(requestURI);

        if(controller==null){
            // 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


        //paramMap
        //모든 파라미터 가져와서 paramMap에 넣기 username:ddd, age:dd
        Map<String, String> paramMap = createParamMap(request);
        Map<String,Object> model = new HashMap<>(); //추가

        String viewName = controller.process(paramMap, model);

        // /WEB-INF/views/new-form.jsp 논리이름을 실제물리주소로 MyView 반환
        MyView view = viewResolver(viewName);

        // 뷰와 모델을 랜더링
        view.render(model, request,response);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    //모든 파라미터 가져와서 paramMap에 넣기
    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                        .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
