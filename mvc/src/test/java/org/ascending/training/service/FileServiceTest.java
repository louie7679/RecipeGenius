package org.ascending.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.ascending.training.ApplicationBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBootstrap.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @Autowired
    private AmazonS3 s3Client;

    // @Mock
    // File file;

    @Mock
    MultipartFile file;

//    @Test
//    public void uploadFileTest_happyPath() throws IOException {
//        // File file = new File("/Users/tengfeilouie/IdeaProjects/coffee.jpg");
//        fileService.uploadFile("tengfei-recipe-mvc", file);
//        verify(s3Client, times(1)).putObject(any(PutObjectRequest.class));
//    }

    @Test
    public void uploadFileTest_fileIsNull() {}
}
