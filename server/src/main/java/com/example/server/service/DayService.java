package com.example.server.service;

import com.example.server.entity.Day;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import com.google.cloud.firestore.WriteResult;
import java.util.concurrent.ExecutionException;

@Service
public class DayService {
    public static final String COL_NAME = "Calender";

    public String saveDay(Day day) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture =
                dbFireStore.collection(COL_NAME).document(day.getDate()).set(day);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Day getDayInfo(String date) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        DocumentReference documentReference =
                dbFireStore.collection(COL_NAME).document(date);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        Day day  = null;
        if (!document.exists()) {
            return null;
        }
        day = document.toObject(Day.class);
        return day;
    }

}
