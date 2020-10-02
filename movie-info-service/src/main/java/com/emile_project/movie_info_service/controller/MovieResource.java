package com.emile_project.movie_info_service.controller;

import com.emile_project.movie_info_service.domain.Movie;
import com.emile_project.movie_info_service.domain.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movie")
public class MovieResource {
    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
        MovieSummary movieSummary = restTemplate.getForObject(
                "https://api.themoviedb.org/3/movie/550" + movieId + "?api_key=" +  apiKey, MovieSummary.class);
        assert movieSummary != null;
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
        //return new Movie("I99","Merlain");
    }
}
