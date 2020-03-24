package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

	private final Messages messages;
	private final UserService userService;

//	@GetMapping("/profile")
//    public ModelAndView viewUserAccount(@AuthenticationPrincipal UserImpl activeUser){
//        ModelAndView mav = new ModelAndView("create_user_page");
//        mav.addObject("user", userService.getOne(activeUser.getUser()));
//        return mav;
//    }

    @GetMapping("/create")
    public ModelAndView getCreateUser(User user){

        if (user == null) {
            user = new User();
        }

        user.setPassword("");
        ModelAndView mav = new ModelAndView("/user/create_user_page");
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView postCreateUser(@ModelAttribute User user,
                                   BindingResult bindingResult) throws IOException {


        if (bindingResult.hasErrors()){
            return this.getCreateUser(user);
        }

        userService.save(user);
        ModelAndView mav = new ModelAndView("/user/create_user_page");


        return mav;
    }
}
