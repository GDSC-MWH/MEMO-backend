package com.example.server.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Document(collectionName = "Calender")
public class Day {
    @DocumentId
    private String date;
    private String diary;
    private List<Photo> photos;

}
