package br.edu.ifrs.canoas.webapp.helper;

import br.edu.ifrs.canoas.webapp.domain.*;
import br.edu.ifrs.canoas.webapp.enums.Role;

public class UserHelper {
    public static User createUser() {
        return User.builder()
                .address("Rua tramandaí")
                .state("RS")
                .city("Canoas")
                .neighborhood("Estância Velha")
                .addressNumber("578")
                .zipCode("92032-360")
                .celPhone("(51) 998440019")
                .email("carina.emerim@gmail.com")
                .avatar(null)
                .password("123456")
                .residentialPhone("(051) 34733473")
                .name("Carina Emerim")
                .username("carina.emerim")
                .cpf("021.570.780-02")
                .active(true)
                .role(Role.ROLE_ADMIN)
                .animalAge(ScoreHelper.createScore("PUPPY", 2.0, AnimalAge.class))
                .animalCastrated(ScoreHelper.createScore("YES", 2.0, AnimalCastrated.class))
                .animalColor(ScoreHelper.createScore("WHITE", 2.0, AnimalColor.class))
                .animalSize(ScoreHelper.createScore("MEDIUM", 2.0, AnimalSize.class))
                .animalType(ScoreHelper.createScore("DOG", 2.0, AnimalType.class))
                .animalGender(ScoreHelper.createScore("FEMALE", 2.0, AnimalGender.class))
                .build();
    }
}
