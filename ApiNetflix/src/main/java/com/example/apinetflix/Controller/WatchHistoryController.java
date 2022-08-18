package com.example.apinetflix.Controller;

import com.example.apinetflix.Controller.Model.GetWatchHistoryInput;
import com.example.apinetflix.Controller.Model.WatchHistoryInput;
import com.example.apinetflix.Exception.InvalidProfileException;
import com.example.apinetflix.Exception.InvalidVideoException;
import com.example.apinetflix.Security.Roles;
import com.example.apinetflix.Service.WatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class WatchHistoryController {
    @Autowired
   WatchHistoryService watchHistoryService;

    @PostMapping("/video{videoId}/watchTime")
    @Secured({Roles.Customer})
    public ResponseEntity<Void> updateWatchHistory(@PathVariable("videoId") String videoId, @RequestBody WatchHistoryInput watchHistoryInput){

        try {
            String profileId = watchHistoryInput.getProfileId();
            int watchTime= watchHistoryInput.getWatchTime();
            watchHistoryService.updateWatchHistory(profileId,videoId,watchTime);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (InvalidVideoException| InvalidProfileException  exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/video{videoId}/watchTime")
    @Secured({Roles.Customer})
     public  ResponseEntity<Integer> getWatchHistory(@PathVariable("videoId") String videoId, @RequestBody GetWatchHistoryInput input){
        String profileId = input.getProfileId();
        try{
            int watchlength= watchHistoryService.getWatchHistory(profileId,videoId);
            return ResponseEntity.status(HttpStatus.OK).body(watchlength);
        }
        catch (InvalidVideoException| InvalidProfileException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
