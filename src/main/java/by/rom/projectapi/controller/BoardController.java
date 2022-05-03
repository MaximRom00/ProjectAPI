package by.rom.projectapi.controller;

import by.rom.projectapi.model.dto.BoardDto;
import by.rom.projectapi.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<BoardDto> getBoard(@RequestParam (required = false) String name){
        return boardService.getBoard(name);
    }

    @PostMapping
    public BoardDto createBoard(@RequestParam String name, @RequestParam(required = false) String desc){
       return boardService.createBoard(name, desc);
    }

    @PutMapping("/{id}")
    public BoardDto updateBoard(@PathVariable Long id, @RequestParam String name){
        return boardService.update(id, name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable(name = "id") Long id){
        boardService.deleteBoard(id);
        return new ResponseEntity<>("Delete was successful", HttpStatus.OK);
    }
}
