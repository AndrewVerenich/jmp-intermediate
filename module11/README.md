### Task 
At the end of this homework you need to have:

- Trigger for DynamoDB put the action in your collection from the previous task;
- Lambda that will create a simple HTML page with content from your DynamoDB and put it in S3;
- S3 bucket with configured static website hosting.   
     
1 point 

- Create DynamoDB trigger
 
3 points

- Implement Lambda that processes data from DynamoDB on trigger, create an HTML page with products, and puts it in S3 

1 point     

- Configure S3 static website hosting

**RESULTS:**

Please see task [source code](../module11/product-to-html-function), which implements lambda function.

AWS settings:

- [Lambda](attachments/lambda.png) that processes data from DynamoDB on trigger
- [S3 bucket](attachments/bucket_products.png) for HTML files
- [S3 settings](attachments/static.png), which is configured for static website hosting


Build lambda: 
  
  ```markdown
cd module11/
mvn clean package shade:shade
  ```
  
Testing app:
- Create order using saveProductFunction from [module 8](../module8/saveProductFunction)
- Implemented lambda processes data using DynamoDB trigger
- [Html product order](attachments/html.png) will be created in S3 bucket, html uses logo image as static content example