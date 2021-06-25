package br.edu.ifrs.canoas.webapp.web.page.user;

import br.edu.ifrs.canoas.webapp.web.page.GenericPage;
import lombok.Data;
import lombok.Getter;
import org.fluentlenium.core.annotation.PageUrl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

@Data
@PageUrl("/user/create")
public class UserCreatePage extends GenericPage {
    @FindBy(id = "btn-home-user-create")
    private FluentWebElement buttonHomeUserCreate;

    @FindBy(id = "btn-home-user-login")
    private FluentWebElement buttonHomeUserLogin;

    @FindBy(id = "user_form_username")
    private FluentWebElement usernameField;

    @FindBy(id = "user_form_cpf")
    private FluentWebElement cpfField;

    @FindBy(id = "user_form_password")
    private FluentWebElement passwordField;

    @FindBy(id = "user_form_password_confirm")
    private FluentWebElement passwordConfirmField;

    @FindBy(id = "user_form_zipCode")
    private FluentWebElement zipcodeField;

    @FindBy(id = "user_form_state")
    private FluentWebElement stateField;

    @FindBy(id = "user_form_city")
    private FluentWebElement cityField;

    @FindBy(id = "user_form_neighborhood")
    private FluentWebElement neighborhoodField;

    @FindBy(id = "user_form_address")
    private FluentWebElement addressField;

    @FindBy(id = "user_form_address_number")
    private FluentWebElement addressNumberField;

    @FindBy(id = "user_form_name")
    private FluentWebElement contactNameField;

    @FindBy(id = "user.form.email")
    private FluentWebElement contactEmailField;

    @FindBy(id = "user_form_residentialPhone")
    private FluentWebElement contactResidentialPhoneField;

    @FindBy(id = "user.form.celPhone")
    private FluentWebElement contactCelPhoneField;

    @FindBy(id = "user.animalType1")
    private FluentWebElement animalTypeCatField;

    @FindBy(id = "user.animalType2")
    private FluentWebElement animalTypeDogField;

    @FindBy(id = "user.animalGender1")
    private FluentWebElement animalGenderFemale;

    @FindBy(id = "user.animalGender2")
    private FluentWebElement animalGenderMale;

    @FindBy(id = "user_form_animal_size")
    private FluentWebElement animalSizeField;

    @FindBy(id = "user_form_animal_color")
    private FluentWebElement animalColorField;

    @FindBy(id = "user.animalCastrated1")
    private FluentWebElement animalCastratedNoField;

    @FindBy(id = "user.animalCastrated2")
    private FluentWebElement animalCastratedMaybeField;

    @FindBy(id = "user.animalCastrated3")
    private FluentWebElement animalCastratedYesField;

    @FindBy(id = "user_form_animal_age")
    private FluentWebElement animalAgeField;

    @FindBy(id = "btn-submit")
    private FluentWebElement submitButton;

    public void fillForm(
        String username,
        String cpf,
        String password,
        String passwordConfirm,
        String zipcode,
        String state,
        String city,
        String neighborhood,
        String street,
        String number,
        String contactName,
        String contactEmail,
        String contactResidentialPhone,
        String contactCelPhone,
        FluentWebElement animalType,
        FluentWebElement animalGender,
        String animalSize,
        String animalColor,
        FluentWebElement animalCastrated,
        String animalAge
    ) {
        this.fillUserForm(
            username,
            cpf,
            password,
            passwordConfirm
        );

        this.fillAddressForm(
            zipcode,
            state,
            city,
            neighborhood,
            street,
            number
        );

        this.fillContactForm(
            contactName,
            contactEmail,
            contactResidentialPhone,
            contactCelPhone
        );

        this.fillAnimalForm(
            animalType,
            animalGender,
            animalSize,
            animalColor,
            animalCastrated,
            animalAge
        );
    }

    public void fillUserForm(
        String username,
        String cpf,
        String password,
        String passwordConfirm
    ) {
        this.usernameField.fill().withText(username);
        this.cpfField.fill().withText(cpf);
        this.passwordField.fill().withText(password);
        this.passwordConfirmField.fill().withText(passwordConfirm);

    }

    public void fillAddressForm(
        String zipcode,
        String state,
        String city,
        String neighborhood,
        String street,
        String number
    ) {
        this.zipcodeField.fill().withText(zipcode);
        this.usernameField.click();

        await().atMost(15, TimeUnit.SECONDS).until(
                stateField
        ).text().not().equalTo("...");

        this.stateField.fill().withText(state);
        this.cityField.fill().withText(city);
        this.neighborhoodField.fill().withText(neighborhood);
        this.addressField.fill().withText(street);
        this.addressNumberField.fill().withText(number);
    }
    public void fillContactForm(
        String contactName,
        String contactEmail,
        String contactResidentialPhone,
        String contactCelPhone
    ) {
        this.contactNameField.fill().withText(contactName);
        this.contactEmailField.fill().withText(contactEmail);
        this.contactResidentialPhoneField.fill().withText(contactResidentialPhone);
        this.contactCelPhoneField.fill().withText(contactCelPhone);
    }
    public void fillAnimalForm(
        FluentWebElement animalType,
        FluentWebElement animalGender,
        String animalSize,
        String animalColor,
        FluentWebElement animalCastrated,
        String animalAge
    ) {
        this.submitButton.scrollIntoView();
        await().explicitlyFor(4, TimeUnit.SECONDS);

        animalType.click();

        animalGender.click();
        this.animalSizeField.fillSelect().withText(animalSize);
        this.animalColorField.fillSelect().withText(animalColor);
        animalCastrated.click();
        this.animalAgeField.fillSelect().withText(animalAge);
    }
}