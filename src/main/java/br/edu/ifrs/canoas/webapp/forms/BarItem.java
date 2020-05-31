package br.edu.ifrs.canoas.webapp.forms;

import lombok.Data;

@Data
public class BarItem {
    private String href;
    private Long size = 0l;
    private boolean active = false;

    public BarItem(String href) {
        this.href = href;
    }

    public void set(String uri) {
        this.active = href == null
                ? uri.equals("/") || uri.equals("")
                : uri.startsWith(href);
    }
}
