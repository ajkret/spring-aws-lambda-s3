package com.dersommer.lambda.demo.functions;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.dersommer.lambda.demo.util.S3Helper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// Dev Note: this class is a Component, but it is registered using application.yml - spring.cloud.function.scan.packages
public class ConsumeS3Demo implements Function<S3Event, String[]> {

  // We don't really need a separate bean, it is more a test to check if the bean is injected
  private S3Helper s3helper;
  private ObjectMapper mapper = new ObjectMapper();

  public ConsumeS3Demo(S3Helper s3helper) {
    this.s3helper = s3helper;
  }

  @Override
  public String[] apply(S3Event event) {

    // How many events a Lambda can receive
    return event.getRecords().stream().map(record -> processEvent(record)).collect(Collectors.toList()).toArray(new String[event.getRecords().size()]);
  }

  // Process each event
  private String processEvent(S3EventNotification.S3EventNotificationRecord record) {
    log.info("Received event: {} {} {} {}", record.getEventName(), record.getEventTime().toString(), record.getS3().getBucket().getName(),
        record.getS3().getObject().getUrlDecodedKey());

    // Download file from S3
    Optional<File> tmpdir = s3helper.downloadFileToLocalDisk(record.getS3());

    tmpdir.ifPresent(dir -> doSomethingWithTheTempDir(dir));

    return "";
  }

  // Continue processing...
  private void doSomethingWithTheTempDir(File dir) {
    log.info("Received {} to process", dir.getAbsolutePath());
  }
}
