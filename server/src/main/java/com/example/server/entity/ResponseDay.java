package com.example.server.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter

public class ResponseDay {
    private String date;
    private String diary;
    private List<String> images;

    // Constructors, getters, and setters

    public ResponseDay(String date, String diary, List<String> image) {
        this.date = date;
        this.diary = diary;
        this.images = image;
    }

    // Other methods...
}
