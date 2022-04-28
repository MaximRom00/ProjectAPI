package by.rom.projectapi.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TaskDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Builder.Default
    @JsonProperty("created_at")
    private LocalDateTime createAt = LocalDateTime.now();

    @NotNull
    private String description;
}
