package com.example.apinetflix.Accessor;
import com.example.apinetflix.Accessor.Model.WatchHistoryDTO;
import com.example.apinetflix.Exception.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class WatchHistoryAccessor {

 @Autowired
 private DataSource dataSource;

 public void updateWatchLength(final String profileId, final String videoId,final int watchedLength) {
     String query = "UPDATE WatchHistory set watchedLength =?,lastWatchedAt =? where profileId =? and " + "videoId=?";
     try (Connection connection = dataSource.getConnection()) {
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setInt(1, watchedLength);
         preparedStatement.setDate(2, new Date(System.currentTimeMillis()));
         preparedStatement.setString(3, profileId);
         preparedStatement.setString(4, videoId);
         preparedStatement.execute();
     } catch (SQLException exception) {
         exception.printStackTrace();
         throw new DependencyFailureException(exception);
     }
 }
     public void insertWatchHistory(final String profileId, final String videoId,final double rating, final int watchedLength){
     String query="INSERT into watchedHistory values(?,?,?,?,?,?)";
     try(Connection connection= dataSource.getConnection()){
         PreparedStatement preparedStatement= connection.prepareStatement(query);
         Date currentDate= new Date(System.currentTimeMillis());
         preparedStatement.setString(1,profileId);
         preparedStatement.setString(2,videoId);
         preparedStatement.setDouble(3,rating);
         preparedStatement.setInt(4,watchedLength);
         preparedStatement.setDate(5,currentDate);
         preparedStatement.setDate(6,currentDate);
         preparedStatement.execute();

     }
     catch (SQLException exception){
         exception.printStackTrace();
         throw new DependencyFailureException(exception);
     }
 }
public WatchHistoryDTO getWatchHistory(final String profileId, final String videoId) {
    String query = "SELECT rating ,watchedLength,lastWatchedAt, firstWatchedAt, from watchHistory" + "where profileId=? and videoId=?";
    try (Connection connection = dataSource.getConnection()) {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, profileId);
        preparedStatement.setString(2, videoId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            WatchHistoryDTO watchHistoryDTO = WatchHistoryDTO.builder()
                    .profileId(profileId)
                    .videoId(videoId)
                    .rating(resultSet.getInt(1))
                    .watchedLength(resultSet.getInt(2))
                    .lastWatchedAt(resultSet.getDate(3))
                    .firstWatchedAt(resultSet.getDate(4))
                    .build();
            return watchHistoryDTO;
        } else {
            return null;
        }
    } catch (SQLException exception) {
        exception.printStackTrace();
        throw new DependencyFailureException(exception);
    }
}
}
