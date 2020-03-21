package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.domain.AnimalType;
import br.edu.ifrs.canoas.webapp.service.AnimalTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by rodrigo on 2/21/17.
 */
@Controller
@AllArgsConstructor
public class HomeController {

	private final AnimalTypeService animalTypeService;

	@GetMapping("/")
	public ModelAndView greetings() {


		List<AnimalType> animalType = animalTypeService.listAnimalType();


		ModelAndView mav = new ModelAndView("/index");

		mav.addObject("animalTypes", animalType);

		return mav;
	}


}
