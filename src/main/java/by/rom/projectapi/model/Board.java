package by.rom.projectapi.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString(exclude = "cards")
@EqualsAndHashCode(of = {"name", "description"})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String shortLink;

    private String idBoard;

    private String description;

    private String idMemberCreator;

    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now();

    @OneToMany
    @JoinColumn(name = "board_id")
    private List<Card> cards = new ArrayList<>();
}
