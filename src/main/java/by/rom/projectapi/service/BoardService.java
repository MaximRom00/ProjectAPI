package by.rom.projectapi.service;

import by.rom.projectapi.client.TrelloClient;
import by.rom.projectapi.converter.BoardConverter;
import by.rom.projectapi.exception.BadRequestException;
import by.rom.projectapi.exception.NotFoundException;
import by.rom.projectapi.model.Board;
import by.rom.projectapi.model.dto.BoardDto;
import by.rom.projectapi.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final BoardConverter boardConverter;

    private final TrelloClient trelloClient;

    public BoardService(BoardRepository boardRepository, BoardConverter boardConverter, TrelloClient trelloClient) {
        this.boardRepository = boardRepository;
        this.boardConverter = boardConverter;
        this.trelloClient = trelloClient;
    }

    public BoardDto createBoard(String name, String desc) {

        boardRepository.findByName(name)
                .ifPresent(board -> {
                    throw new BadRequestException("Board exists with such name: " + name);
                });

        Board board = boardRepository.save(
                Board.builder()
                        .name(name)
                        .description(desc)
//                        .shortLink(name + "shortLink")
                        .idBoard(String.valueOf(Math.abs(new Random().nextInt())))
                        .build()
        );

//        Save board to Trello
        BoardDto boardDto = boardConverter.toDto(board);
        trelloClient.saveTrelloBoard(boardDto);
//        trelloClient.getBoard();
//

        return boardConverter.toDto(board);
    }

    public BoardDto update(Long id, String name) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Board with such id: %d , didn't found", id)));

        board.setName(name);

        boardRepository.save(board);

        return boardConverter.toDto(board);
    }

    public void deleteBoard(Long id) {
        boardRepository.findById(id)
                .ifPresentOrElse(
                (board -> boardRepository.deleteById(id)),
                ()-> {
                    throw new NotFoundException(String.format("Board with such id: %d , didn't found", id)); }
                );
    }

    public List<BoardDto> getBoard(String name) {

        System.out.println("name - " + name);
        trelloClient.getBoard();
        if (name == null){
            return boardRepository.findAll()
                    .stream()
                    .map(boardConverter::toDto)
                    .collect(Collectors.toList());
        }
        else{
            return  boardRepository
                    .findByNameContainingIgnoreCase(name)
                    .stream()
                    .map(boardConverter::toDto)
                    .collect(Collectors.collectingAndThen(Collectors.toList(), Optional::of))
                    .filter(pr -> !pr.isEmpty())
                    .orElseThrow(() -> new NotFoundException(String.format("Board with such name: %s , didn't found", name)));
        }
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Board with such id: %d , didn't found", id)));

    }
}
