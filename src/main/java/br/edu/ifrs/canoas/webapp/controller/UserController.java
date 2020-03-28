package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.forms.UserCreateForm;
import br.edu.ifrs.canoas.webapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final Messages messages;

//	@GetMapping("/profile")
//    public ModelAndView viewUserAccount(@AuthenticationPrincipal UserImpl activeUser){
//        ModelAndView mav = new ModelAndView("create_user_page");
//        mav.addObject("user", userService.getOne(activeUser.getUser()));
//        return mav;
//    }

    @GetMapping("/create")
    public String getCreateUser(Model model) {
        UserCreateForm form = new UserCreateForm();
        form.setUser(new User());
        form.getUser().setPassword("");
        form.setPasswordConfirm("");
        model.addAttribute("form", new UserCreateForm());
        return "/user/create_user_page";
    }

    @PostMapping("/create")
    public String postCreateUser(@ModelAttribute("form") @Valid UserCreateForm form,
                                 BindingResult bindingResult, Model model) {
        if (!form.getPasswordConfirm().equals(form.getUser().getPassword())) {
            String message = messages.get("form.validation.pwd_is_not_equal");
            FieldError error = new FieldError(bindingResult.getObjectName(), "passwordConfirm", message);
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            return "/user/create_user_page";
        }

        userService.save(form.getUser());

        return "/user/create_user_page";
    }
}
