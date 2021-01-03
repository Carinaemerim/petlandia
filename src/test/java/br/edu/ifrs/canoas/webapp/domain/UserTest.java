package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.domain.validation.UserCreateGroup;
import br.edu.ifrs.canoas.webapp.domain.validation.UserEditGroup;
import br.edu.ifrs.canoas.webapp.helper.UserHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest extends BaseTest<User> {
    @Test
    public void testValidUser() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testValidUserNullOptionals() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCelPhone(null);
        user.setAddressNumber(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testNullUserName() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setUsername(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankUserName() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setUsername("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeUsername() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setUsername(RandomStringUtils.randomAlphabetic(1));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.username.size", violations);
    }

    @Test
    public void testMaxSizeUsername() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setUsername(RandomStringUtils.randomAlphabetic(41));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.username.size", violations);

    }

    @Test
    public void testNullPassword() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setPassword(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankPassword() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setPassword("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizePassword() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setPassword(RandomStringUtils.randomAlphabetic(5));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.password.size", violations);
    }

    @Test
    public void testMaxSizePassword() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setPassword(RandomStringUtils.randomAlphabetic(21));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.password.size", violations);
    }

    @Test
    public void testNullName() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setName(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankName() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setName("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeName() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setName(RandomStringUtils.randomAlphabetic(2));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.name.size", violations);
    }

    @Test
    public void testMaxSizeName() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setName(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.name.size", violations);
    }

    @Test
    public void testNullCPF() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCpf(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankCPF() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCpf("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeCPF() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCpf(RandomStringUtils.randomNumeric(13));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.cpf.size", violations);
    }

    @Test
    public void testMaxSizeCPF() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCpf(RandomStringUtils.randomNumeric(15));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.cpf.size", violations);
    }

    @Test
    public void testInvalidCPF() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCpf("111.111.111-11");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.cpf.invalid", violations);
    }

    @Test
    public void testNullEmail() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setEmail(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankEmail() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setEmail(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeEmail() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setEmail(RandomStringUtils.randomAlphabetic(5));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.email.size", violations);
    }

    @Test
    public void testMaxSizeEmail() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setEmail(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.email.size", violations);
    }

    @Test
    public void testInvalidEmail() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setEmail("invalid.1234");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.email.invalid", violations);
    }

    @Test
    public void testNullAddress() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAddress(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankAddress() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAddress("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeAddress() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAddress(RandomStringUtils.randomAlphabetic(2));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.address.size", violations);
    }

    @Test
    public void testMaxSizeAddress() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAddress(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.address.size", violations);
    }

    @Test
    public void testNullZipCode() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setZipCode(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankZipCode() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setZipCode("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeZipCode() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setZipCode(RandomStringUtils.randomAlphabetic(8));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.zipcode.size", violations);
    }

    @Test
    public void testMaxSizeZipCode() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setZipCode(RandomStringUtils.randomAlphabetic(10));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.zipcode.size", violations);
    }

    @Test
    public void testZipCodePattern() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setZipCode("123456789");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.zipcode.pattern", violations);
    }

    @Test
    public void testNullNeighborhood() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setNeighborhood(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankNeighborhood() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setNeighborhood("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeNeighborhood() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setNeighborhood(RandomStringUtils.randomAlphabetic(2));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.neighborhood.size", violations);
    }

    @Test
    public void testMaxSizeNeighborhood() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setNeighborhood(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.neighborhood.size", violations);
    }

    @Test
    public void testNullCity() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCity(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankCity() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCity("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeCity() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCity(RandomStringUtils.randomAlphabetic(2));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.city.size", violations);
    }

    @Test
    public void testMaxSizeCity() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCity(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.city.size", violations);
    }

    @Test
    public void testNullState() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setState(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankState() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setState("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeState() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setState(RandomStringUtils.randomAlphabetic(1));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.state.size", violations);
    }

    @Test
    public void testMaxSizeState() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setState(RandomStringUtils.randomAlphabetic(4));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.state.size", violations);
    }

    @Test
    public void testMaxSizeAddressNumber() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAddressNumber(RandomStringUtils.randomNumeric(6));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.addressNumber.size", violations);
    }

    @Test
    public void testNullResidentialPhone() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setResidentialPhone(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankResidentialPhone() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setResidentialPhone("");

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeResidentialPhone() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setResidentialPhone(RandomStringUtils.randomNumeric(13));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.residential.size", violations);
    }

    @Test
    public void testMaxSizeResidentialPhone() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setResidentialPhone(RandomStringUtils.randomNumeric(16));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.residential.size", violations);
    }

    @Test
    public void testMinSizeCelPhone() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCelPhone(RandomStringUtils.randomNumeric(13));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.celphone.size", violations);
    }

    @Test
    public void testMaxSizeCelPhone() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setCelPhone(RandomStringUtils.randomNumeric(16));

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("validation.user.celphone.size", violations);
    }

    @Test
    public void testNullAnimalGender() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAnimalGender(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullAnimalType() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAnimalType(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullAnimalSize() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAnimalSize(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullAnimalColor() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAnimalColor(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullAnimalCastrated() {
        User user = UserHelper.createUser();
        Set<ConstraintViolation<User>> violations;

        user.setAnimalCastrated(null);

        violations = this.validator.validate(user, UserCreateGroup.class, UserEditGroup.class);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testGetAvatarHash() throws NoSuchAlgorithmException {
        User user = UserHelper.createUser();

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(user.getEmail().toLowerCase().trim().getBytes());
        byte[] digest = md.digest();
        String avatarHash = DatatypeConverter.printHexBinary(digest).toLowerCase();

        assertThat(user.getAvatarHash()).isEqualTo(avatarHash);
    }

    @Test
    public void testGetAvatar() {
        User user = UserHelper.createUser();
        String avatarHash = user.getAvatarHash();

        user.setAvatar(avatarHash);

        String avatar = "https://www.gravatar.com/avatar/" + avatarHash + "?d=mp&s=200";

        assertThat(user.getAvatar()).isEqualTo(avatar);
    }
}
