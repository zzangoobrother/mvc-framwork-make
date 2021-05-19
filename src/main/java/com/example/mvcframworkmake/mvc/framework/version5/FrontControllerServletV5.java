package com.example.mvcframworkmake.mvc.framework.version5;

import com.example.mvcframworkmake.mvc.framework.ModelView;
import com.example.mvcframworkmake.mvc.framework.MyView;
import com.example.mvcframworkmake.mvc.framework.version3.controller.MemberFormControllerV3;
import com.example.mvcframworkmake.mvc.framework.version3.controller.MemberListControllerV3;
import com.example.mvcframworkmake.mvc.framework.version3.controller.MemberSaveControllerV3;
import com.example.mvcframworkmake.mvc.framework.version4.Controller.MemberFormControllerV4;
import com.example.mvcframworkmake.mvc.framework.version4.Controller.MemberListControllerV4;
import com.example.mvcframworkmake.mvc.framework.version4.Controller.MemberSaveControllerV4;
import com.example.mvcframworkmake.mvc.framework.version5.adapter.ControllerV3HandlerAdapter;
import com.example.mvcframworkmake.mvc.framework.version5.adapter.ControllerV4HandlerAdapter;
import com.example.mvcframworkmake.mvc.framework.version5.adapter.MyHandlerAdapter;

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

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/version5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/version5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/version5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/version5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/version5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/version5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/version5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdqpter(handler);

        ModelView mv = adapter.handler(request, response, handler);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdqpter(Object handler) {
        MyHandlerAdapter adapter;
        for (MyHandlerAdapter handlerAdapter : handlerAdapters) {
            if (handlerAdapter.supports(handler)) {
                return handlerAdapter;
            }
        }
        throw new IllegalArgumentException("handler adqpter를 찾을 수 없습니다.");
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }
}
