package com.example.server.controller;

import com.example.server.entity.Day;
import com.example.server.entity.Photo;
import com.example.server.entity.ResponseDay;
import com.example.server.service.DayService;
import com.example.server.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/records")
public class DayController {

    @Autowired
    private DayService dayService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/save")
    public ResponseEntity<String> saveRecord(@RequestParam("date") String date,
                                             @RequestParam("diary") String diary,
                                             @RequestParam("files") List<MultipartFile> files) throws IOException, ExecutionException, InterruptedException {
        // Firestore에 데이터 저장
        Day day = new Day();
        day.setDate(date);
        day.setDiary(diary);
        List<Photo> photos = new ArrayList<>();

        for (MultipartFile file : files) {
            // 이미지를 Firebase Storage에 업로드하고 다운로드 URL을 Firestore 데이터에 추가
            storageService.uploadImage(file,date, file.getOriginalFilename());
            String imageUrl = "photos/" + date + "/" + file.getOriginalFilename();
            photos.add(new Photo(imageUrl, "none"));
        }
        day.setPhotos(photos);


        // Firestore에 Record 저장
        dayService.saveDay(day);

        return ResponseEntity.ok("Record saved successfully");
    }
    @GetMapping("/load/{date}")
    public ResponseDay getRecord(@PathVariable String date) throws ExecutionException, InterruptedException, IOException {
        // Firestore에서 데이터 조회
        Day day = dayService.getDayInfo(date);
        String base64EncodedImage;
        List<String> imageList  = new ArrayList<>();
        List<Photo> photos = day.getPhotos();
        for ( Photo photo : photos) {
            String url = photo.getUrl();
            byte[] imageData = storageService.getImage(url);
            // 이미지 파일을 Base64로 인코딩하여 문자열로 변환
            imageList.add(Base64.getEncoder().encodeToString(imageData));
        }
        return new ResponseDay(day.getDate(),day.getDiary(), imageList);
    }


}

