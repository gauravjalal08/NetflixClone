package com.example.apinetflix.Service;

import com.example.apinetflix.Accessor.Model.VideoDTO;
import com.example.apinetflix.Accessor.S3Accessor;
import com.example.apinetflix.Accessor.VideoAccessor;
import com.example.apinetflix.Exception.InvalidVideoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VideoService {
    @Autowired
    VideoAccessor videoAccessor;
    @Autowired
    S3Accessor s3Accessor;

    public String getVideoUrl(final String videoId){
        VideoDTO videoDTO=videoAccessor.getVideoByVideoId(videoId);
        if(videoDTO==null){
            throw new InvalidVideoException("video with videoId" +videoId +"does not exist!");
        }
        String videoPath= videoDTO.getVideoPath();
        return s3Accessor.getPreSignedUrl(videoPath, videoDTO.getTotalLength()*60);

    }
    public String getVideoThumbnail(final String videoId){
        VideoDTO videoDTO= videoAccessor.getVideoByVideoId(videoId);
        if(videoDTO==null){
            throw new InvalidVideoException("video with videoId" +videoId +"does not exist!");

        }
        String thumbnilPath=videoDTO.getThumbnilPath();
        return s3Accessor.getPreSignedUrl(thumbnilPath, 2*60);
    }

}
