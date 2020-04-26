package br.edu.ifrs.canoas.webapp.forms;

import lombok.Data;

@Data
public class ManagerSidebarItem {
    private String href;
    private Long size = 0l;
    private boolean active = false;

    public ManagerSidebarItem(String href) {
        this.href = href;
    }

    public void set(String uri) {
        this.active = uri.startsWith(href);
    }
}
