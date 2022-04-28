package by.rom.projectapi.converter;

import by.rom.projectapi.model.Task;
import by.rom.projectapi.model.dto.TaskDto;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {

    public TaskDto toDto(Task task){
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .createAt(task.getCreateAt())
                .build();
    }
}
