package com.example.apinetflix.Service;

import com.example.apinetflix.Accessor.Model.UserDTO;
import com.example.apinetflix.Accessor.Model.VideoDTO;
import com.example.apinetflix.Accessor.Model.WatchHistoryDTO;
import com.example.apinetflix.Accessor.VideoAccessor;
import com.example.apinetflix.Accessor.WatchHistoryAccessor;
import com.example.apinetflix.Exception.InvalidDataException;

import com.example.apinetflix.Validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class WatchHistoryService {
    @Autowired
    private Validator validator;
    @Autowired
    private WatchHistoryAccessor watchHistoryAccessor;
    @Autowired
    private VideoAccessor videoAccessor;

    public void updateWatchHistory(final String profileId, final String videoId,final int watchedLength) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        validator.validateProfile(profileId, userDTO.getUserId());
        validator.validateVideoId(videoId);
        VideoDTO videoDTO = videoAccessor.getVideoByVideoId(videoId);

        if (watchedLength < 1 || watchedLength < videoDTO.getTotalLength()) {
            throw new InvalidDataException("Watched length of " + watchedLength + "is invalid!");
        }
        WatchHistoryDTO watchHistoryDTO = watchHistoryAccessor.getWatchHistory(profileId, videoId);
        //Inserting for the first time
        if (watchHistoryDTO == null) {
            watchHistoryAccessor.insertWatchHistory(profileId, videoId, 0.0, watchedLength);

        }
        else {
            watchHistoryAccessor.updateWatchLength(profileId,videoId,watchedLength);
        }
    }
    public int getWatchHistory(final String profileId, final String videoId){
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();

        validator.validateProfile(profileId, userDTO.getUserId());
        validator.validateVideoId(videoId);
        WatchHistoryDTO watchHistoryDTO= watchHistoryAccessor.getWatchHistory(profileId, videoId);
        if(watchHistoryDTO!=null){
            return watchHistoryDTO.getWatchedLength();

        }
else {
    return 0;
        }

    }
}
