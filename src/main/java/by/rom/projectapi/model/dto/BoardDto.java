package by.rom.projectapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BoardDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("shortLink")
    private String shortLink;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("id")
    private String idBoard;

    @JsonProperty("idMemberCreator")
    private String idMemberCreator;

    @NotNull
    private LocalDateTime createAt;

    @JsonProperty("lists")
    private List<CardDto> lists;
}
