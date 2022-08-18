package com.example.apinetflix.Accessor.Model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthDTO {
    private String authID;
    private String token;
    private String userId;

    AuthDTO(String authID, String token, String userId) {
        this.authID = authID;
        this.token = token;
        this.userId = userId;
    }

    public static AuthDTOBuilder builder() {
        return new AuthDTOBuilder();
    }

    public static class AuthDTOBuilder {
        private String authID;
        private String token;
        private String userId;

        AuthDTOBuilder() {
        }

        public AuthDTOBuilder authID(String authID) {
            this.authID = authID;
            return this;
        }

        public AuthDTOBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthDTOBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public AuthDTO build() {
            return new AuthDTO(authID, token, userId);
        }

        public String toString() {
            return "AuthDTO.AuthDTOBuilder(authID=" + this.authID + ", token=" + this.token + ", userId=" + this.userId + ")";
        }
    }
}
