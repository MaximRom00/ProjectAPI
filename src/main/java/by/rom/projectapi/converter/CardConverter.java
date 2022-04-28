package by.rom.projectapi.converter;

import by.rom.projectapi.model.Card;
import by.rom.projectapi.model.dto.TaskDto;
import by.rom.projectapi.model.dto.CardDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardConverter {

    private final TaskConverter taskConverter;

    private final BoardConverter boardConverter;

    public CardConverter(TaskConverter taskConverter, BoardConverter boardConverter) {
        this.taskConverter = taskConverter;
        this.boardConverter = boardConverter;
    }

    public CardDto toDto(Card card){
        List<TaskDto> tasks = null;

        if (card.getTasks() != null){
             tasks = card
                    .getTasks()
                    .stream()
                    .map(taskConverter::toDto)
                    .collect(Collectors.toList());
        }

        return CardDto.builder()
                .id(card.getId())
                .name(card.getName())
                .createAt(card.getCreateAt())
                .boardDto(boardConverter.toDto(card.getBoard()))
                .idList(card.getIdList())
                .tasks(tasks)
                .build();
    }
}
