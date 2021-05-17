package com.example.mvcframworkmake.mvc.framework.version3;

import com.example.mvcframworkmake.mvc.framework.ModelView;
import com.example.mvcframworkmake.mvc.framework.MyView;
import com.example.mvcframworkmake.mvc.framework.version3.controller.ControllerV3;
import com.example.mvcframworkmake.mvc.framework.version3.controller.MemberFormControllerV3;
import com.example.mvcframworkmake.mvc.framework.version3.controller.MemberListControllerV3;
import com.example.mvcframworkmake.mvc.framework.version3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/version3/*")
public class FrontControllerServletV3 extends HttpServlet {

    Map<String, ControllerV3> controllerV3Map = new HashMap<>();

    public FrontControllerServletV3() {
        controllerV3Map.put("/front-controller/version3/members/new-form", new MemberFormControllerV3());
        controllerV3Map.put("/front-controller/version3/members/save", new MemberSaveControllerV3());
        controllerV3Map.put("/front-controller/version3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerV3Map.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        MyView view = viewResolver(mv);

        view.render(mv.getModel(), request, response);
    }

    private MyView viewResolver(ModelView mv) {
        return new MyView("/WEB-INF/views/" + mv.getViewName() + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
               .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
