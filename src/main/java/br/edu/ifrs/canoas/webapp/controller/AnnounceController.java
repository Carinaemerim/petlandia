package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.AnimalCastrated;
import br.edu.ifrs.canoas.webapp.domain.AnimalGender;
import br.edu.ifrs.canoas.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.webapp.domain.Announce;
import br.edu.ifrs.canoas.webapp.helper.ImageResize;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/announce")
@AllArgsConstructor
public class AnnounceController {

    private final AnnounceService announceService;
    private final AnimalCastratedService animalCastratedService;
    private final AnimalGenderService animalGenderService;
    private final AnimalSizeService animalSizeService;
    private final AnimalTypeService animalTypeService;

    @GetMapping("/create")
    public ModelAndView getCreate(Announce announce) {

        List<AnimalType> animalTypes = animalTypeService.listAnimalType();
        List<AnimalCastrated> animalCastrateds = animalCastratedService.listAnimalCastrated();
        List<AnimalGender> animalGenders = animalGenderService.listAnimalGender();

        if (announce == null) {
            announce = new Announce();
            announce.setAnimalType(animalTypes.get(0));
            announce.setAnimalCastrated(animalCastrateds.get(0));
            announce.setAnimalGender(animalGenders.get(0));
        }

        ModelAndView mav = new ModelAndView("/announce/create_announce_page");
        mav.addObject("announce", announce);
        mav.addObject("animalCastrated", animalCastrateds);
        mav.addObject("animalGender", animalGenders);
        mav.addObject("animalSize", animalSizeService.listAnimalSize());
        mav.addObject("animalType", animalTypes);
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView postCreate(@ModelAttribute Announce announce,
                                   @RequestParam("main_photo") MultipartFile mainPhoto,
                                   @RequestParam("second_photo") MultipartFile secondPhoto,
                                   @RequestParam("third_photo") MultipartFile thirdPhoto,
                                    BindingResult bindingResult) throws IOException {

        Dimension desirable = new Dimension(500, 500);
        if (mainPhoto.isEmpty() && bindingResult.hasErrors() == false) {
            // bindingResult.addError();
        } else {
            String image = ImageResize.getBase64FromUploadImage(mainPhoto, desirable);
            announce.setMainPhoto(image);
        }

        if (!secondPhoto.isEmpty() && bindingResult.hasErrors() == false) {
            String image = ImageResize.getBase64FromUploadImage(mainPhoto, desirable);
            announce.setSecondPhoto(image);
        }

        if (!thirdPhoto.isEmpty() && bindingResult.hasErrors() == false) {
            String image = ImageResize.getBase64FromUploadImage(mainPhoto, desirable);
            announce.setThirdPhoto(image);
        }


        if (bindingResult.hasErrors()){
            return this.getCreate(announce);
        }

        announceService.save(announce);
        ModelAndView mav = new ModelAndView("/announce/create_announce_page");


        return mav;
    }

    @GetMapping("/details")
    public ModelAndView dashboard(@RequestParam(value = "id") final Long id) {

        ModelAndView mav = new ModelAndView("/announce/announceDetails");

        Announce announce = announceService.findById(id);

        mav.addObject("announce", announce);

        return mav;
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
