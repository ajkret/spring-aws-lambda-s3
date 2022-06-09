package com.dersommer.lambda.demo.util;

import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.dersommer.lambda.demo.AwsLambdaS3Application;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class S3Helper {
  private static final Logger log = LoggerFactory.getLogger(AwsLambdaS3Application.class);

  public Optional<File> downloadFileToLocalDisk(S3EventNotification.S3Entity s3) {
    // Download the image from S3 into a stream
    AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
    S3Object s3Object = s3Client.getObject(new GetObjectRequest(s3.getBucket().getName(), s3.getObject().getUrlDecodedKey()));

    // Create a temporary folder
    File tmpdir = new File(String.format("%s%c%s", System.getProperty("java.io.tmpdir"), File.separatorChar, UUID.randomUUID().toString()));
    tmpdir.mkdirs();

    try (ZipInputStream objectData = new ZipInputStream(s3Object.getObjectContent())) {

      // Uncompress into new folder
      ZipEntry entry;
      while ((entry = objectData.getNextEntry()) != null) {
        if (entry.isDirectory()) {
          log.info("Creating directory {}", entry.getName());
          new File(String.format("%s%c%s", tmpdir.getAbsolutePath(), File.separatorChar, entry.getName())).mkdirs();
          continue;
        }

        String outputFileName = String.format("%s%c%s", tmpdir.getAbsolutePath(), File.separatorChar, entry.getName());
        log.info("S3 file: extracting {}", outputFileName);
        try (OutputStream output = new FileOutputStream(outputFileName)) {
          byte b[] = new byte[256];
          int i;
          while ((i = objectData.read(b)) > 0) {
            output.write(b, 0, i);
          }
          output.flush();
        }
      }

      return Optional.of(tmpdir);
      // Do something with the output folder
    } catch (IOException e) {
      log.error("Problem processing {}", s3.getObject().getUrlDecodedKey());
    }

    return Optional.empty();
  }
}
