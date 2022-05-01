package by.rom.projectapi.converter;

import by.rom.projectapi.model.Board;
import by.rom.projectapi.model.dto.BoardDto;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class BoardConverter {

    public BoardDto toDto(Board board){
        return BoardDto.builder()
//                .id(String.valueOf(board.getId()))
                .name(board.getName())
                .shortLink(board.getShortLink())
                .idBoard(board.getIdBoard())
                .desc(board.getDescription())
                .idMemberCreator(board.getIdMemberCreator())
                .createAt(board.getCreateAt())
                .build();
    }

    public Board fromDto(BoardDto boardDto){
        return Board.builder()
//                .id(Long.valueOf(boardDto.getId()))
                .name(boardDto.getName())
                .shortLink(boardDto.getShortLink())
                .idBoard(boardDto.getIdBoard())
                .description(boardDto.getDesc())
                .idMemberCreator(boardDto.getIdMemberCreator())
                .createAt(LocalDateTime.now())
                .build();
    }
}
