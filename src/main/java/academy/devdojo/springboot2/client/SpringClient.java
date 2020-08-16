package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.wrapper.PageableResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SpringClient {

    public static void main(String[] args) {
        //testGetWithRestTemplate();

        //@formatter:off
        ResponseEntity<PageableResponse<Anime>> exchangeAnimeList = new RestTemplate()
                .exchange("http://localhost:8080/animes", HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        //@formatter:on

        log.info("Anime List{}", exchangeAnimeList.getBody());

//        Anime overlord = Anime.builder().name("Overlord").build();
//        Anime overlordSaved = new RestTemplate().postForObject("http://localhost:8080/animes", overlord, Anime.class);
//
//        log.info("Overlord saved id {}", overlordSaved.getId());

        Anime steinsGate = Anime.builder().name("Steins Gate").build();

        Anime steinsGateSaved = new RestTemplate().exchange("http://localhost:8080/animes", HttpMethod.POST, new HttpEntity<>(steinsGate, createJsonHeader()), Anime.class).getBody();

        log.info("Steins Gate saved id:d {}", steinsGateSaved.getId());

        steinsGateSaved.setName("Steins Gate Zero");
        ResponseEntity<Void> exchangeUpdate = new RestTemplate()
                .exchange("http://localhost:8080/animes",
                        HttpMethod.PUT,
                        new HttpEntity<>(steinsGateSaved, createJsonHeader()),
                        Void.class);

        log.info("Steins Gate Update Status {}", exchangeUpdate.getStatusCode());

        ResponseEntity<Void> exchangeDeleted = new RestTemplate()
                .exchange("http://localhost:8080/animes/{id}",
                        HttpMethod.DELETE,
                        null,
                        Void.class, steinsGateSaved.getId());

        log.info("Steins Gate Deleted Status {}", exchangeDeleted.getStatusCode());


    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private static void testGetWithRestTemplate() {
        ResponseEntity<Anime> animeResponseEntity = new RestTemplate()
                .getForEntity("http://localhost:8080/animes/{id}", Anime.class, 8);
        log.info("Response Entity {}", animeResponseEntity);

        log.info("Response Data {}", animeResponseEntity.getBody());

        Anime anime = new RestTemplate()
                .getForObject("http://localhost:8080/animes/{id}", Anime.class, 8);

        log.info("Anime {}", anime);
    }
}
