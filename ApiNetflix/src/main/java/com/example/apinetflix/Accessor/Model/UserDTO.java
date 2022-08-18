package com.example.apinetflix.Accessor.Model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //---used when we have more fields

public class UserDTO {        //USERDTO---Whatever we are fetching from DB
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phoneNo;
    private UserState state;
   private UserRole role;
   private EmailVerificationStatus emailVerificationStatus;
   private PhoneVerificationStatus phoneVerificationStatus;
}
