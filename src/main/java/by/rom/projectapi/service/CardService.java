package by.rom.projectapi.service;

import by.rom.projectapi.client.TrelloClient;
import by.rom.projectapi.converter.CardConverter;
import by.rom.projectapi.exception.BadRequestException;
import by.rom.projectapi.exception.NotFoundException;
import by.rom.projectapi.model.Board;
import by.rom.projectapi.model.Card;
import by.rom.projectapi.model.dto.CardDto;
import by.rom.projectapi.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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

        CardDto cardDto = trelloClient.saveTrelloCard(cardConverter.toDto(Card.builder()
                .name(name)
                .board(board)
                .build()));

        Card card = cardConverter.fromDto(cardDto);
        card.setBoard(board);

        cardRepository.save(card);

        return cardDto;
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

        CardDto cardDto = cardConverter.toDto(card);

        trelloClient.updateCard(cardDto);

        return cardDto;
    }

    public void deleteCard(Long id) {
        cardRepository.findById(id)
                .ifPresentOrElse(
                        (board -> cardRepository.deleteById(id)),
                        ()-> {
                            throw new NotFoundException(String.format("Card with such id: %d , didn't found", id)); }
                );
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Card with such id: %d , didn't found", id)));

    }
}
