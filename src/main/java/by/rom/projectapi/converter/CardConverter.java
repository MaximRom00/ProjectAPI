package by.rom.projectapi.converter;

import by.rom.projectapi.model.Card;
import by.rom.projectapi.model.dto.CardDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CardConverter {


    private final BoardConverter boardConverter;

    public CardConverter( BoardConverter boardConverter) {
        this.boardConverter = boardConverter;
    }

    public CardDto toDto(Card card){

        return CardDto.builder()
                .name(card.getName())
                .createAt(card.getCreateAt())
                .boardDto(boardConverter.toDto(card.getBoard()))
                .idList(card.getIdList())
                .build();
    }

    public Card fromDto(CardDto cardDto) {
        return Card.builder()
                .idList(cardDto.getIdList())
                .createAt(LocalDateTime.now())
                .name(cardDto.getName())
                .build();
    }
}
