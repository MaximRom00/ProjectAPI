package by.rom.projectapi.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString(exclude = "cards")

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

//    @Column(nullable = false)
//    private String shortLink;

    private String idBoard;

    private String description;


    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now();

    @OneToMany
    @JoinColumn(name = "board_id")
    private List<Card> cards = new ArrayList<>();
}
