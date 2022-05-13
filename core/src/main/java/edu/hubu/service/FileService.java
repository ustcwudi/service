package edu.hubu.service;

import edu.hubu.config.MinioConfiguration;
import edu.hubu.auto.dao.UploadMongoDao;
import edu.hubu.auto.model.Upload;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Component
public class FileService {

    private final MinioClient minioClient;

    private final String bucket;

    @Autowired
    private UploadMongoDao uploadMongoDao;

    @Autowired
    public FileService(MinioConfiguration configuration) {
        bucket = configuration.getBucket();
        minioClient = MinioClient.builder()
                .endpoint(configuration.getEndpoint())
                .credentials(configuration.getAccessKey(), configuration.getSecretKey())
                .build();
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String upload(MultipartFile file, String folder) {
        var upload = new Upload();
        upload.setName(file.getName());
        uploadMongoDao.add(upload);
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(folder + upload.getId())
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return upload.getId();
    }

    public GetObjectResponse download(String folder, String fileName, HttpServletResponse response) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucket).object(folder + fileName).build());
    }
}
