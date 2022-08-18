package com.example.apinetflix.Service;

import com.example.apinetflix.Accessor.Model.*;
import com.example.apinetflix.Accessor.OtpAccessor;
import com.example.apinetflix.Accessor.UserAccessor;
import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;
//purpose of servicelayer---1)call user accessor----2)validation
@Component
public class UserService {

    @Autowired   //intrect with userAccessor
    private UserAccessor userAccessor;
    @Autowired
    private OtpAccessor otpAccessor;

    public void addNewUser(final String email, final String name, final String password, final String phoneNo) throws InvalidDataException {
        if (phoneNo.length() != 10) {
            throw new InvalidDataException("phone No" + phoneNo + " is invalid!");
        }
        if (password.length() < 6) {
            throw new InvalidDataException("password" + password + " not secured to use!");

        }
        if (name.length() < 5) {
            throw new InvalidDataException("name" + name + "Name is invalid!");

        }
// for validating email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);  //patter class to compile regex
        if (!pat.matcher(email).matches()) {
            throw new InvalidDataException("Email is Invalid");
        }
        {
            UserDTO userDTO = userAccessor.getUserByEmail(email);
            if (userDTO != null) {
                throw new InvalidDataException("User with the given  email is already exists!");

            }
            userDTO = userAccessor.getUserByPhoneNo(phoneNo);
            if (userDTO != null) {
                throw new InvalidDataException("user with the given phoneNo is already exists!");
            }

            userAccessor.addNewUser(email, name, password, phoneNo, UserState.Active, UserRole.ROLE_USER);

        }
    }

    public void activateSubscription() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        userAccessor.updateRole(userDTO.getUserId(), UserRole.ROLE_CUSTOMER);
    }

    public void deleteSubscription() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        UserDTO userDTO = (UserDTO) authentication.getPrincipal();
        userAccessor.updateRole(userDTO.getUserId(), UserRole.ROLE_USER);
    }
    public void verifyEmail(final String otp){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO=(UserDTO) authentication.getPrincipal();
        if(userDTO.getEmailVerificationStatus().equals(EmailVerificationStatus.UNVERIFIED)){
           OtpDTO otpDTO=otpAccessor.getUnusedOtp(userDTO.getUserId(),otp);
           if(userDTO!=null){
               userAccessor.updateEmailVerificationStatus(userDTO.getUserId(), EmailVerificationStatus.UNVERIFIED);
               otpAccessor.updateOtpState(otpDTO.getOtpId(),OtpState.USED);
           }
           else {
               throw new com.example.apinetflix.Exception.InvalidDataException("Otp doesn't exist");
           }
        }
    }
    public void verifyPhone(final String otp){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDTO userDTO=(UserDTO) authentication.getPrincipal();
        if(userDTO.getPhoneVerificationStatus().equals(PhoneVerificationStatus.UNVERIFIED)){
            OtpDTO otpDTO=otpAccessor.getUnusedOtp(userDTO.getUserId(),otp);
            if(userDTO!=null){
                userAccessor.updatePhoneVerificationStatus(userDTO.getUserId(), PhoneVerificationStatus.UNVERIFIED);
                otpAccessor.updateOtpState(otpDTO.getOtpId(),OtpState.USED);
            }
            else {
                throw new com.example.apinetflix.Exception.InvalidDataException("Otp doesn't exist");
            }
        }
    }
}
