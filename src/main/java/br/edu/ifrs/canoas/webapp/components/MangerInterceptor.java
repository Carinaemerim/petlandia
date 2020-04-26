package br.edu.ifrs.canoas.webapp.components;

import br.edu.ifrs.canoas.webapp.forms.ManagerSidebar;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class MangerInterceptor implements HandlerInterceptor {

    private final AnnounceService announceService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
        ManagerSidebar sidebar = new ManagerSidebar();

        sidebar.set(announceService, request);

        model.addObject("sidebar", sidebar);
    }
}
