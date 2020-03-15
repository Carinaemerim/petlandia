package br.edu.ifrs.canoas.lds.webapp.controller;

import br.edu.ifrs.canoas.lds.webapp.domain.Announce;
import br.edu.ifrs.canoas.lds.webapp.service.AnnounceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/announce")
@AllArgsConstructor
public class AnnounceController {

    private final AnnounceService announceService;

    @GetMapping("/create")
    public ModelAndView getCreate() {

        ModelAndView mav = new ModelAndView("/announce/create_announce_page");

        return mav;
    }

    @PostMapping("/create")
    @ModelAttribute
    public ModelAndView postCreate() {


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
