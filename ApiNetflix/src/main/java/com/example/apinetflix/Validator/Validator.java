package com.example.apinetflix.Validator;

import com.example.apinetflix.Accessor.Model.ProfileDTO;
import com.example.apinetflix.Accessor.Model.VideoDTO;
import com.example.apinetflix.Accessor.ProfileAccessor;
import com.example.apinetflix.Accessor.VideoAccessor;
import com.example.apinetflix.Exception.DependencyFailureException;
import com.example.apinetflix.Exception.InvalidProfileException;
import com.example.apinetflix.Exception.InvalidVideoException;
import org.springframework.beans.factory.annotation.Autowired;

public class Validator {
    @Autowired
   private ProfileAccessor profileAccessor;
    @Autowired
    private VideoAccessor videoAccessor;
    public void validateProfile(final String profileId, final String userId){
        ProfileDTO profileDTO= profileAccessor.getProfileByProfileId(profileId);
        if(profileDTO ==null || !profileDTO.getUserId().equals(userId)){
            throw new InvalidProfileException("Profile " +profileId+ "is invalid or doesnt exist!");
        }

    }
    public void validateVideoId(final String videoId){
        VideoDTO videoDTO = videoAccessor.getVideoByVideoId(videoId);
        if (videoDTO == null) {
            throw new InvalidVideoException("Video with videoId" + videoId + "does not exist!");

        }

    }
}
