package by.rom.projectapi.converter;

import by.rom.projectapi.model.Card;
import by.rom.projectapi.model.Task;
import by.rom.projectapi.model.dto.CardDto;
import by.rom.projectapi.model.dto.TaskDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskConverter {

    private final CardConverter cardConverter;

    public TaskConverter(CardConverter cardConverter) {
        this.cardConverter = cardConverter;
    }


    public TaskDto toDto(Task task){
        return TaskDto.builder()
                .name(task.getName())
                .description(task.getDescription())
                .cardDto(cardConverter.toDto(task.getCard()))
                .createAt(task.getCreateAt())
                .idTask(task.getIdTask())
                .build();
    }

    public Task fromDto(TaskDto taskDto) {
        return Task.builder()
                .description(taskDto.getDescription())
                .createAt(LocalDateTime.now())
                .idTask(taskDto.getIdTask())
                .name(taskDto.getName())
                .build();
    }

}
