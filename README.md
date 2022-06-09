# Deploy

    aws lambda update-function-code --function-name demo-lambda-s3-spring --zip-file fileb://target/aws-lambda-s3-0.0.1-SNAPSHOT-aws.jar --profile da

## Trigger

     aws s3 cp .\results.zip s3://odx-dev-bucket --profile da
