package by.rom.projectapi.controller;

import by.rom.projectapi.exception.BadRequestException;
import by.rom.projectapi.model.dto.CardDto;
import by.rom.projectapi.service.CardService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/card")
@Api(tags = {"Card"})
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @ApiOperation(value = "Get trello card by id.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Found the card"),
            @ApiResponse(code = 404, message = "Card not found")
    })
    @GetMapping("")
    public List<CardDto> getCard(@ApiParam(value = "id of board for getting all cards") @RequestParam Long id){
        return cardService.findById(id);
    }

    @ApiOperation(value = "Create new card.", response = CardDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Card is created"),
            @ApiResponse(code = 400, message = "Missing or invalid request body")
    })
    @PostMapping("/{id}")
    public CardDto createCard(@ApiParam(value = "id of board", example = "1", required = true) @PathVariable Long id,
                              @ApiParam(value = "name of card", example = "My new card", required = true) @RequestParam @Nullable String name){
         if (name == null){
            throw new BadRequestException("Name can't be empty.");
        }
        return cardService.createCard(id, name);
    }

    @ApiOperation(value = "Update cards name.", response = CardDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cards name is changed"),
            @ApiResponse(code = 400, message = "Missing or invalid request body")
    })
    @PutMapping("/{id}")
    public CardDto updateCard(@ApiParam(value = "unique id of card", example = "1", required = true) @PathVariable Long id,
                              @ApiParam(value = "name of card for update", example = "Update name", required = true) @RequestParam String name){
        return cardService.updateCard(id, name);
    }

    @ApiOperation(value = "Delete trello card by id.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Card was deleted"),
            @ApiResponse(code = 404, message = "Card not found")
    })
    @DeleteMapping("/{id}")
     public ResponseEntity<String> deleteCard(@ApiParam(value = "unique id of card for delete", example = "1", required = true) @PathVariable Long id){
        cardService.deleteCard(id);
        return new ResponseEntity<>("Delete was successful", HttpStatus.OK);
    }
}
