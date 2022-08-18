package com.example.apinetflix.Controller;
import com.example.apinetflix.Controller.Model.LoginInput;
import com.example.apinetflix.Exception.InvalidCredentialsException;
//import com.example.apinetflix.Security.Roles;
import com.example.apinetflix.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;  //extracting email and password

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginInput loginInput) { //input as jason(@RequestBody)
        String email = loginInput.getEmail();
        String password = loginInput.getPassword();
        //  System.out.println("email=" + email +" and password =" +password);
        try {
            String token = authService.login(email, password); //return token
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (InvalidCredentialsException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());

        }

    }


  @PostMapping("logoutCurrentUser")
    @Secured({Roles.User, Roles.Customer})
    public  void logout(@RequestHeader(name = "Authorization",required = false) String authorizationHeader){
      String token =authorizationHeader.replace("Bearer", "");
      authService.logout(token);
  }
}
