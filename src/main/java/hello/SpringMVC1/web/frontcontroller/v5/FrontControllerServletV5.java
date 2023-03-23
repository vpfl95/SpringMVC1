package hello.SpringMVC1.web.frontcontroller.v5;

import hello.SpringMVC1.web.frontcontroller.ModelView;
import hello.SpringMVC1.web.frontcontroller.MyView;
import hello.SpringMVC1.web.frontcontroller.v3.ControllerV3;
import hello.SpringMVC1.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.SpringMVC1.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.SpringMVC1.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.SpringMVC1.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.SpringMVC1.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.SpringMVC1.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.SpringMVC1.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.SpringMVC1.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5",urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    
    // 기존과 다르게 V3, V4 ...든 다 들어갈 수 있게 Objrct 타입으로
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private  final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }
    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        
        //V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. handlerMappingMap에서 핸들러 조회
        //MemberFormControllerV3 반환
        Object handler = getHandler(request);

        if(handler==null){
            // 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        //2. handlerAdapters에서 핸들러를 처리할 수 있느 핸들러어댑터 조회
        //ControllerV3HandlerAdapter 반환
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        //3.handle() 호출
        //ModelView 반환
        ModelView mv = adapter.handle(request, response, handler);

        // viewResolver 호출
        String viewName = mv.getViewName();// 논리이름 new-form
        // /WEB-INF/views/new-form.jsp 논리이름을 실제물리주소로 MyView 반환
        MyView view = viewResolver(viewName);

        // 뷰와 모델을 랜더링
        view.render(mv.getModel(),request,response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        //controllerMap에서 uri로 해당 controller 얻기
        return handlerMappingMap.get(requestURI);
    }
}
