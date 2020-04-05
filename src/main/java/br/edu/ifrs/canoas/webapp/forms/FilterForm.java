package br.edu.ifrs.canoas.webapp.forms;

import lombok.Data;

import java.util.List;

@Data
public class FilterForm<T> {
    private String id;
    private String label;
    private List<T> options;
}
