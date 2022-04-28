package by.rom.projectapi.client;

import by.rom.projectapi.converter.BoardConverter;
import by.rom.projectapi.model.Board;
import by.rom.projectapi.model.dto.BoardDto;
import by.rom.projectapi.model.dto.CardDto;
import by.rom.projectapi.repository.BoardRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    private final RestTemplate restTemplate;

    public TrelloClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public URI buildTrelloUrl() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + "trelloUsername" + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all")
                .build()
                .encode()
                .toUri();
    }


    @SneakyThrows
        public void saveTrelloBoard(BoardDto boardDto){
        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/boards/")
                .queryParam("key", trelloAppKey)
                .queryParam("token",   trelloToken)
                .queryParam("name", boardDto.getName())
                .queryParam("defaultLists", false)
                .build().encode().toUri();

        boardDto.setDesc("my first description");
//        boardDto.setIdOrganization("0123456789abcd0123456789");

        System.out.println(url);
        System.out.println(boardDto);

        try {
//            restTemplate.postForLocation(url.toString(), boardDto);
            restTemplate.postForEntity(url, boardDto, BoardDto.class);
//            restTemplate.postForLocation(url.toString(), jsonObject);
 //            restTemplate.put(url.toString(), boardDto);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }


    public void saveTrelloCard(CardDto cardDto){
        System.out.println(cardDto);
        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/lists/")
                .queryParam("key", trelloAppKey)
                .queryParam("token",   trelloToken)
                .queryParam("idBoard", "626ad6f611f05554750a7d48")
                .queryParam("name", cardDto.getName())
                .build().encode().toUri();

        System.out.println(url);
        System.out.println(cardDto);

        restTemplate.postForEntity(url.toString(), cardDto, CardDto.class);


    }

    @SneakyThrows
    public void saveTask(BoardDto boardDto
//            final TrelloBoardDto boardDto, final String newName
    ){

        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/cards/")
                .queryParam("key", trelloAppKey)
                .queryParam("idList", new Object())
                .queryParam("token", trelloToken)
                .queryParam("name", boardDto.getName())
                .build().encode().toUri();

        System.out.println(url);

        try {
            restTemplate.postForEntity(url.toString(), boardDto, BoardDto.class);
        } catch (RestClientException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void getBoard() {
//https://api.trello.com/1/boards/dBW7O6Hf?key=27adb151059841d76aa93b2bee1c6c44&token=5245fec2bc7b98a3ffe1d8cf168597550aa6210ac342a8244a2ccd2a68f9712f
        URI url = UriComponentsBuilder
                .fromHttpUrl(trelloApiEndpoint + "/boards/dBW7O6Hf")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .build().encode().toUri();

        System.out.println();
        BoardDto body = restTemplate.getForEntity(url, BoardDto.class).getBody();
        System.out.println("BODY - " + body);


    }
}
