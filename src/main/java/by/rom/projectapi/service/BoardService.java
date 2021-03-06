package by.rom.projectapi.service;

import by.rom.projectapi.client.TrelloClient;
import by.rom.projectapi.converter.BoardConverter;
import by.rom.projectapi.exception.BadRequestException;
import by.rom.projectapi.exception.NotFoundException;
import by.rom.projectapi.model.Board;
import by.rom.projectapi.model.dto.BoardDto;
import by.rom.projectapi.repository.BoardRepository;
import org.springframework.stereotype.Service;

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

        Board board =
                Board.builder()
                        .name(name)
                        .description(desc)
                        .idBoard(String.valueOf(Math.abs(new Random().nextInt())))
                        .createAt(LocalDateTime.now())
                        .build();

//        Save board to Trello
        trelloClient.saveTrelloBoard(boardConverter.toDto(board));
//
        return boardConverter.toDto(board);
    }

    public BoardDto update(Long id, String name) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Board with such id: %d , didn't found", id)));

        board.setName(name);
        boardRepository.save(board);

        BoardDto boardDto = boardConverter.toDto(board);
        trelloClient.updateBoardName(boardDto);

        return boardDto;
    }

    public void deleteBoard(Long id) {
        Optional<Board> boardById = boardRepository.findById(id);
        boardById.ifPresentOrElse(
                (board -> boardRepository.deleteById(id)),
                ()-> {
                    throw new NotFoundException(String.format("Board with such id: %d , didn't found", id)); }
                );

        boardById.ifPresent(board -> trelloClient.deleteBoard(boardConverter.toDto(board)));
    }

    public List<BoardDto> getBoard(String name) {

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
