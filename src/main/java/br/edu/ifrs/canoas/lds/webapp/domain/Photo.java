package br.edu.ifrs.canoas.lds.webapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Photo {

    private int id;
    private String name;
    private String path;

}
