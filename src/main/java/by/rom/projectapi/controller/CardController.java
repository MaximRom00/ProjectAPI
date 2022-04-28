package by.rom.projectapi.controller;

import by.rom.projectapi.exception.BadRequestException;
import by.rom.projectapi.model.dto.CardDto;
import by.rom.projectapi.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-state")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("")
    public List<CardDto> getCard(@RequestParam Long id){
        return cardService.findById(id);
    }

    @PostMapping("/{id}")
    public CardDto createCard(@PathVariable Long id,
                                   @RequestParam @Nullable String name){
         if (name == null){
            throw new BadRequestException("Name can't be empty.");
        }
        return cardService.createCard(id, name);
    }

    @PutMapping("/{id}")
    public CardDto updateCard(@PathVariable Long id, @RequestParam String name){
        return cardService.updateCard(id, name);
    }

    @DeleteMapping("/{id}")
     public ResponseEntity<String> deleteCard(@PathVariable Long id){
        cardService.deleteCard(id);
        return new ResponseEntity<>("Delete was successful", HttpStatus.OK);
    }



}
