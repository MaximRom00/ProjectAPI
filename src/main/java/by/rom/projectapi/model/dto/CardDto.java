package by.rom.projectapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CardDto {

    @NotNull
    private String name;

    @JsonProperty("id")
    private String idList;

    @NotNull
    @Builder.Default
    @JsonProperty("created_at")
    private LocalDateTime createAt = LocalDateTime.now();

    private List<TaskDto> tasks = new ArrayList<>();

    private BoardDto boardDto;
}
