package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.enums.AnimalSizeEnum;
import br.edu.ifrs.canoas.webapp.interfaces.FilterOption;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class AnimalSize implements FilterOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AnimalSizeEnum name;
}