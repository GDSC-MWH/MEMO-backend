package com.example.server.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {

    @Value("${app.firebase-bucket}")
    private String firebaseBucket;


    public String uploadImage(MultipartFile file, String date, String filename) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/serviceAccountKey.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("MEMO").build().getService();
        String filePath = "photos/" + date + "/" + filename;
        BlobId blobId = BlobId.of(firebaseBucket, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();
        InputStream content = new ByteArrayInputStream(file.getBytes());

        // Writer를 사용하여 파일 업로드
        try (WriteChannel writer = storage.writer(blobInfo)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = content.read(buffer)) != -1) {
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, bytesRead);
                writer.write(byteBuffer);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }
        return storage.get(blobId).getMediaLink();
    }

    public byte[] getImage(String url) throws IOException {
        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/serviceAccountKey.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("MEMO").build().getService();
        try {
            BlobId blobId = BlobId.of(firebaseBucket, url);
            Blob blob = storage.get(blobId); // your-bucket-name은 실제 버킷 이름으로 대체되어야 합니다.
            System.out.println(blob);
            return blob.getContent();
        } catch (NullPointerException e) {
            System.out.println(e);
        } catch (Exception e) {
        }
        return new byte[0];
    }
}