package by.rom.projectapi.model.dto;

import by.rom.projectapi.model.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
//ProjectCard
public class CardDto {


    private Long id;

    @NotNull
    private String name;

    private int idList;

    @NotNull
    @Builder.Default
    @JsonProperty("created_at")
    private LocalDateTime createAt = LocalDateTime.now();

    private List<TaskDto> tasks = new ArrayList<>();

    private BoardDto boardDto;
}
