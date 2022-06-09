package com.dersommer.lambda.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionalSpringApplication;

@SpringBootApplication
public class AwsLambdaS3Application {
  Logger log = LoggerFactory.getLogger(AwsLambdaS3Application.class);

  public static void main(String[] args) {
    FunctionalSpringApplication.run(AwsLambdaS3Application.class, args);
  }


  /**
   * The project can have multiple function (Lambda) implementations in the same jar/zip file deployed on AWS.
   * Which Function to call is defined in the Function Name at AWS Console. Check the package "functions".
   * It is also possible to define in the property: <b>spring.cloud.function.definition=myExampleFunction</b>
   *
   * @return
   */
	/*
	@Bean
	public Function<String, String> reverseString() {
		return value -> new StringBuilder(value).reverse().toString();
	}
	 */
}