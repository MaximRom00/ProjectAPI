package by.rom.projectapi.controller;

import by.rom.projectapi.model.dto.TaskDto;
import by.rom.projectapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDto> getTask(String name){
        return taskService.getTask(name);
    }

    @PostMapping("/{id}")
    public TaskDto createTask(@PathVariable Long id, String name, @RequestParam(required = false) String desc){
        return taskService.createTask(id, name, desc);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "id") Long id){
        taskService.deleteTask(id);
        return new ResponseEntity<>("Delete was successful", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestParam String name, @RequestParam(required = false) String desc){
        return taskService.updateTask(id, name, desc);
    }
}
