package com.example.apinetflix.Controller;

import com.example.apinetflix.Exception.InvalidVideoException;
import com.example.apinetflix.Security.Roles;
import com.example.apinetflix.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {
    @Autowired
    VideoService videoService;


    @GetMapping("/video/{videoId}/link")
    @Secured({Roles.Customer})
    public ResponseEntity<String> getVideoLink(@PathVariable ("videoId") String videoId){
        try{
            String videoUrl =videoService.getVideoUrl(videoId);
            return ResponseEntity.status(HttpStatus.OK).body(videoUrl);
        }
        catch (InvalidVideoException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
}
