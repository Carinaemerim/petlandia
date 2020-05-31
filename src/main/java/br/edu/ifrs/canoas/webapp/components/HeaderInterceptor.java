package br.edu.ifrs.canoas.webapp.components;

import br.edu.ifrs.canoas.webapp.forms.HeaderBar;
import br.edu.ifrs.canoas.webapp.forms.ManagerSidebar;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class HeaderInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
        HeaderBar headerBar = new HeaderBar();
        headerBar.set(request);
        model.addObject("headerBar", headerBar);
    }
}
