package by.rom.projectapi.controller;

import by.rom.projectapi.model.dto.BoardDto;
import by.rom.projectapi.model.dto.TaskDto;
import by.rom.projectapi.service.TaskService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@Api(tags = {"Task"})
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation(value = "Get trello task from card by name", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the task"),
            @ApiResponse(code = 404, message = "Task not found")
    })
    @GetMapping
    public List<TaskDto> getTask(@ApiParam(value = "name of task") @RequestParam String name){
        return taskService.getTask(name);
    }

    @ApiOperation(value = "Create new task in card with description.", response = TaskDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Card is created"),
            @ApiResponse(code = 400, message = "Missing or invalid request body")
    })
    @PostMapping("/{id}")
    public TaskDto createTask(@ApiParam(value = "id of card", example = "1", required = true) @PathVariable Long id,
                              @ApiParam(value = "name of task", example = "My new task", required = true) @RequestParam String name,
                              @ApiParam(value = "description of task", example = "Tasks description") @RequestParam(required = false) String desc){
        return taskService.createTask(id, name, desc);
    }

    @ApiOperation(value = "Delete trello task by id.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task was deleted"),
            @ApiResponse(code = 404, message = "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@ApiParam(value = "unique id of task for delete", example = "1", required = true) @PathVariable(name = "id") Long id){
        taskService.deleteTask(id);
        return new ResponseEntity<>("Delete was successful", HttpStatus.OK);
    }

    @ApiOperation(value = "Update task name and description.", response = TaskDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Task is changed"),
            @ApiResponse(code = 400, message = "Missing or invalid request body")
    })
    @PutMapping("/{id}")
    public TaskDto updateTask(@ApiParam(value = "unique id of task", example = "1", required = true) @PathVariable Long id,
                              @ApiParam(value = "name of task for update", example = "Update name", required = true) @RequestParam String name,
                              @ApiParam(value = "description of task for update", example = "Update description") @RequestParam(required = false) String desc){
        return taskService.updateTask(id, name, desc);
    }
}
