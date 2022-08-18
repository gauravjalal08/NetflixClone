package com.example.apinetflix.Accessor.Model;

import lombok.Builder;

import lombok.Getter;

import java.sql.Date;

@Builder
@Getter
public class OtpDTO {
    private String otpId;
    private String userId;
    private String otp;
    private OtpState state;
    private Date createdAt;
    private OtpSentTo sentTo;
}
