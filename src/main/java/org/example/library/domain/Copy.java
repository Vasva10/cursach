package org.example.library.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "copies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Copy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь с Book
    @ManyToOne(optional = false)
    @JoinColumn(name="book_id")
    private Book book;

    @Column(nullable = false)
    private String inventoryNumber;

    @Column(nullable = false)
    private String status; // например: "AVAILABLE", "BORROWED"
}
