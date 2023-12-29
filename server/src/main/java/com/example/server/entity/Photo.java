package com.example.server.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Photo {
    // Getter 및 Setter 메서드
    private String url;
    private String who;

    public Photo() {
    }
    public Photo(String url, String who) {
        this.url = url;
        this.who = who;
    }

}