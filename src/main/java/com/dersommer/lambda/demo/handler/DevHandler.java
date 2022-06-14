package com.dersommer.lambda.demo.handler;


import com.amazonaws.services.lambda.runtime.events.S3Event;
import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

// Dev Note: added just for debugging
public class DevHandler extends SpringBootRequestHandler<S3Event, String> {
}
