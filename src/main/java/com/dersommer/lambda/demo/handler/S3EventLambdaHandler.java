package com.dersommer.lambda.demo.handler;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

/**
 * This is not needed in recent Spring Boot versions.
 */
public class S3EventLambdaHandler extends SpringBootRequestHandler<S3Event, String> {
}
