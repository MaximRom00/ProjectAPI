package by.rom.projectapi.client;

import by.rom.projectapi.converter.BoardConverter;
import by.rom.projectapi.model.Board;
import by.rom.projectapi.model.dto.BoardDto;
import by.rom.projectapi.model.dto.CardDto;
import by.rom.projectapi.model.dto.TaskDto;
import by.rom.projectapi.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@Slf4j
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    private final RestTemplate restTemplate;
    private final BoardConverter boardConverter;
    private final BoardRepository boardRepository;

    public TrelloClient(RestTemplate restTemplate, BoardConverter boardConverter, BoardRepository boardRepository) {
        this.restTemplate = restTemplate;
        this.boardRepository = boardRepository;
        this.boardConverter = boardConverter;
    }

    @Scheduled(fixedRate = 90000)
    public void getAllBoards(){
        List<BoardDto> boardDtos = getList();
        List<Board> list = boardRepository.findAll();

        List<Board> boards = Objects.requireNonNull(boardDtos)
                .stream()
                .map(boardConverter::fromDto)
                .collect(Collectors.toList());

        boards.stream().filter(board -> !list.contains(board)).forEach(boardRepository::save);
    }

    private List<BoardDto> getList(){

        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/members/6261b0e401fed88aae2faeb9/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token",   trelloToken)
                .build().encode().toUri();

        ResponseEntity<List<BoardDto>> response =
                restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

        return response.getBody();
    }

    public void deleteBoard(BoardDto board){
        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/boards/" + board.getShortLink())
                .queryParam("key", trelloAppKey)
                .queryParam("token",   trelloToken)
                .build().encode().toUri();

        restTemplate.delete(url);
    }

    public void updateBoardName(BoardDto board){
        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/boards/" + board.getIdBoard())
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", board.getName())
                .build().encode().toUri();

        restTemplate.put(url, board);
    }

    public void saveTrelloBoard(BoardDto boardDto){

        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/boards/")
                .queryParam("key", trelloAppKey)
                .queryParam("token",   trelloToken)
                .queryParam("name", boardDto.getName())
                .queryParam("defaultLists", false)
                .build().encode().toUri();

        restTemplate.postForEntity(url, boardDto, BoardDto.class);

    }

    public CardDto saveTrelloCard(CardDto cardDto){

        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/lists/")
                .queryParam("key", trelloAppKey)
                .queryParam("token",   trelloToken)
                .queryParam("idBoard", cardDto.getBoardDto().getIdBoard())
                .queryParam("name", cardDto.getName())
                .build().encode().toUri();

        return restTemplate.postForEntity(url.toString(), cardDto, CardDto.class).getBody();
    }

    public void updateCard(CardDto cardDto){

        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/lists/" + cardDto.getIdList())
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", cardDto.getName())
                .build().encode().toUri();

        restTemplate.put(url, cardDto);
    }

    public TaskDto saveTask(TaskDto taskDto){
        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/cards/")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("idList", taskDto.getCardDto().getIdList())
                .queryParam("name", taskDto.getName())
                .build().encode().toUri();

       return restTemplate.postForEntity(url.toString(), taskDto, TaskDto.class).getBody();
    }

    public void deleteTask(TaskDto taskDto){
        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/cards/" + taskDto.getIdTask())
                .queryParam("key", trelloAppKey)
                .queryParam("token",   trelloToken)
                .build().encode().toUri();

        restTemplate.delete(url);
    }

    public void updateTask(TaskDto taskDto){
        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/cards/" + taskDto.getIdTask())
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("name", taskDto.getName())
                .queryParam("desc", taskDto.getDescription())
                .build().encode().toUri();

        restTemplate.put(url, taskDto);
    }
}
