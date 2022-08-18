package com.example.apinetflix.Controller;

import com.example.apinetflix.Accessor.CreateUserInput;
import com.example.apinetflix.Controller.Model.VerifyEmailInput;
import com.example.apinetflix.Controller.Model.VerifyPhoneNoInput;
import com.example.apinetflix.Security.Roles;
import com.example.apinetflix.Service.UserService;
import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService; //intrect with userService

    @PostMapping("/user")
    public ResponseEntity<String> addNewUser(@RequestBody CreateUserInput createUserInput) {
        String name = createUserInput.getName();
        String email = createUserInput.getEmail();
        String phoneNo = createUserInput.getPhoneNo();
        String password = createUserInput.getPassword();
        try {
            userService.addNewUser(name, email, phoneNo, password);
            return ResponseEntity.status(HttpStatus.OK).body("User created successfully");
        } catch (InvalidDataException exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }


    @PostMapping("/user/subscription")
        @Secured({Roles.User})
        public String activateSubscription () {
            userService.activateSubscription();
            return "Subscription activated successfully";
        }
    @DeleteMapping("/user/subscription")
    @Secured({Roles.Customer})
    public String deleteSubscription () {
        userService.deleteSubscription();
        return "Subscription deactivated ";
    }
    @PostMapping("/user/email")
    @Secured({Roles.User,Roles.Customer})
    public ResponseEntity<String> verifyEmail(@RequestBody VerifyEmailInput emailInput){
        try{
            userService.verifyEmail(emailInput.getOtp());
            return ResponseEntity.status(HttpStatus.OK).body("Otp verified Successfully");

        }
        catch (com.example.apinetflix.Exception.InvalidDataException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
    @PostMapping("/user/phoneNo")
    @Secured({Roles.User,Roles.Customer})
    public ResponseEntity<String> verifyPhoneNo(@RequestBody VerifyPhoneNoInput phoneNoInput){
        try{
            userService.verifyEmail(phoneNoInput.getOtp());
            return ResponseEntity.status(HttpStatus.OK).body("Otp verified Successfully");

        }
        catch (com.example.apinetflix.Exception.InvalidDataException exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }
    }




