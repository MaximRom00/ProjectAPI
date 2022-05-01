package by.rom.projectapi.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
public class BoardDto {

//    @JsonProperty("id")
//    @JsonIgnore
//    private String id;

//    @NotNull
    @JsonProperty("name")
    private String name;

//    @NotNull
    @JsonProperty("shortLink")
    private String shortLink;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("id")
    private String idBoard;

    @JsonProperty("idMemberCreator")
    private String idMemberCreator;

    @NotNull
//    @JsonProperty("created_at")
    private LocalDateTime createAt;
}
