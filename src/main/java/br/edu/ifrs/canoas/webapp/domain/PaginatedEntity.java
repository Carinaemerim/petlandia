package br.edu.ifrs.canoas.webapp.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PaginatedEntity<T> {
    List<T> data;
    int currentPage;
    int totalResults;
    int pageLength;

    public double getTotalPages() {
        if (totalResults == 0) {
            return 0;
        }

        return Math.ceil(totalResults / (double) pageLength);
    }
}