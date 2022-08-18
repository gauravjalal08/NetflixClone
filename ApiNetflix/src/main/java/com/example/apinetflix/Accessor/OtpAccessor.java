package com.example.apinetflix.Accessor;
import com.example.apinetflix.Accessor.Model.OtpDTO;
import com.example.apinetflix.Accessor.Model.OtpSentTo;
import com.example.apinetflix.Accessor.Model.OtpState;
import com.example.apinetflix.Exception.DependencyFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class OtpAccessor {

    @Autowired
    DataSource dataSource;

    public OtpDTO getUnusedOtp(final String userId, final String otp) {
        String query = "SELECT * from otp where userId = ? and otp = ? and state = ?";
        try (Connection connection= dataSource.getConnection()){
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1,userId);
            preparedStatement.setString(2,otp);
            preparedStatement.setString(3, OtpState.UNUSED.toString());
            ResultSet resultSet= preparedStatement.executeQuery();
            if (resultSet.next()){
                return OtpDTO.builder()
                        .otpId(resultSet.getString(1))
                        .userId(userId)
                        .otp(otp)
                        .state(OtpState.UNUSED)
                        .createdAt( resultSet.getDate(5))
                        .sentTo(OtpSentTo.valueOf(resultSet.getString(6)))
                        .build();

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
    public void updateOtpState(final String otpId, final OtpState otpState){
        String query= "UPDATE otp set state=? where otpId=? ";
        try(Connection connection= dataSource.getConnection()){
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1,otpState.toString());
            preparedStatement.setString(2,otpId);
            preparedStatement.executeUpdate();
        }
        catch (SQLException exception){
            exception.printStackTrace();
            throw new DependencyFailureException(exception);
        }
    }

}
