package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.forms.AnnounceCreateFrom;
import br.edu.ifrs.canoas.webapp.forms.Cropper;
import br.edu.ifrs.canoas.webapp.helper.URIHelper;
import br.edu.ifrs.canoas.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/announce")
@AllArgsConstructor
public class AnnounceController {

    private final Messages messages;
    private final AnnounceService announceService;
    private final CommentService commentService;
    private final ReportService reportService;
    private final AnimalCastratedService animalCastratedService;
    private final AnimalGenderService animalGenderService;
    private final AnimalSizeService animalSizeService;
    private final AnimalTypeService animalTypeService;
    private final AnimalAgeService animalAgeService;
    private final AnimalColorService animalColorService;

    private final Dimension imageTarget = new Dimension(512, 288);

    @GetMapping("/create")
    public String getCreate(Model model) {
        List<AnimalType> animalTypes = animalTypeService.listAnimalType();
        List<AnimalCastrated> animalCastrateds = animalCastratedService.listAnimalCastrated();
        List<AnimalGender> animalGenders = animalGenderService.listAnimalGender();
        List<AnimalAge> animalAges = animalAgeService.listAnimalAge();
        List<AnimalColor> animalColors = animalColorService.listAnimalColor();
        List<AnimalSize> animalSizes = animalSizeService.listAnimalSize();

        Announce announce = new Announce();
        announce.setAnimalType(animalTypes.get(0));
        announce.setAnimalCastrated(animalCastrateds.get(0));
        announce.setAnimalGender(animalGenders.get(0));
        announce.setAnimalAge(animalAges.get(0));
        announce.setAnimalColor(animalColors.get(0));
        announce.setAnimalSize(animalSizes.get(0));
        announce.setMainPhoto("");
        announce.setSecondPhoto("");
        announce.setThirdPhoto("");

        AnnounceCreateFrom form = new AnnounceCreateFrom();
        Cropper cropper = new Cropper();
        cropper.setRequired(true);
        form.setMainPhotoCropper(cropper);
        form.setSecondPhotoCropper(new Cropper());
        form.setThirdPhotoCropper(new Cropper());
        form.setAnnounce(announce);

        model.addAttribute("form", form);
        model.addAttribute("animalCastrated", animalCastrateds);
        model.addAttribute("animalGender", animalGenders);
        model.addAttribute("animalSize", animalSizes);
        model.addAttribute("animalType", animalTypes);
        model.addAttribute("animalAges", animalAges);
        model.addAttribute("animalColors", animalColors);
        return "/announce/create_announce_page";
    }

    @PostMapping("/create")
    public String postCreate(@AuthenticationPrincipal UserImpl activeUser,
                             @ModelAttribute("form") AnnounceCreateFrom form,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) throws IOException {

        model.addAttribute("animalCastrated", animalCastratedService.listAnimalCastrated());
        model.addAttribute("animalGender", animalGenderService.listAnimalGender());
        model.addAttribute("animalSize", animalSizeService.listAnimalSize());
        model.addAttribute("animalType", animalTypeService.listAnimalType());
        model.addAttribute("animalAges", animalAgeService.listAnimalAge());
        model.addAttribute("animalColors", animalColorService.listAnimalColor());

        form.getAnnounce().setMainPhoto(form.getMainPhotoCropper().doUpload(bindingResult, "mainPhoto", imageTarget, messages));
        form.getAnnounce().setSecondPhoto(form.getSecondPhotoCropper().doUpload(bindingResult, "secondPhoto", imageTarget, messages));
        form.getAnnounce().setThirdPhoto(form.getThirdPhotoCropper().doUpload(bindingResult, "thirdPhoto", imageTarget, messages));

        if (bindingResult.hasErrors()) {
            return "/announce/create_announce_page";
        }

        form.getAnnounce().setUser(activeUser.getUser());
        form.getAnnounce().setStatus(AnnounceStatus.ACTIVE);
        Announce announce = announceService.save(form.getAnnounce());

        redirectAttributes.addFlashAttribute("success", "announce.form.success");
        return "redirect:/announces/" + announce.getId();
    }

    /* Edit */

