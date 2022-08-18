package com.example.apinetflix.Accessor.Model;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class ShowDTO {
    private String showId;
    private String name;
    private ShowType  typeOfShow;
    private ShowGenre  genre;
    private  ShowAudience audience;
    private  double rating;
    private int length;




}
