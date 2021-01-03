package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.enums.AnnounceStatus;

public class AnnounceHelper {
    public static Announce createAnnounce() {
        return Announce.builder()
                .title("Dengoso e carente")
                .name("Flovis")
                .animalAge(ScoreHelper.createScore("PUPPY", 2.0, AnimalAge.class))
                .animalCastrated(ScoreHelper.createScore("YES", 2.0, AnimalCastrated.class))
                .animalColor(ScoreHelper.createScore("WHITE", 2.0, AnimalColor.class))
                .animalSize(ScoreHelper.createScore("MEDIUM", 2.0, AnimalSize.class))
                .animalType(ScoreHelper.createScore("DOG", 2.0, AnimalType.class))
                .animalGender(ScoreHelper.createScore("FEMALE", 2.0, AnimalGender.class))
                .status(AnnounceStatus.ACTIVE)
                .description("Foi encontrado sozinho")
                .address("Rua tramandaí")
                .state("RS")
                .city("Canoas")
                .neighborhood("Estância Velha")
                .addressNumber("578")
                .zipCode("92032-360")
                .user(UserHelper.createUser())
                .mainPhoto("main photo")
                .secondPhoto("second photo")
                .thirdPhoto("third photo")
                .build();
    }
}
