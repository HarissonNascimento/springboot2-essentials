package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.Util.AnimeCreator;
import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.service.AnimeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
    @InjectMocks
    private AnimeController animeController;
    @Mock
    private AnimeService animeServiceMock;

    @BeforeEach
    public void setUp(){
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.findById(anyInt()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(AnimeCreator.createAnimeToBeSaved()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).delete(anyInt());

        BDDMockito.when(animeServiceMock.save(AnimeCreator.createValidAnime()))
                .thenReturn(AnimeCreator.createValidUpdateAnime());
    }

    @Test
    @DisplayName("ListAll returns a pageable list of animes when successful")
    public void listAll_ReturnListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        Page<Anime> animePage = animeController.listAll(null).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList()).isNotEmpty();

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById return an anime when successful")
    public void findById_ReturnAnAnimeById_WhenSuccessful(){
        Integer expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.findById(1).getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull();

        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a pageable list of animes when successful")
    public void findByName_ReturnListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        List<Anime> animeList = animeController.findByName("DBZ").getBody();

        Assertions.assertThat(animeList).isNotNull();

        Assertions.assertThat(animeList).isNotEmpty();

        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Save create an anime when successful")
    public void save_CreateAnime_WhenSuccessful(){
        Integer expectedId = AnimeCreator.createValidAnime().getId();

        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();

        Anime anime = animeController.save(animeToBeSaved).getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull();

        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Delete removes the anime when successful")
    public void delete_RemovesAnime_WhenSuccessful(){
        ResponseEntity<Void> responseEntity = animeController.delete(1);

        Assertions.assertThat(responseEntity).isNotNull();

        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("Update save updated anime when successful")
    public void update_SaveUpdatedAnime_WhenSuccessful(){
        Anime validUpdateAnime = AnimeCreator.createValidUpdateAnime();

        String expectedName = validUpdateAnime.getName();

        Anime anime = animeController.save(AnimeCreator.createValidAnime()).getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull();

        Assertions.assertThat(anime.getName()).isEqualTo(expectedName);
    }

}