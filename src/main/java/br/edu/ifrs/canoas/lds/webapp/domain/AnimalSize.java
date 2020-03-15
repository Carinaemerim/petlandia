package br.edu.ifrs.canoas.lds.webapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class AnimalSize {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
}