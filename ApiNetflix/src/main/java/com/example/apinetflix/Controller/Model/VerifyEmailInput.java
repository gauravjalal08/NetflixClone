package com.example.apinetflix.Controller.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifyEmailInput {
    private String otp;
}
