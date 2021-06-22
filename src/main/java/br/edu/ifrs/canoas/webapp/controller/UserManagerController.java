package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.User;
import br.edu.ifrs.canoas.webapp.domain.validation.UserEditGroup;
import br.edu.ifrs.canoas.webapp.forms.UserChangePasswordForm;
import br.edu.ifrs.canoas.webapp.forms.UserCreateForm;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager/user")
@AllArgsConstructor
public class UserManagerController {

    private final UserService userService;
    private final Messages messages;
    private final AnimalCastratedService animalCastratedService;
    private final AnimalGenderService animalGenderService;
    private final AnimalSizeService animalSizeService;
    private final AnimalTypeService animalTypeService;
    private final AnimalAgeService animalAgeService;
    private final AnimalColorService animalColorService;

    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal UserImpl activeUser, Model model) {
        User user = userService.findById(activeUser.getUser().getId());
        if (user == null) {
            return "/notFound";
        }

        model.addAttribute("user", user);
        return "/manager/user/profile";
    }

    @GetMapping("/profile/edit")
    public String getProfileEdit(@AuthenticationPrincipal UserImpl activeUser, Model model) {
        User user = userService.findById(activeUser.getUser().getId());
        if (user == null) {
            return "/notFound";
        }

        UserCreateForm form = new UserCreateForm();
        form.setUser(user);

        model.addAttribute("form", form);
        setAnimalData(model);
        return "/manager/user/edit";
    }

    @PostMapping("/profile/edit")
    public String postProfileEdit(@AuthenticationPrincipal UserImpl activeUser,
                                  @Validated({UserEditGroup.class}) @ModelAttribute("form") UserCreateForm form,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Model model) {

        User user = userService.findById(activeUser.getUser().getId());

        if (bindingResult.hasErrors()) {
            model.addAttribute("form", form);
            setAnimalData(model);
            return "/manager/user/edit";
        }

        user.setAnimalSize(form.getUser().getAnimalSize());
        user.setAnimalColor(form.getUser().getAnimalColor());
        user.setAnimalAge(form.getUser().getAnimalAge());
        user.setAnimalGender(form.getUser().getAnimalGender());
        user.setAnimalCastrated(form.getUser().getAnimalCastrated());
        user.setAnimalType(form.getUser().getAnimalType());
        user.setAddress(form.getUser().getAddress());
        user.setAddressNumber(form.getUser().getAddressNumber());
        user.setCelPhone(form.getUser().getCelPhone());
        user.setCity(form.getUser().getCity());
        user.setNeighborhood(form.getUser().getNeighborhood());
        user.setResidentialPhone(form.getUser().getResidentialPhone());
        user.setState(form.getUser().getState());
        user.setZipCode(form.getUser().getZipCode());
        user.setName(form.getUser().getName());

        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "user.form.edit");
        return "redirect:/manager/user/profile";
    }


    private void setAnimalData(Model model) {
        model.addAttribute("animalCastrated", animalCastratedService.listAnimalCastrated());
        model.addAttribute("animalGender", animalGenderService.listAnimalGender());
        model.addAttribute("animalSize", animalSizeService.listAnimalSize());
        model.addAttribute("animalType", animalTypeService.listAnimalType());
        model.addAttribute("animalAges", animalAgeService.listAnimalAge());
        model.addAttribute("animalColors", animalColorService.listAnimalColor());
    }

    @GetMapping("/password")
    public String getPasswordEdit(Model model) {
        model.addAttribute("form", new UserChangePasswordForm());
        return "/manager/user/password";
    }

    @PostMapping("/password")
    public String postPasswordEdit(
            @AuthenticationPrincipal UserImpl activeUser,
            @ModelAttribute("form") UserChangePasswordForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        User user = userService.getOne(activeUser.getUser());
        if (!form.isEqualCurrentPassword(user)) {
            String message = messages.get("validation.user.password_current_mismatch");
            FieldError error = new FieldError(bindingResult.getObjectName(), "currentPassword", message);
            bindingResult.addError(error);
        }

        if (!form.isEqualNewPassword()) {
            String message = messages.get("validation.user.password_mismatch");
            FieldError error = new FieldError(bindingResult.getObjectName(), "newPassword", message);
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            return "/manager/user/password";
        }

        user.setPassword(form.getHash());
        userService.save(user);
        redirectAttributes.addFlashAttribute("success", "user.password.changed");
        return "redirect:/manager/user/profile";
    }
}
