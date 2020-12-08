package br.edu.ifrs.canoas.webapp.domain;

import br.edu.ifrs.canoas.webapp.enums.ReportStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.WAITING_REVIEW;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime ratedAt;

    @ManyToOne(fetch= FetchType.EAGER)
    private User ratedBy;

    @NotNull
    @ManyToOne(fetch= FetchType.EAGER)
    private User reportBy;

    @NotNull
    @ManyToOne(fetch= FetchType.EAGER)
    private Announce announce;

    @ManyToOne(fetch= FetchType.EAGER)
    private Comment comment;

    @NotNull (message="{field.required}")
    @Size(min = 10, max = 250, message="{validation.report.message.size}")
    private String message;
}
