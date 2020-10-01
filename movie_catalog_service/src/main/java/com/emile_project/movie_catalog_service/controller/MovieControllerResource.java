package com.emile_project.movie_catalog_service.controller;

import com.emile_project.movie_catalog_service.domain.CatalogItem;
import com.emile_project.movie_catalog_service.domain.Movie;
import com.emile_project.movie_catalog_service.domain.Rating;
import com.emile_project.movie_catalog_service.domain.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieControllerResource {
    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = restTemplate.getForObject("http://ratings-data-services/ratingsData/user/"+userId, UserRating.class);
        assert ratings != null;
        return ratings.getUserRatings().stream().map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movie/"+rating.getMovieId(), Movie.class);

                    assert movie != null;
                    return new CatalogItem(movie.getName(), "test", rating.getRating());
                })
                .collect(Collectors.toList());

    }
}


/* Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8016/movie/foo"+rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();
                     */