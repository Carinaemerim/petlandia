package br.edu.ifrs.canoas.webapp.components;

import br.edu.ifrs.canoas.webapp.forms.ManagerSidebar;
import br.edu.ifrs.canoas.webapp.service.AnnounceService;
import br.edu.ifrs.canoas.webapp.service.CommentService;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@AllArgsConstructor
public class ManagerInterceptor implements HandlerInterceptor {

    private final AnnounceService announceService;
    private final CommentService commentService;
    private final UserService userService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {
        if (model == null) {
            return;
        }

        ManagerSidebar sidebar = new ManagerSidebar();
        sidebar.set(announceService, commentService, userService, request);
        model.addObject("sidebar", sidebar);
    }
}
