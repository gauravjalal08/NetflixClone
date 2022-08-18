package com.example.apinetflix.Accessor;

import com.example.apinetflix.Accessor.Model.*;
import com.example.apinetflix.Exception.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository//-----will create beans(rep ann is used for those who interact with db directly)
public class UserAccessor {
    @Autowired
    private DataSource dataSource; //DB config

    //if user exists returns its UserDto object else return null
    public UserDTO getUserByEmail(final String email) { //fetch a user based on email
        String query = "Select userId,name ,email,password,phoneNo,state role,emailVerificationStatus,phoneVerifiactionStatus from User where email =?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);// ? will replace by email
            //execution of query
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {//set the value to user dto---next--if more result are there if true conf userDto
                UserDTO userDto = UserDTO.builder()//userDto -classname
                        .userId(resultSet.getString(1))
                        .name(resultSet.getString(2))
                        .email(resultSet.getString(3))
                        .password(resultSet.getString(4))
                        .phoneNo(resultSet.getString(5))
                        .state(UserState.valueOf(resultSet.getString(6)))
                        .role(UserRole.valueOf(resultSet.getString(7)))
                        //.emailVerificationStatus(EmailVerificationStatus.valueOf(resultSet.getString(8)))
                        //.phoneVerificationStatus(PhoneVerificationStatus.valueOf(resultSet.getString(9)))
                        .build();
                return userDto;

            }
            return  null;

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DependencyFailureException(exception);
        }

    }
        public void addNewUser(final String email,final String name, final String password, final String phoneNo, final UserState userState, final UserRole userRole ){
        String insertQuery = "Insert into User values(?,?,?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, phoneNo);
            preparedStatement.setString(6, userState.name());
            preparedStatement.setString(7, userRole.name());
            preparedStatement.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);

        }
    }
    // helper method
    public UserDTO getUserByPhoneNo(final String PhoneNO) {
        String query = "Select userID,name ,Email,Password,PhoneNo,state role from User where PhoneNO =?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, PhoneNO);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {//set the value to user dto---next--if more result are there
                UserDTO userDto = UserDTO.builder()
                        .userId(resultSet.getString(1))
                        .name(resultSet.getString(2))
                        .email(resultSet.getString(3))
                        .password(resultSet.getString(4))
                        .phoneNo(resultSet.getString(5))
                        .state(UserState.valueOf(resultSet.getString(6)))
                        .role(UserRole.valueOf(resultSet.getString(7)))
                        .build();
                return userDto;

            }
            return null;


        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DependencyFailureException(ex);
        }
    }
    public void updateRole(final String userid, final UserRole updatedRole){
        String query= "Update user set role =? where userid=?";
        try(Connection connection= dataSource.getConnection()){
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1,updatedRole.toString());
            preparedStatement.setString(2,userid);
        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw new DependencyFailureException(exception);
        }
    }
    public void updateEmailVerificationStatus(final String userId, final EmailVerificationStatus newStatus){
        String query= "UPDATE user set emailVerificationStatus =? where userId =?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1, newStatus.toString());
            preparedStatement.setString(2,userId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw  new DependencyFailureException(exception);
        }
    }
    public void updatePhoneVerificationStatus(final String userId, final PhoneVerificationStatus newStatus){
        String query= "UPDATE user set PhoneVerificationStatus =? where userId =?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1, newStatus.toString());
            preparedStatement.setString(2,userId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw  new DependencyFailureException(exception);
        }
    }
}


