package com.example.apinetflix.Controller.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateProfileInput {
    private String name;
    private ProfileTypeInput type;
}
