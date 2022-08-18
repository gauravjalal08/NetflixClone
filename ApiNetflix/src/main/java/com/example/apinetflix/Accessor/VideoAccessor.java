package com.example.apinetflix.Accessor;

import com.example.apinetflix.Accessor.Model.VideoDTO;
import com.example.apinetflix.Exception.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class VideoAccessor {
    @Autowired
    DataSource dataSource;

    public VideoDTO getVideoByVideoId(final String videoId) {
        String query = "SELECT name,seriesId,showId,rating,releaseDate,totalLength, videoPath,thumbnilPath from video " + " where videoId=?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, videoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                VideoDTO videoDTO = VideoDTO.builder()
                        .videoId(videoId)
                        .name(resultSet.getString(1))
                        .seriesId(resultSet.getString(2))
                        .showId(resultSet.getString(3))
                        .rating(resultSet.getDouble(4))
                        .releaseDate(resultSet.getDate(5))
                        .totalLength(resultSet.getInt(6))
                        .videoPath(resultSet.getString(7))
                        .thumbnilPath(resultSet.getString(8))
                        .build();
                return videoDTO;


            } else {
                return null;
            }


        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DependencyFailureException(exception);
        }
    }
}
