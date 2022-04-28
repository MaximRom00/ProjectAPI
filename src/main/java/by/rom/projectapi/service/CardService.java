package by.rom.projectapi.service;

import by.rom.projectapi.client.TrelloClient;
import by.rom.projectapi.converter.CardConverter;
import by.rom.projectapi.exception.BadRequestException;
import by.rom.projectapi.exception.NotFoundException;
import by.rom.projectapi.model.Board;
import by.rom.projectapi.model.Card;
import by.rom.projectapi.model.dto.BoardDto;
import by.rom.projectapi.model.dto.CardDto;
import by.rom.projectapi.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CardService {

    private final CardConverter cardConverter;

    private final BoardService boardService;

    private final CardRepository cardRepository;

    private final TrelloClient trelloClient;

    public CardService(CardConverter cardConverter, BoardService boardService, CardRepository cardRepository, TrelloClient trelloClient) {
        this.cardConverter = cardConverter;
        this.boardService = boardService;
        this.cardRepository = cardRepository;
        this.trelloClient = trelloClient;
    }

    public List<CardDto> findById(Long id) {
        Board board = boardService.getBoardById(id);

        return board.getCards().stream()
                .map(cardConverter::toDto)
                .collect(Collectors.toList());
    }

    public CardDto createCard(Long id, String name) {
        Board board = boardService.getBoardById(id);

        board.getCards().stream()
                .filter(card -> card.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresent(card->{
                    throw new BadRequestException("Task with such name exists: " + name);
                });

        Card card = cardRepository.save(Card.builder()
                .name(name)
                .board(board)
                .idList(Math.abs(new Random().nextInt()))
                .build());

        System.out.println(card);
        CardDto cardDto = cardConverter.toDto(card);
        trelloClient.saveTrelloCard(cardDto);

        return cardConverter.toDto(card);
    }

    public CardDto updateCard(Long id, String name) {
        cardRepository.findAll()
                .stream()
                .filter(card -> card.getName().equalsIgnoreCase(name))
                .findAny()
                .ifPresent(card -> {
                    throw new BadRequestException("Card with such name: " + name + " exists. Name should be unique.");
                });

        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Card with such id: %d , didn't found", id)));

        card.setName(name);

        cardRepository.save(card);

        return cardConverter.toDto(card);
    }

    public void deleteCard(Long id) {
        cardRepository.findById(id)
                .ifPresentOrElse(
                        (board -> cardRepository.deleteById(id)),
                        ()-> {
                            throw new NotFoundException(String.format("Card with such id: %d , didn't found", id)); }
                );
    }
}
