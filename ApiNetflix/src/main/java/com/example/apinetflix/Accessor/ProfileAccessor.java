package com.example.apinetflix.Accessor;

import com.example.apinetflix.Accessor.Model.ProfileDTO;
import com.example.apinetflix.Accessor.Model.ProfileType;
import com.example.apinetflix.Exception.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

@Repository
public class ProfileAccessor {
    @Autowired
   private DataSource dataSource;

    public void  addNewProfile(final String userId, final  String name, final ProfileType type){
       String sql= "INSERT INTO profile vale(?,?,?,?,?)";
       try(Connection connection = dataSource.getConnection()) {
           PreparedStatement preparedStatement= connection.prepareStatement(sql);
           preparedStatement.setString(1, UUID.randomUUID().toString());
           preparedStatement.setString(2,name);
           preparedStatement.setString(3,type.toString());
           preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
           preparedStatement.setString(5, userId);
           preparedStatement.execute();
       }
       catch (SQLException exception){
           exception.printStackTrace();
           throw  new DependencyFailureException(exception);
       }
    }
    public  void  deleteProfile(final String profileId){
        String sql= "DELETE from profile where profileId = ?";
        try (Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,profileId);
            preparedStatement.execute(sql);

        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw new DependencyFailureException(exception);
        }
    }
    public ProfileDTO getProfileByProfileId(final  String profileId){
        String sql="SELECT name,type,createdAt,userId from profile where profileId=?";
        try(Connection connection= dataSource.getConnection()){
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
            preparedStatement.setString(1,profileId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
            ProfileDTO profileDTO= ProfileDTO.builder()
                        .profileId(profileId)
                        .name(resultSet.getString(1))
                        .type(ProfileType.valueOf(resultSet.getString(2)))
                        .createdAt(resultSet.getDate(3))
                        .userId(resultSet.getString(4))
                        .build();
                return profileDTO;
            }
            else {
                return null;
            }
        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw new DependencyFailureException(exception);
        }
    }

}

