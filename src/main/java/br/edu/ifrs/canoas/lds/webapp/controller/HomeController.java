package br.edu.ifrs.canoas.lds.webapp.controller;

import br.edu.ifrs.canoas.lds.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.lds.webapp.domain.Announce;
import br.edu.ifrs.canoas.lds.webapp.domain.City;
import br.edu.ifrs.canoas.lds.webapp.service.AnnounceService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by rodrigo on 2/21/17.
 */
@Controller
@AllArgsConstructor
public class HomeController {

	private final AnnounceService announceService;

	@GetMapping("/")
	public ModelAndView greetings() {


		List<AnimalType> animalTypes = announceService.getAnimalTypes();
		List<City> cities = announceService.getCityTypes();


		ModelAndView mav = new ModelAndView("/index");

		mav.addObject("animalTypes", animalTypes);
		mav.addObject("cities", cities);

		return mav;
	}


}
