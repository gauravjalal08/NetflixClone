package com.example.apinetflix.Controller.Model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WatchHistoryInput {
    private String profileId;
    private int watchTime;


}
