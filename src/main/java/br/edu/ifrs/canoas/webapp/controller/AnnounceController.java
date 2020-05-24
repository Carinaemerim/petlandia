package br.edu.ifrs.canoas.webapp.controller;

import br.edu.ifrs.canoas.webapp.config.Messages;
import br.edu.ifrs.canoas.webapp.config.auth.UserImpl;
import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.enums.CommentStatus;
import br.edu.ifrs.canoas.webapp.exception.AnnounceNotFoundException;
import br.edu.ifrs.canoas.webapp.exception.CommentNotFoundException;
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

import javax.validation.Valid;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/announce")
@AllArgsConstructor
public class AnnounceController {

    private final Messages messages;
    private final AnnounceService announceService;
    private final CommentService commentService;
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
        Cropper cropper = new Cropper();
        cropper.setRequired(true);
        form.setMainPhotoCropper(cropper);
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

        form.getAnnounce().setMainPhoto(doUpload(form.getMainPhotoCropper(), bindingResult, "mainPhoto"));
        form.getAnnounce().setSecondPhoto(doUpload(form.getSecondPhotoCropper(), bindingResult, "secondPhoto"));
        form.getAnnounce().setThirdPhoto(doUpload(form.getThirdPhotoCropper(), bindingResult, "thirdPhoto"));


        if (bindingResult.hasErrors()) {
            return "/announce/create_announce_page";
        }

        form.getAnnounce().setUser(activeUser.getUser());
        form.getAnnounce().setStatus(AnnounceStatus.ACTIVE);
        Announce announce = announceService.save(form.getAnnounce());
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
                          BindingResult bindingResult, Model model) throws IOException {

        Announce announce = announceService.findById(Long.decode(id));
        if (announce == null || !announce.canEdit()) {
            throw new AnnounceNotFoundException();
        }

        form.getMainPhotoCropper().setRequired(true);
        form.getMainPhotoCropper().setCurrentImage(announce.getMainPhoto());
        form.getSecondPhotoCropper().setCurrentImage(announce.getSecondPhoto());
        form.getThirdPhotoCropper().setCurrentImage(announce.getThirdPhoto());

        String image = doUpload(form.getMainPhotoCropper(), bindingResult, "mainPhoto", announce.getMainPhoto());
        if (image != null) {
            form.getAnnounce().setMainPhoto(image);
        }

        image = doUpload(form.getSecondPhotoCropper(), bindingResult, "secondPhoto", announce.getSecondPhoto());
        if (image != null) {
            form.getAnnounce().setSecondPhoto(image);
        }

        image = doUpload(form.getThirdPhotoCropper(), bindingResult, "thirdPhoto", announce.getThirdPhoto());
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

            announce.setStatus(AnnounceStatus.WAITING_REVIEW);
            announceService.save(announce);
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

    /* Comments */

    @PostMapping("/{id}/comment")
    public String saveComment(@AuthenticationPrincipal UserImpl activeUser,
                              @PathVariable("id") final Long id,
                              @ModelAttribute("message") String message,
                              BindingResult bindingResult) {
        Announce announce = this.getActiveAnnounce(id);

        if (!bindingResult.hasErrors()) {
            Comment comment = new Comment();
            comment.setAnnounce(announce);
            comment.setStatus(CommentStatus.ACTIVE);
            comment.setUser(activeUser.getUser());
            comment.setMessage(message);

            commentService.save(comment);
        }

        return "redirect:/announces/" + announce.getId();
    }

    @PostMapping("/{id}/comment/{commentId}/report")
    public String PostCommentToReport(@PathVariable("id") final Long id,
                                       @PathVariable("commentId") final Long commentId,
                                       @ModelAttribute("form") @Valid AnnounceCreateFrom form,
                                       BindingResult bindingResult, Model model) {
        Announce announce = this.getActiveAnnounce(id);
        Comment comment = this.getComment(commentId, announce);

        return "redirect:/announces/" + announce.getId();
    }

    @PostMapping("/{id}/comment/{commentId}/remove")
    public String PostCommentToRemove(@PathVariable("id") final Long id,
                                        @PathVariable("commentId") final Long commentId,
                                        @ModelAttribute("form") @Valid AnnounceCreateFrom form,
                                        BindingResult bindingResult, Model model) {

        Announce announce = this.getActiveAnnounce(id);
        Comment comment = this.getComment(commentId, announce);

        commentService.remove(comment);

        return "redirect:/announces/" + announce.getId();
    }











    private Announce getActiveAnnounce(Long id) {
        Announce announce = announceService.findById(id);
        if (announce == null || !announce.getStatus().equals(AnnounceStatus.ACTIVE)) {
            throw new AnnounceNotFoundException();
        }

        return announce;
    }

    private Comment getComment(Long id, Announce announce) {
        Comment comment = commentService.findByIdAndAnnounce(id, announce);
        if (comment == null) {
            throw new CommentNotFoundException();
        }

        return comment;
    }

    private String doUpload(Cropper cropper, BindingResult bindingResult, String field) throws IOException {
        return this.doUpload(cropper, bindingResult, field, null);
    }

    private String doUpload(Cropper cropper, BindingResult bindingResult, String field, String currentImage) throws IOException {
        if (bindingResult.hasErrors()) {
            return null;
        }

        boolean hasImage = cropper.getImage() != null && !cropper.getImage().isEmpty();
        if (hasImage) {
            return ImageResize.getBase64FromUploadImage(cropper, this.imageTarget);
        }

        if (cropper.isRequired()) {
            if (currentImage != null) {
                return currentImage;
            } else {
                String message = messages.get("form.validation.pwd_is_not_equal"); // TODO: Trocar
                FieldError error = new FieldError(bindingResult.getObjectName(), field, message);
                bindingResult.addError(error);
            }
        }

        return cropper.isRemove() ? null : currentImage;
    }
}
