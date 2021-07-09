### Task 
Create demo applications that implement the following order processing flow. 

First application read the following order details from console and send it to the order queue (orders):

- The user who makes the order;
- Type of goods for the order – liquids or countable item;
- The volume of order for liquids;
- Number of items for countable items;
- Order total.

Second application should process orders with the following rules:

- If order total greater than some threshold – order should be rejected;
- If already ordered more than N liters – the order should be rejected;
- Summary information for accepted and rejected logs should be passed to other queues or topics.

Third application to log summary about accepted and rejected orders into some file.

Tasks to be implemented:

- Implement the full flow described above – 2 points.
- Use message selectors to split orders for liquids and countable items – 1 point. COMMENT: AWS SQS does not support message selectors as well as transactions in message processing.
- Use topics to implement message exchange – 1 point.
- Add trigger to S3 bucket that will send message to SQS that file was changed. - 1 point.

**RESULTS:**

Please see task [source code](../module10), which implements the full flow.

AWS settings:

- [SQS queues](attachments/sqs.png)
- [S3 bucket](attachments/bucket.png)
- [SNS topic](attachments/sns.png) to send email notifications
- [S3 bucket event](attachments/event.png), which triggers SNS notification on PUT S3 object event


Running app app on EC2 instance:

Build project locally: 
  
  ```markdown
cd module10/
mvn install
  ```

Copy executable jar to EC2 instance: 
  
  ```markdown
scp -i ~/Downloads/awsdemo.pem firstapp-1.0-SNAPSHOT.jar ec2-user@3.95.236.237:~/
scp -i ~/Downloads/awsdemo.pem secondapp-1.0-SNAPSHOT.jar ec2-user@3.95.236.237:~/
scp -i ~/Downloads/awsdemo.pem thirdapp-1.0-SNAPSHOT.jar ec2-user@3.95.236.237:~/
  ```
  
Run jar on EC2 instance:

  ```markdown
java -jar firstapp-1.0-SNAPSHOT.jar
java -jar secondapp-1.0-SNAPSHOT.jar
java -jar thirdapp-1.0-SNAPSHOT.jar
  ```
  
Testing app:
- [Running firstapp](attachments/firstapp.png)
- [Running secondapp](attachments/secondapp.png)
- [Running thirdapp](attachments/thirdapp.png)
- [Writing to file](attachments/file.png)