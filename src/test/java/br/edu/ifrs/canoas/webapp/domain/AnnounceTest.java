package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.MockAuthContext;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;
import br.edu.ifrs.canoas.webapp.helper.AnnounceHelper;
import br.edu.ifrs.canoas.webapp.service.UserDetailsImplService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnounceTest extends BaseTest<Announce> {

    @MockBean
    UserDetailsImplService userDetailsImplService;

    @Autowired
    protected MockAuthContext mockAuthContext;

    @AfterEach
    public void tearDown() {
        this.mockAuthContext.tearDown();
    }

    @Test
    public void testValidAnnounce() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        violations = this.validator.validate(announce);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testValidAnnounceNullOptionals() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAddressNumber(null);
        announce.setSecondPhoto(null);
        announce.setThirdPhoto(null);

        violations = this.validator.validate(announce);
        assertThat(violations).isEmpty();
    }

    @Test
    public void testNullTitle() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setTitle(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankTitle() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setTitle("");

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeTitle() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setTitle(RandomStringUtils.randomAlphabetic(3));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.title.size", violations);
    }

    @Test
    public void testMaxSizeTitle() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setTitle(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.title.size", violations);
    }

    @Test
    public void testNullName() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setName(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankName() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setName("");

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeName() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setName(RandomStringUtils.randomAlphabetic(1));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.name.size", violations);
    }

    @Test
    public void testMaxSizeName() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setName(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.name.size", violations);
    }

    @Test
    public void testNullAnimalAge() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAnimalAge(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullAnimalGender() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAnimalGender(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullAnimalType() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAnimalType(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullStatus() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setStatus(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullAnimalSize() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAnimalSize(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullAnimalColor() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAnimalColor(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullDescription() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setDescription(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankDescription() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setDescription("");

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeDescription() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setDescription(RandomStringUtils.randomAlphabetic(9));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.description.size", violations);
    }

    @Test
    public void testMaxSizeDescription() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setDescription(RandomStringUtils.randomAlphabetic(10001));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.description.size", violations);
    }

    @Test
    public void testNullAddress() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAddress(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankAddress() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAddress("");

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeAddress() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAddress(RandomStringUtils.randomAlphabetic(2));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.address.size", violations);
    }

    @Test
    public void testMaxSizeAddress() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAddress(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.address.size", violations);
    }

    @Test
    public void testNullZipCode() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setZipCode(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankZipCode() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setZipCode("");

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeZipCode() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setZipCode(RandomStringUtils.randomAlphabetic(8));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.zipcode.size", violations);
    }

    @Test
    public void testMaxSizeZipCode() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setZipCode(RandomStringUtils.randomAlphabetic(10));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.zipcode.size", violations);
    }

    @Test
    public void testZipCodePattern() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setZipCode("123456789");

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.zipcode.pattern", violations);
    }

    @Test
    public void testNullNeighborhood() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setNeighborhood(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankNeighborhood() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setNeighborhood("");

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeNeighborhood() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setNeighborhood(RandomStringUtils.randomAlphabetic(2));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.neighborhood.size", violations);
    }

    @Test
    public void testMaxSizeNeighborhood() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setNeighborhood(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.neighborhood.size", violations);
    }

    @Test
    public void testNullCity() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setCity(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankCity() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setCity("");

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeCity() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setCity(RandomStringUtils.randomAlphabetic(2));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.city.size", violations);
    }

    @Test
    public void testMaxSizeCity() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setCity(RandomStringUtils.randomAlphabetic(121));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.city.size", violations);
    }

    @Test
    public void testNullState() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setState(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testBlankState() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setState("");

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testMinSizeState() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setState(RandomStringUtils.randomAlphabetic(1));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.state.size", violations);
    }

    @Test
    public void testMaxSizeState() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setState(RandomStringUtils.randomAlphabetic(4));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.state.size", violations);
    }

    @Test
    public void testMaxSizeAddressNumber() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setAddressNumber(RandomStringUtils.randomNumeric(6));

        violations = this.validator.validate(announce);

        this.assertHasViolation("validation.announce.addressNumber.size", violations);
    }

    @Test
    public void testNullAnimalCastrated() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setTitle(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testNullUser() {
        Announce announce = AnnounceHelper.createAnnounce();
        Set<ConstraintViolation<Announce>> violations;

        announce.setTitle(null);

        violations = this.validator.validate(announce);

        this.assertHasViolation("field.required", violations);
    }

    @Test
    public void testCanModifyUnauthenticated() {
        Announce announce = AnnounceHelper.createAnnounce();

        this.mockAuthContext.mockAuthAnonymous();

        assertThat(announce.canModify()).isFalse();
    }

    @Test
    public void testCanModifyOwner() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.getUser().setId(MockAuthContext.USER_ID);

        assertThat(announce.canModify()).isTrue();
    }

    @Test
    public void testCanModifyAdmin() {
        this.mockAuthContext.mockAuthAdmin();

        Announce announce = AnnounceHelper.createAnnounce();

        assertThat(announce.canModify()).isTrue();
    }

    @Test
    public void testCanModifyNeitherOwnerNorAdmin() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();

        assertThat(announce.canModify()).isFalse();
    }

    @Test
    public void testCanModifyInactiveAnnounce() {
        this.mockAuthContext.mockAuthAdmin();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.getUser().setId(MockAuthContext.USER_ID);
        announce.setStatus(AnnounceStatus.INACTIVE);

        assertThat(announce.canModify()).isFalse();
    }

    @Test
    public void testIsModeratorRoleModerator() {
        this.mockAuthContext.mockAuthModerator();

        Announce announce = AnnounceHelper.createAnnounce();
        assertThat(announce.isModerator()).isTrue();
    }

    @Test
    public void testIsModeratorRoleAdmin() {
        this.mockAuthContext.mockAuthAdmin();

        Announce announce = AnnounceHelper.createAnnounce();
        assertThat(announce.isModerator()).isTrue();
    }

    @Test
    public void testIsModeratorRoleUser() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();
        assertThat(announce.isModerator()).isFalse();
    }

    @Test
    public void testIsAdminRoleAdmin() {
        this.mockAuthContext.mockAuthAdmin();

        Announce announce = AnnounceHelper.createAnnounce();
        assertThat(announce.isAdmin()).isTrue();
    }

    @Test
    public void testIsAdminRoleModerator() {
        this.mockAuthContext.mockAuthModerator();

        Announce announce = AnnounceHelper.createAnnounce();
        assertThat(announce.isAdmin()).isFalse();
    }

    @Test
    public void testIsAdminRoleUser() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();
        assertThat(announce.isAdmin()).isFalse();
    }

    @Test
    public void testIsOwnerUnauthenticated() {
        this.mockAuthContext.mockAuthAnonymous();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.getUser().setId(MockAuthContext.USER_ID);

        assertThat(announce.isOwner()).isFalse();
    }

    @Test
    public void testIsOwnerTrue() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.getUser().setId(MockAuthContext.USER_ID);
        assertThat(announce.isOwner()).isTrue();
    }

    @Test
    public void testIsOwnerFalse() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();
        assertThat(announce.isOwner()).isFalse();
    }

    @Test
    public void testCanApproveTrue() {
        this.mockAuthContext.mockAuthAdmin();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.setStatus(AnnounceStatus.WAITING_REVIEW);

        assertThat(announce.canApprove()).isTrue();
    }

    @Test
    public void testCanApproveNotModerator() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.setStatus(AnnounceStatus.WAITING_REVIEW);

        assertThat(announce.canApprove()).isFalse();
    }

    @Test
    public void testCanApproveNotWaitingReview() {
        this.mockAuthContext.mockAuthAdmin();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.setStatus(AnnounceStatus.ACTIVE);

        assertThat(announce.canApprove()).isFalse();
    }

    @Test
    public void testCanEditTrue() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.getUser().setId(MockAuthContext.USER_ID);
        announce.setStatus(AnnounceStatus.ACTIVE);

        assertThat(announce.canEdit()).isTrue();
    }

    @Test
    public void testCanEditNotOwner() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.setStatus(AnnounceStatus.ACTIVE);

        assertThat(announce.canEdit()).isFalse();
    }

    @Test
    public void testCanEditNotActiveAnnounce() {
        this.mockAuthContext.mockAuthUser();

        Announce announce = AnnounceHelper.createAnnounce();
        announce.getUser().setId(MockAuthContext.USER_ID);
        announce.setStatus(AnnounceStatus.WAITING_REVIEW);

        assertThat(announce.canEdit()).isFalse();
    }

    @Test
    public void testCanRemoveTrue() {
        this.mockAuthContext.mockAuthAdmin();

        Announce announce = AnnounceHelper.createAnnounce();

        assertThat(announce.canRemove()).isTrue();
    }

    @Test
    public void testCanRemoveFalse() {
        this.mockAuthContext.mockAuthAnonymous();

        Announce announce = AnnounceHelper.createAnnounce();

        assertThat(announce.canRemove()).isFalse();
    }
}
