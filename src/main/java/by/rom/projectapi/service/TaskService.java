package by.rom.projectapi.service;


import by.rom.projectapi.client.TrelloClient;
import by.rom.projectapi.converter.TaskConverter;
import by.rom.projectapi.exception.BadRequestException;
import by.rom.projectapi.exception.NotFoundException;
import by.rom.projectapi.model.Card;
import by.rom.projectapi.model.Task;
import by.rom.projectapi.model.dto.TaskDto;
import by.rom.projectapi.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskConverter taskConverter;

    private final TrelloClient trelloClient;

    private final TaskRepository taskRepository;

    private final CardService cardService;

    public TaskService(TaskConverter taskConverter, TrelloClient trelloClient, TaskRepository taskRepository, CardService cardService) {
        this.taskConverter = taskConverter;
        this.trelloClient = trelloClient;
        this.taskRepository = taskRepository;
        this.cardService = cardService;
    }

    public List<TaskDto> getTask(String name) {

        if (name == null){
            return taskRepository.findAll()
                    .stream()
                    .map(taskConverter::toDto)
                    .collect(Collectors.toList());
        }
        else{
            return  taskRepository
                    .findByNameContainingIgnoreCase(name)
                    .stream()
                    .map(taskConverter::toDto)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::of))
                    .filter(pr -> !pr.isEmpty())
                    .orElseThrow(() -> new NotFoundException(String.format("Task with such name: %s , didn't found", name)));
        }
    }


    public TaskDto createTask(Long id, String name, String desc) {
        Card card = cardService.getCardById(id);

        card.getTasks().stream()
                .filter(task -> card.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresent(task->{
                    throw new BadRequestException("Task with such name exists: " + name);
                });

        TaskDto taskDto = trelloClient.saveTask(taskConverter.toDto(Task.builder()
                .name(name)
                .card(card)
                .description(desc)
                .createAt(LocalDateTime.now())
                .build()));

        Task task = taskConverter.fromDto(taskDto);
        task.setCard(card);

        taskRepository.save(task);

        return taskDto;
    }


    public void deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        task.ifPresentOrElse(
                (board -> taskRepository.deleteById(id)),
                ()-> {
                    throw new NotFoundException(String.format("Task with such id: %d , didn't found", id)); }
        );

        task.ifPresent(board -> trelloClient.deleteTask(taskConverter.toDto(task.get())));
    }

    public TaskDto updateTask(Long id, String name, String desc){
        taskRepository.findAll()
                .stream()
                .filter(task -> task.getName().equalsIgnoreCase(name))
                .findAny()
                .ifPresent(card -> {
                    throw new BadRequestException("Task with such name: " + name + " exists. Name should be unique.");
                });

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Task with such id: %d , didn't found", id)));

        task.setName(name);
        task.setDescription(desc);

        taskRepository.save(task);

        TaskDto taskDto = taskConverter.toDto(task);

        trelloClient.updateTask(taskDto);

        return taskDto;
    }
}
