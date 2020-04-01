package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.forms.AnnounceCreateFrom;
import br.edu.ifrs.canoas.webapp.forms.Cropper;
import br.edu.ifrs.canoas.webapp.helper.ImageResize;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public String postCreate(@ModelAttribute("form") AnnounceCreateFrom form,
                                    BindingResult bindingResult, Model model) throws IOException {

        model.addAttribute("animalCastrated", animalCastratedService.listAnimalCastrated());
        model.addAttribute("animalGender", animalGenderService.listAnimalGender());
        model.addAttribute("animalSize", animalSizeService.listAnimalSize());
        model.addAttribute("animalType", animalTypeService.listAnimalType());
        model.addAttribute("animalAge", animalAgeService.listAnimalAge());
        model.addAttribute("animalColors", animalColorService.listAnimalColor());

        Dimension desirable = new Dimension(500, 500);
        boolean hasMainPhoto = form.getMainPhoto() != null && !form.getMainPhoto().isEmpty();
        boolean hasSecondPhoto = form.getSecondPhoto() != null && !form.getSecondPhoto().isEmpty();
        boolean hasThirdPhoto = form.getThirdPhoto() != null && !form.getThirdPhoto().isEmpty();

        if (!hasMainPhoto && !bindingResult.hasErrors()) {
            String message = messages.get("form.validation.pwd_is_not_equal");
            FieldError error = new FieldError(bindingResult.getObjectName(), "mainPhoto", message);
            bindingResult.addError(error);
        } else {
            String image = ImageResize.getBase64FromUploadImage(form.getMainPhoto(), desirable);
            System.out.println(image);
            form.getAnnounce().setMainPhoto(image);
        }

        if (hasSecondPhoto && !bindingResult.hasErrors()) {
            String image = ImageResize.getBase64FromUploadImage(form.getSecondPhoto(), desirable);
            form.getAnnounce().setSecondPhoto(image);
        }

        if (hasThirdPhoto && !bindingResult.hasErrors()) {
            String image = ImageResize.getBase64FromUploadImage(form.getThirdPhoto(), desirable);
            form.getAnnounce().setThirdPhoto(image);
        }

        if (bindingResult.hasErrors()){
            return "/announce/create_announce_page";
        }


        form.getAnnounce().setDate(new Date());
        Announce announce = announceService.save(form.getAnnounce());
        return "redirect:/announce/" + announce.getId();
    }

    @GetMapping("/{id}")
    public String announceDetails(@PathVariable("id") final String id, Model model) {

        Announce announce = announceService.findById(Long.decode(id));
        if (announce == null){
            return "/notFound";
        }

        model.addAttribute("announce", announce);
        return "/announce/announceDetails";
    }

    @GetMapping("/filter")
    public ModelAndView filterAnnounces(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                        @RequestParam(value = "cityId", required = false) Long cityId,
                                        @RequestParam(value = "animalTypeId", required = false) Long animalTypeId){

        ModelAndView mav = new ModelAndView("/announce/fragments/announce-list");

        if(page < 0){
            page=1;
        }

        Page<Announce> announces = announceService.findAll(page, cityId, animalTypeId);
        mav.addObject("announces", announces);
        mav.addObject("currentPage", page);

        return mav;
    }

}
