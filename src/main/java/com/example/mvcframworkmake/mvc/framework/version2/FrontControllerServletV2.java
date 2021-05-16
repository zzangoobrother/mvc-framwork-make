package com.example.mvcframworkmake.mvc.framework.version2;


import com.example.mvcframworkmake.mvc.framework.MyView;
import com.example.mvcframworkmake.mvc.framework.version2.controller.ControllerV2;
import com.example.mvcframworkmake.mvc.framework.version2.controller.MemberFormControllerV2;
import com.example.mvcframworkmake.mvc.framework.version2.controller.MemberListControllerV2;
import com.example.mvcframworkmake.mvc.framework.version2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/version2/*")
public class FrontControllerServletV2 extends HttpServlet {

    Map<String, ControllerV2> controllerV2Map = new HashMap<>();

    public FrontControllerServletV2() {
        controllerV2Map.put("/front-controller/version2/members/new-form", new MemberFormControllerV2());
        controllerV2Map.put("/front-controller/version2/members/save", new MemberSaveControllerV2());
        controllerV2Map.put("/front-controller/version2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        ControllerV2 controller = controllerV2Map.get(requestURI);

        if (requestURI == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
