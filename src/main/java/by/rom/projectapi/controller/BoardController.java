package by.rom.projectapi.controller;

import by.rom.projectapi.model.dto.BoardDto;
import by.rom.projectapi.service.BoardService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@Api(tags = {"Board"})
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @ApiOperation(value = "Get trello board by name or get all boards.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the board"),
            @ApiResponse(code = 404, message = "Board not found")
    })
    @GetMapping
    public List<BoardDto> getBoard(@ApiParam(value = "name of board") @RequestParam (required = false) String name){
        return boardService.getBoard(name);
    }

    @ApiOperation(value = "Create new board with description.", response = BoardDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Board is created"),
            @ApiResponse(code = 400, message = "Missing or invalid request body")
    })
    @PostMapping
    public BoardDto createBoard(@ApiParam(value = "name of board", example = "My new board", required = true) @RequestParam String name,
                                @ApiParam(value = "description of board", example = "My new description") @RequestParam(required = false) String desc){
       return boardService.createBoard(name, desc);
    }

    @ApiOperation(value = "Update boards name.", response = BoardDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Board is changed"),
            @ApiResponse(code = 400, message = "Missing or invalid request body")
    })
    @PutMapping("/{id}")
    public BoardDto updateBoard(@ApiParam(value = "unique id of board", example = "1", required = true) @PathVariable Long id,
                                @ApiParam(value = "name of board for update", example = "Update name", required = true) @RequestParam String name){
        return boardService.update(id, name);
    }

    @ApiOperation(value = "Delete trello board by id.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Board was deleted"),
            @ApiResponse(code = 404, message = "Board not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@ApiParam(value = "unique id of board for delete", example = "1", required = true) @PathVariable(name = "id") Long id){
        boardService.deleteBoard(id);
        return new ResponseEntity<>("Delete was successful", HttpStatus.OK);
    }
}
