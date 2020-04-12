package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.forms.AnnounceCreateFrom;
import br.edu.ifrs.canoas.webapp.forms.Cropper;
import br.edu.ifrs.canoas.webapp.helper.ImageResize;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/announce")
@AllArgsConstructor
public class AnnounceController {

    private final Messages messages;
    private final AnnounceService announceService;
    private final AnimalCastratedService animalCastratedService;
    private final AnimalGenderService animalGenderService;
    private final AnimalSizeService animalSizeService;
    private final AnimalTypeService animalTypeService;
    private final AnimalAgeService animalAgeService;
    private final AnimalColorService animalColorService;

    private final Dimension imageTarget = new Dimension(1280, 720);

    @GetMapping("/create")
    public String getCreate(Model model) {
        List<AnimalType> animalTypes = animalTypeService.listAnimalType();
        List<AnimalCastrated> animalCastrateds = animalCastratedService.listAnimalCastrated();
        List<AnimalGender> animalGenders = animalGenderService.listAnimalGender();
        List<AnimalAge> animalAges = animalAgeService.listAnimalAge();
        List<AnimalColor> animalColors = animalColorService.listAnimalColor();

        Announce announce = new Announce();
        announce.setAnimalType(animalTypes.get(0));
        announce.setAnimalCastrated(animalCastrateds.get(0));
        announce.setAnimalGender(animalGenders.get(0));
        announce.setAnimalAge(animalAges.get(0));
        announce.setAnimalColor(animalColors.get(0));
        announce.setMainPhoto("");
        announce.setSecondPhoto("");
        announce.setThirdPhoto("");

        AnnounceCreateFrom form = new AnnounceCreateFrom();
        form.setMainPhotoCropper(new Cropper());
        form.setSecondPhotoCropper(new Cropper());
        form.setThirdPhotoCropper(new Cropper());
        form.setAnnounce(announce);

        model.addAttribute("form", form);
        model.addAttribute("animalCastrated", animalCastrateds);
        model.addAttribute("animalGender", animalGenders);
        model.addAttribute("animalSize", animalSizeService.listAnimalSize());
        model.addAttribute("animalType", animalTypes);
        model.addAttribute("animalAges", animalAges);
        model.addAttribute("animalColors", animalColors);
        return "/announce/create_announce_page";
    }

    @PostMapping("/create")
    public String postCreate(@AuthenticationPrincipal UserImpl activeUser,
                             @ModelAttribute("form") AnnounceCreateFrom form,
                             BindingResult bindingResult, Model model) throws IOException {

        model.addAttribute("animalCastrated", animalCastratedService.listAnimalCastrated());
        model.addAttribute("animalGender", animalGenderService.listAnimalGender());
        model.addAttribute("animalSize", animalSizeService.listAnimalSize());
        model.addAttribute("animalType", animalTypeService.listAnimalType());
        model.addAttribute("animalAges", animalAgeService.listAnimalAge());
        model.addAttribute("animalColors", animalColorService.listAnimalColor());

        doUpload(form, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/announce/create_announce_page";
        }

        form.getAnnounce().setUser(activeUser.getUser());
        form.getAnnounce().setDate(new Date());
        Announce announce = announceService.save(form.getAnnounce());
        return "redirect:/announces/" + announce.getId();
    }

    /* Edit */

    @GetMapping("/{id}/edit")
    public String editGet(@PathVariable("id") final String id, Model model) {

        Announce announce = announceService.findById(Long.decode(id));
        if (announce == null || !announce.canEdit()) {
            return "/notFound";
        }

        AnnounceCreateFrom form = new AnnounceCreateFrom();
        form.setAnnounce(announce);
        form.setMainPhotoCropper(new Cropper());
        form.setSecondPhotoCropper(new Cropper());
        form.setThirdPhotoCropper(new Cropper());

        model.addAttribute("animalCastrated", animalCastratedService.listAnimalCastrated());
        model.addAttribute("animalGender", animalGenderService.listAnimalGender());
        model.addAttribute("animalSize", animalSizeService.listAnimalSize());
        model.addAttribute("animalType", animalTypeService.listAnimalType());
        model.addAttribute("animalAges", animalAgeService.listAnimalAge());
        model.addAttribute("animalColors", animalColorService.listAnimalColor());
        model.addAttribute("form", form);

        return "/announce/edit";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable("id") final String id,
                          @ModelAttribute("form") AnnounceCreateFrom form,
                          BindingResult bindingResult, Model model) {

        Announce announce = announceService.findById(Long.decode(id));
        if (announce == null || !announce.canEdit()) {
            return "/notFound";
        }

        // TODO Salvar

        model.addAttribute("animalCastrated", animalCastratedService.listAnimalCastrated());
        model.addAttribute("animalGender", animalGenderService.listAnimalGender());
        model.addAttribute("animalSize", animalSizeService.listAnimalSize());
        model.addAttribute("animalType", animalTypeService.listAnimalType());
        model.addAttribute("animalAges", animalAgeService.listAnimalAge());
        model.addAttribute("animalColors", animalColorService.listAnimalColor());
        model.addAttribute("form", form);

        return "/announce/edit";
    }



























    private void doUpload(AnnounceCreateFrom form, BindingResult bindingResult) throws IOException {
        Cropper mainPhoto = form.getMainPhotoCropper();
        boolean hasMainPhoto = mainPhoto != null && !mainPhoto.getImage().isEmpty();

        Cropper secondPhoto = form.getSecondPhotoCropper();
        boolean hasSecondPhoto = secondPhoto != null && !secondPhoto.getImage().isEmpty();

        Cropper thirdPhoto = form.getThirdPhotoCropper();
        boolean hasThirdPhoto = thirdPhoto != null && !thirdPhoto.getImage().isEmpty();

        if (!hasMainPhoto && !bindingResult.hasErrors()) {
            String message = messages.get("form.validation.pwd_is_not_equal"); // TODO: trocar
            FieldError error = new FieldError(bindingResult.getObjectName(), "mainPhoto", message);
            bindingResult.addError(error);
        } else {
            String image = ImageResize.getBase64FromUploadImage(mainPhoto, this.imageTarget);
            form.getAnnounce().setMainPhoto(image);
        }

        if (hasSecondPhoto && !bindingResult.hasErrors()) {
            String image = ImageResize.getBase64FromUploadImage(secondPhoto, this.imageTarget);
            form.getAnnounce().setSecondPhoto(image);
        }

        if (hasThirdPhoto && !bindingResult.hasErrors()) {
            String image = ImageResize.getBase64FromUploadImage(thirdPhoto, this.imageTarget);
            form.getAnnounce().setThirdPhoto(image);
        }
    }
}
