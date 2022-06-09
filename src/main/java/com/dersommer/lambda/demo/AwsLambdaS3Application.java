package com.dersommer.lambda.demo;

import java.util.function.Function;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AwsLambdaS3Application {

	public static void main(String[] args) {
		SpringApplication.run(AwsLambdaS3Application.class, args);
	}


	/**
	 * The project can have multiple function (Lambda) implementations in the same jar/zip file deployed on AWS.
	 * Which Function to call is defined in the Function Name at AWS Console. Check the package "functions".
	 * It is also possible to define in the property: <b>spring.cloud.function.definition=myExampleFunction</b>
	 * @return
	 */
	@Bean
	public Function<String, String> reverseString() {
		return value -> new StringBuilder(value).reverse().toString();
	}

}
