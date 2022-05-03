package by.rom.projectapi.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString(exclude = "card")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Builder.Default
    private LocalDateTime createAt = LocalDateTime.now();

    private String description;

    private String idTask;

    @ManyToOne
    private Card card;
}
