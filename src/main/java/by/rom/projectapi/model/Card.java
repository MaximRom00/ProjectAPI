package by.rom.projectapi.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "card")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(nullable = false)
//    @Builder.Default
    private int idList;

    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now();

    @ManyToOne
    private Board board;

    @OneToMany
    @JoinColumn(name = "card_id")
    private List<Task> tasks = new ArrayList<>();
}
