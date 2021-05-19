package com.example.mvcframworkmake.mvc.framework.version4;

import com.example.mvcframworkmake.mvc.framework.MyView;
import com.example.mvcframworkmake.mvc.framework.version4.Controller.ControllerV4;
import com.example.mvcframworkmake.mvc.framework.version4.Controller.MemberFormControllerV4;
import com.example.mvcframworkmake.mvc.framework.version4.Controller.MemberListControllerV4;
import com.example.mvcframworkmake.mvc.framework.version4.Controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/version4/*")
public class FrontControllerServletV4 extends HttpServlet {

    Map<String, ControllerV4> controllerV4Map = new HashMap<>();

    public FrontControllerServletV4() {
        controllerV4Map.put("/front-controller/version4/members/new-form", new MemberFormControllerV4());
        controllerV4Map.put("/front-controller/version4/members/save", new MemberSaveControllerV4());
        controllerV4Map.put("/front-controller/version4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        ControllerV4 controller = controllerV4Map.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();
        String viewName = controller.process(paramMap, model);
        
        MyView view = viewResolver(viewName);

        view.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