    @GetMapping("/{id}/edit")
    public String editGet(@PathVariable("id") final String id, Model model) {

        Announce announce = announceService.findById(Long.decode(id));
        if (announce == null || !announce.canEdit()) {
            throw new AnnounceNotFoundException();
        }

        AnnounceCreateFrom form = new AnnounceCreateFrom();
        form.setAnnounce(announce);

        Cropper cropper = new Cropper();
        cropper.setRequired(true);
        cropper.setCurrentImage(announce.getMainPhoto());
        form.setMainPhotoCropper(cropper);

        cropper = new Cropper();
        cropper.setCurrentImage(announce.getSecondPhoto());
        form.setSecondPhotoCropper(cropper);

        cropper = new Cropper();
        cropper.setCurrentImage(announce.getThirdPhoto());
        form.setThirdPhotoCropper(cropper);

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
                           @ModelAttribute("form") @Valid AnnounceCreateFrom form,
                           BindingResult bindingResult,
                           Model model,
                           RedirectAttributes redirectAttributes) throws IOException {

        Announce announce = announceService.findById(Long.decode(id));
        if (announce == null || !announce.canEdit()) {
            throw new AnnounceNotFoundException();
        }

        form.getMainPhotoCropper().setRequired(true);
        form.getMainPhotoCropper().setCurrentImage(announce.getMainPhoto());
        form.getSecondPhotoCropper().setCurrentImage(announce.getSecondPhoto());
        form.getThirdPhotoCropper().setCurrentImage(announce.getThirdPhoto());

        String image = form.getMainPhotoCropper().doUpload(bindingResult, "mainPhoto", imageTarget, messages, announce.getMainPhoto());
        if (image != null) {
            form.getAnnounce().setMainPhoto(image);
        }

        image = form.getSecondPhotoCropper().doUpload(bindingResult, "secondPhoto", imageTarget, messages, announce.getSecondPhoto());
        if (image != null) {
            form.getAnnounce().setSecondPhoto(image);
        }

        image = form.getThirdPhotoCropper().doUpload(bindingResult, "thirdPhoto", imageTarget, messages, announce.getThirdPhoto());
        if (image != null) {
            form.getAnnounce().setThirdPhoto(image);
        }

        if (!bindingResult.hasErrors()) {
            announce.setTitle(form.getAnnounce().getTitle());
            announce.setName(form.getAnnounce().getName());
            announce.setAnimalType(form.getAnnounce().getAnimalType());
            announce.setAnimalGender(form.getAnnounce().getAnimalGender());
            announce.setAnimalSize(form.getAnnounce().getAnimalSize());
            announce.setAnimalColor(form.getAnnounce().getAnimalColor());
            announce.setAnimalCastrated(form.getAnnounce().getAnimalCastrated());
            announce.setAnimalAge(form.getAnnounce().getAnimalAge());
            announce.setZipCode(form.getAnnounce().getZipCode());
            announce.setState(form.getAnnounce().getState());
            announce.setCity(form.getAnnounce().getCity());
            announce.setNeighborhood(form.getAnnounce().getNeighborhood());
            announce.setAddress(form.getAnnounce().getAddress());
            announce.setAddressNumber(form.getAnnounce().getAddressNumber());
            announce.setDescription(form.getAnnounce().getDescription());
            announce.setMainPhoto(form.getAnnounce().getMainPhoto());
            announce.setSecondPhoto(form.getAnnounce().getSecondPhoto());
            announce.setThirdPhoto(form.getAnnounce().getThirdPhoto());

            announceService.save(announce);
            redirectAttributes.addFlashAttribute("success", "announce.form.updated");
            return "redirect:/announces/" + announce.getId();
        }

        model.addAttribute("animalCastrated", animalCastratedService.listAnimalCastrated());
        model.addAttribute("animalGender", animalGenderService.listAnimalGender());
        model.addAttribute("animalSize", animalSizeService.listAnimalSize());
        model.addAttribute("animalType", animalTypeService.listAnimalType());
        model.addAttribute("animalAges", animalAgeService.listAnimalAge());
        model.addAttribute("animalColors", animalColorService.listAnimalColor());
        model.addAttribute("form", form);

        return "/announce/edit";
    }

    @PostMapping("/{id}/report")
    public String PostToReport(@AuthenticationPrincipal UserImpl activeUser,
                               @PathVariable("id") final Long id,
                               @ModelAttribute("message") String message,
                               RedirectAttributes redirectAttributes) {
        Announce announce = announceService.findByIdAndStatusActive(id);
        reportService.save(announce, activeUser.getUser(), message);
        announceService.setStatus(announce, AnnounceStatus.WAITING_REVIEW);
        redirectAttributes.addFlashAttribute("success", "announce.reported");
        return "redirect:/announces";
    }

    /* Comments */
    @PostMapping("/{id}/comment")
    public String saveComment(@AuthenticationPrincipal UserImpl activeUser,
                              @PathVariable("id") final Long id,
                              @ModelAttribute("message") String message,
                              RedirectAttributes redirectAttributes) {
        Announce announce = announceService.findByIdAndStatusActive(id);

        if (message.length() < 5 || message.length() > 230) {
            redirectAttributes.addFlashAttribute("error", "announce.comment.size");
        } else {
            Comment comment = new Comment();
            comment.setAnnounce(announce);
            comment.setStatus(CommentStatus.ACTIVE);
            comment.setUser(activeUser.getUser());
            comment.setMessage(message.trim());
            commentService.save(comment);
            redirectAttributes.addFlashAttribute("success", "announce.comment.created");
        }

        return "redirect:/announces/" + announce.getId();
    }

    @PostMapping("/{id}/comment/{commentId}/report")
    public String PostCommentToReport(@AuthenticationPrincipal UserImpl activeUser,
                                      @PathVariable("id") final Long id,
                                      @PathVariable("commentId") final Long commentId,
                                      @ModelAttribute("message") String message,
                                      RedirectAttributes redirectAttributes) {
        Announce announce = announceService.findByIdAndStatusActive(id);
        Comment comment = this.commentService.findOrThrow(commentId, announce);

        reportService.save(comment, activeUser.getUser(), message);
        commentService.setStatus(comment, CommentStatus.WAITING_REVIEW);
        redirectAttributes.addFlashAttribute("success", "announce.comment.reported");
        return "redirect:/announces/" + announce.getId();
    }

    @PostMapping("/{id}/comment/{commentId}/remove")
    public String PostCommentToRemove(@PathVariable("id") final Long id,
                                      @PathVariable("commentId") final Long commentId,
                                      RedirectAttributes redirectAttributes) {
        Announce announce = announceService.findByIdAndStatusActive(id);
        Comment comment = this.commentService.findOrThrow(commentId, announce);
        commentService.remove(comment);
        redirectAttributes.addFlashAttribute("success", "announce.comment.deleted");
        return "redirect:/announces/" + announce.getId();
    }

    @PostMapping("/{id}/remove")
    public String removeAnnounce(
            @PathVariable final Long id,
            @RequestParam final URI origin,
            RedirectAttributes redirectAttributes
    ) {

        announceService.removeAnnounce(id);
        redirectAttributes.addFlashAttribute("success", "announce.deleted");

        return "redirect:" + URIHelper.buildPath(origin);
    }
}
