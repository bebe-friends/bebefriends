package com.bbf.bebefriends.global.utils.file;

import com.bbf.bebefriends.community.exception.CommunityControllerAdvice;
import com.bbf.bebefriends.global.exception.ResponseCode;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FireBaseService {
    @Value("${firebase.bucket-name}")
    private String firebaseStorageUrl;

    // 파일 업로드
    public String uploadFirebaseBucket(MultipartFile multipartFile) {
        try {
            Bucket bucket = StorageClient.getInstance().bucket(firebaseStorageUrl);
            String fileName = generateUniqueFileName(multipartFile.getOriginalFilename());

            Blob blob = bucket.create(fileName,
                    multipartFile.getInputStream(),
                    multipartFile.getContentType());

            blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

            return blob.getMediaLink(); // 파이어베이스에 저장된 파일 url
        } catch (IOException e) {
            throw new CommunityControllerAdvice(ResponseCode.FILE_UPLOAD_FAIL);
        }
    }

    // 파일 삭제
    public void deleteFirebaseBucket(String key) {
        Bucket bucket = StorageClient.getInstance().bucket(firebaseStorageUrl);

        bucket.get(key).delete();
    }

    // 파일 이름 충돌 방지
    private String generateUniqueFileName(String originalFilename) {
        String extension = "";
        int pos = originalFilename.lastIndexOf('.');
        if (pos > 0) {
            extension = originalFilename.substring(pos);
        }
        return UUID.randomUUID().toString() + extension;
    }
}
