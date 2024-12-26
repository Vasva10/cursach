package org.example.library.domain;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "issues")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Copy copy;

    @ManyToOne
    private User user;

    private LocalDate issueDate;
    private LocalDate returnDate;
    private Boolean isReturned;
}
