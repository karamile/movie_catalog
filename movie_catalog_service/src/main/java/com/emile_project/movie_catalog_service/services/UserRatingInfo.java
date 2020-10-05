package com.emile_project.movie_catalog_service.services;

import com.emile_project.movie_catalog_service.domain.Rating;
import com.emile_project.movie_catalog_service.domain.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {
    @Autowired
    private RestTemplate restTemplate;
    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(@PathVariable("userId") String userId){
        return restTemplate.getForObject("http://ratings-data-services/ratingsData/user/"+userId, UserRating.class);
    }
    public UserRating getFallbackUserRating(@PathVariable("userId") String userId){
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setUserRatings(Arrays.asList(new Rating("0",0)));
        return userRating;
    }
}
