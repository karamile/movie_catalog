package com.emile_project.movie_catalog_service.services;

import com.emile_project.movie_catalog_service.domain.CatalogItem;
import com.emile_project.movie_catalog_service.domain.Movie;
import com.emile_project.movie_catalog_service.domain.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
    @Autowired
    private RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating){
        Movie movie = restTemplate.getForObject("http://movie-info-service/movie/"+rating.getMovieId(), Movie.class);
        assert movie != null;
        return  new CatalogItem(movie.getName(),movie.getGetDescription(),rating.getRating());
    }
    public CatalogItem getFallbackCatalogItem(Rating rating){
        return new CatalogItem("Movie Name not found","no idea",rating.getRating());

    }
}
