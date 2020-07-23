package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.domain.validation.UserCreateGroup;
import br.edu.ifrs.canoas.webapp.domain.validation.UserEditGroup;
import br.edu.ifrs.canoas.webapp.enums.Role;
import br.edu.ifrs.canoas.webapp.forms.UserCreateForm;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final Messages messages;
    private final AnimalCastratedService animalCastratedService;
    private final AnimalGenderService animalGenderService;
    private final AnimalSizeService animalSizeService;
    private final AnimalTypeService animalTypeService;
    private final AnimalAgeService animalAgeService;
    private final AnimalColorService animalColorService;


    @GetMapping("/create")
    public String getCreateUser(Model model) {

        List<AnimalType> animalTypes = animalTypeService.listAnimalType();
        List<AnimalCastrated> animalCastrateds = animalCastratedService.listAnimalCastrated();
        List<AnimalGender> animalGenders = animalGenderService.listAnimalGender();
        List<AnimalAge> animalAges = animalAgeService.listAnimalAge();
        List<AnimalColor> animalColors = animalColorService.listAnimalColor();
        List<AnimalSize> animalSizes = animalSizeService.listAnimalSize();

        UserCreateForm form = new UserCreateForm();
        form.setUser(new User());
        form.getUser().setPassword("");
        form.setPasswordConfirm("");
        form.getUser().setAnimalType(animalTypes.get(0));
        form.getUser().setAnimalCastrated(animalCastrateds.get(0));
        form.getUser().setAnimalGender(animalGenders.get(0));
        form.getUser().setAnimalAge(animalAges.get(0));
        form.getUser().setAnimalColor(animalColors.get(0));
        form.getUser().setAnimalSize(animalSizes.get(0));

        model.addAttribute("form", form);
        model.addAttribute("animalCastrated", animalCastrateds);
        model.addAttribute("animalGender", animalGenders);
        model.addAttribute("animalSize", animalSizes);
        model.addAttribute("animalType", animalTypes);
        model.addAttribute("animalAges", animalAges);
        model.addAttribute("animalColors", animalColors);

        return "/user/create_user_page";
    }

    @PostMapping("/create")
    public String postCreateUser(@ModelAttribute("form") @Validated({UserCreateGroup.class}) UserCreateForm form,
                                 BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        model.addAttribute("animalCastrated", animalCastratedService.listAnimalCastrated());
        model.addAttribute("animalGender", animalGenderService.listAnimalGender());
        model.addAttribute("animalSize", animalSizeService.listAnimalSize());
        model.addAttribute("animalType", animalCastratedService.listAnimalCastrated());
        model.addAttribute("animalAges", animalAgeService.listAnimalAge());
        model.addAttribute("animalColors", animalColorService.listAnimalColor());

        if (!form.getPasswordConfirm().equals(form.getUser().getPassword())) {
            String message = messages.get("form.validation.pwd_is_not_equal");
            FieldError error = new FieldError(bindingResult.getObjectName(), "passwordConfirm", message);
            bindingResult.addError(error);
        }

        if (bindingResult.hasErrors()) {
            return "/user/create_user_page";
        }

        form.getUser().setRole(Role.ROLE_USER);

        userService.save(form.getUser());
        redirectAttributes.addFlashAttribute("successRegister",
                messages.get("user.login.new"));

        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public String userDetails(@PathVariable("id") final String id, Model model) {

        User user = userService.findById(Long.decode(id));
        if (user == null){
            return "/notFound";
        }

        model.addAttribute("user", user);
        return "/user/userDetails";
    }
}
