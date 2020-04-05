package br.edu.ifrs.canoas.webapp.forms;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AnnounceFilterForm {

    private int page = 1;
    private List<Long> animalAge = new ArrayList<>();
    private List<Long> animalGender = new ArrayList<>();
    private List<Long> animalType = new ArrayList<>();
    private List<Long> animalSize = new ArrayList<>();
    private List<Long> animalColor = new ArrayList<>();
    private List<Long> animalCastrated = new ArrayList<>();

    public int getPage() {
        return page < 1 ? 1 : page;
    }
}
