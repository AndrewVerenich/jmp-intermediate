### Task 
1 point: 

Create DynamoDB table for products info (be sure to read): 
NOTE: product is some simple model with name, price, and picture_url: [created table][1]

1 point: 

Implement Lambda that processes uploading information about products from request body (be sure, that lambda uses assume role instead of raw credentials to access DynamoDB): [lambda source code][2]

1 point: 

Implement Lambda that processes updating information about products (be sure, that lambda uses assume role instead of raw credentials to access DynamoDB): [lambda source code][3]

Build lambdas: 
  
  ```markdown
cd module8/
mvn clean package shade:shade
  ```

1 point: 

Create API gateway and configure endpoints: [saveProduct lambda][4], [updateProduct lambda][5]  

1 point: 

Implement small console application with generated API gateway sdk (link) to create and update information 


[1]: attachments/dynamoDB.png
[2]: saveProductFunction
[3]: updateProductFunction
[4]: attachments/saveProductLambda.png
[5]: attachments/updateProductLambda.png