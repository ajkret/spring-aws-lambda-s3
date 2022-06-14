package com.dersommer.lambda.demo;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Profiles;

@SpringBootApplication
public class AwsLambdaS3Application {
  Logger log = LoggerFactory.getLogger(AwsLambdaS3Application.class);

  public static void main(String[] args) {
    ConfigurableApplicationContext context = FunctionalSpringApplication.run(AwsLambdaS3Application.class, args);

    // Debug purposes
    if (context.getEnvironment().acceptsProfiles(Profiles.of("dev"))) {
      String functionName = Optional.ofNullable(args).filter(parms -> parms.length > 0).map(parms -> parms[0]).orElse("consumeS3Demo");
      Function<S3Event, String> function = context.getBean(functionName, Function.class);
      function.apply(createS3Event());
    }
  }

  private static S3Event createS3Event() {

    List<S3EventNotification.S3EventNotificationRecord> records = new ArrayList<>();
    S3Event event = new S3Event(records);

    records.add(new S3EventNotification.S3EventNotificationRecord(
        "eu-central-1",
        UUID.randomUUID().toString(),
        "ObjectCreated:Put",
        "1970-01-01T00:00:00.000Z",
        "2.0",
        new S3EventNotification.RequestParametersEntity("127.0.0.1"),
        null,
        new S3EventNotification.S3Entity("testConfigRule",
            new S3EventNotification.S3BucketEntity("odx-dev-bucket", new S3EventNotification.UserIdentityEntity("EXAMPLE"), ""),
            new S3EventNotification.S3ObjectEntity("results.zip", 1042510L, "0123456789abcdef0123456789abcdef", "1.0", "0A1B2C3D4E5F678901"),
            "1.0"),
        new S3EventNotification.UserIdentityEntity("EXAMPLE")));

    return event;
  }
}
