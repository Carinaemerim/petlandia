package br.edu.ifrs.canoas.webapp.web.page.announce;

import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import lombok.Data;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

@PageUrl("/announce/create")
@Data
public class AnnounceCreatePage extends GenericPage {
    @FindBy(id = "announce_form_title")
    protected FluentWebElement titleField;

    @FindBy(id = "announce_form_animal_name")
    protected FluentWebElement animalNameField;

    @FindBy(id = "announce_form_zipCode")
    protected FluentWebElement zipcodeField;

    @FindBy(id = "announce_form_state")
    protected FluentWebElement stateField;

    @FindBy(id = "announce_form_city")
    protected FluentWebElement cityField;

    @FindBy(id = "announce_form_neighborhood")
    protected FluentWebElement neighborhoodField;

    @FindBy(id = "announce_form_address")
    protected FluentWebElement addressField;

    @FindBy(id = "announce_form_address_number")
    protected FluentWebElement addressNumberField;

    @FindBy(id = "announce.animalType1")
    protected FluentWebElement animalTypeCatField;

    @FindBy(id = "announce.animalType2")
    protected FluentWebElement animalTypeDogField;

    @FindBy(id = "announce.animalGender1")
    protected FluentWebElement animalGenderFemale;

    @FindBy(id = "announce.animalGender2")
    protected FluentWebElement animalGenderMale;

    @FindBy(id = "announce_form_animal_size")
    protected FluentWebElement animalSizeField;

    @FindBy(id = "announce_form_animal_color")
    protected FluentWebElement animalColorField;

    @FindBy(id = "announce.animalCastrated1")
    protected FluentWebElement animalCastratedNoField;

    @FindBy(id = "announce.animalCastrated2")
    protected FluentWebElement animalCastratedMaybeField;

    @FindBy(id = "announce.animalCastrated3")
    protected FluentWebElement animalCastratedYesField;

    @FindBy(id = "announce_form_animal_age")
    protected FluentWebElement animalAgeField;

    @FindBy(id = "announce_form_description")
    protected FluentWebElement descriptionField;

    @FindBy(id = "announce-submit-btn")
    protected FluentWebElement submitButton;

    @FindBy(id = "cropper-modal-mainPhotoCropper")
    protected FluentWebElement mainCropperModal;

    @FindBy(id = "cropper-modal-toggle-mainPhotoCropper")
    protected FluentWebElement mainCropperModalToggleButton;

    @FindBy(id = "cropper-input-mainPhotoCropper")
    protected FluentWebElement mainCropperInput;

    @FindBy(id = "cropper-submit-mainPhotoCropper")
    protected FluentWebElement mainCropperSubmitButton;

    @FindBy(id = "cropper-modal-secondPhotoCropper")
    protected FluentWebElement secondCropperModal;

    @FindBy(id = "cropper-modal-toggle-secondPhotoCropper")
    protected FluentWebElement secondCropperModalToggleButton;

    @FindBy(id = "cropper-input-secondPhotoCropper")
    protected FluentWebElement secondCropperInput;

    @FindBy(id = "cropper-submit-secondPhotoCropper")
    protected FluentWebElement secondCropperSubmitButton;

    @FindBy(id = "cropper-modal-thirdPhotoCropper")
    protected FluentWebElement thirdCropperModal;

    @FindBy(id = "cropper-modal-toggle-thirdPhotoCropper")
    protected FluentWebElement thirdCropperModalToggleButton;

    @FindBy(id = "cropper-input-thirdPhotoCropper")
    protected FluentWebElement thirdCropperInput;

    @FindBy(id = "cropper-submit-thirdPhotoCropper")
    protected FluentWebElement thirdCropperSubmitButton;

    public void fillForm(
            String title,
            String animalName,
            String description,
            String zipcode,
            String state,
            String city,
            String neighborhood,
            String street,
            String number,
            FluentWebElement animalType,
            FluentWebElement animalGender,
            String animalSize,
            String animalColor,
            FluentWebElement animalCastrated,
            String animalAge
    ) {
        this.fillAnnounceFormTop(title, animalName);

        this.fillAnimalForm(
                animalType,
                animalGender,
                animalSize,
                animalColor,
                animalCastrated,
                animalAge
        );

        this.fillAddressForm(
                zipcode,
                state,
                city,
                neighborhood,
                street,
                number
        );

        this.fillAnnounceFormBottom(description);
    }

    public void fillAddressForm(
            String zipcode,
            String state,
            String city,
            String neighborhood,
            String street,
            String number
    ) {
        this.submitButton.scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);

        this.zipcodeField.fill().withText(zipcode);
        this.descriptionField.click();

        await().atMost(15, TimeUnit.SECONDS).until(
                stateField
        ).text().not().equalTo("...");

        this.stateField.fill().withText(state);
        this.cityField.fill().withText(city);
        this.neighborhoodField.fill().withText(neighborhood);
        this.addressField.fill().withText(street);
        this.addressNumberField.fill().withText(number);
    }

    public void fillAnimalForm(
            FluentWebElement animalType,
            FluentWebElement animalGender,
            String animalSize,
            String animalColor,
            FluentWebElement animalCastrated,
            String animalAge
    ) {
        animalType.click();
        animalGender.click();
        this.animalSizeField.fillSelect().withText(animalSize);
        this.animalColorField.fillSelect().withText(animalColor);
        animalCastrated.click();
        this.animalAgeField.fillSelect().withText(animalAge);
    }

    public void fillAnnounceFormTop(
        String title,
        String animalName
    ) {
        this.titleField.fill().withText(title);
        this.animalNameField.fill().withText(animalName);
    }
    public void fillAnnounceFormBottom(
        String description
    ) {
        this.descriptionField.fill().withText(description);
    }

    public void fillCropper(
        FluentWebElement modal,
        FluentWebElement modalToggleButton,
        FluentWebElement submitButton,
        FluentWebElement input,
        String filePath
    ) {
        modalToggleButton.click();
        await().atMost(15, TimeUnit.SECONDS).until(modal).displayed();

        input.fill().with(filePath);

        await().explicitlyFor(4, TimeUnit.SECONDS);

        submitButton.click();

        await().atMost(15, TimeUnit.SECONDS).until(modal).not().displayed();
    }

    public void fillMainCropper(String filePath) {
        this.fillCropper(
            this.mainCropperModal,
            this.mainCropperModalToggleButton,
            this.mainCropperSubmitButton,
            this.mainCropperInput,
            filePath
        );
    }
    public void fillSecondCropper(String filePath) {
        this.fillCropper(
                this.thirdCropperModal,
                this.thirdCropperModalToggleButton,
                this.thirdCropperSubmitButton,
                this.thirdCropperInput,
                filePath
        );
    }
    public void fillThirdCropper(String filePath) {
        this.fillCropper(
            this.secondCropperModal,
            this.secondCropperModalToggleButton,
            this.secondCropperSubmitButton,
            this.secondCropperInput,
            filePath
        );
    }
}
