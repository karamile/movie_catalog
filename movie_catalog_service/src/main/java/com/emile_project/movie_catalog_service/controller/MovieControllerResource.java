package com.emile_project.movie_catalog_service.controller;

import com.emile_project.movie_catalog_service.domain.CatalogItem;
import com.emile_project.movie_catalog_service.domain.Movie;
import com.emile_project.movie_catalog_service.domain.Rating;
import com.emile_project.movie_catalog_service.domain.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieControllerResource {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

//    @Autowired
//    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    @HystrixCommand(fallbackMethod = "getFallbackCatalog")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = restTemplate.getForObject("http://ratings-data-services/ratingsData/user/"+userId, UserRating.class);
        assert ratings != null;
        return ratings.getUserRatings().stream().map(rating -> {
            // For each movie Id, call movie info and get details
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movie/"+rating.getMovieId(), Movie.class);
            // put them all together
                    assert movie != null;
                    return new CatalogItem(movie.getName(), "test", rating.getRating());
                })
                .collect(Collectors.toList());

    }
    public  List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
        return Collections.singletonList(new CatalogItem("no movie", "", 0));
    }
}


/* Movie movie = webClientBuilder.build()
                            .get()
                            .uri("http://localhost:8016/movie/foo"+rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
                            .block();
                     */