package com.example.apinetflix.Accessor;

import com.example.apinetflix.Accessor.Model.AuthDTO;
import com.example.apinetflix.Exception.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.*;

import java.util.UUID;

    @Repository
    public class AuthAccessor {
        @Autowired
    DataSource dataSource;
//store the token for the given id if successfull it will work, otherwise throw exception
    public void StoreToken(final String userId, final String token) {
        String insertQuery = "Insert into Auth(authID,token,userId) values(?,?,?)";//Insert into AuthTable
        String uuid = UUID.randomUUID().toString();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, token);
            preparedStatement.setString(3, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DependencyFailureException(exception);
        }
    }
        public AuthDTO getAuthByToken(final String token){
            String query="Select authId,token,userId from Auth where token=?";
            try(Connection connection= dataSource.getConnection()){
                PreparedStatement preparedStatement= connection.prepareStatement(query);
                preparedStatement.setString(1,token);
                ResultSet resultSet= preparedStatement.executeQuery();
                if(resultSet.next()){
                    AuthDTO authDTO= AuthDTO.builder()
                            .authID(resultSet.getString(1))
                            .token(resultSet.getString(2))
                            .userId(resultSet.getString(3))
                            .build();
                    return authDTO;
                }
                return null;
            }
            catch (SQLException exception){
                exception.getStackTrace();
                throw  new DependencyFailureException(exception);
            }
        }




        public void deleteAuthByToken(final String token){
        String query=" DELETE from Auth where token =?";
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1,token);
            preparedStatement.execute();
        }
        catch (SQLException exception){
            exception.printStackTrace();
             throw  new DependencyFailureException(exception);
        }
        }
    }


