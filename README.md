"# CashAccountManagement" 


Given a cash account, its current balance and a threshold :
When a transaction happens,
1. The account needs to be credited to when the new balance is below the threshold
2. The account needs to be debited from when the new balance is above the threshold
3. You could decide on the medium of input transaction.


Dependency Added	
   - spring-boot-starter-data-jpa - Used to connect database access and query generation
   - spring-boot-starter-web -Used to run the web application and provide necessary dependencies and configuration for RESTful webservices
   - H2 -Used as an in-memory database (stored in system's memory)
   - springdoc-openapi-starter-webmvc-ui- Used for API documentation and swagger UI integration
   - Lombok -Used for builder pattern ,constructors, automatic generation of getter and setter
   - spring-boot-starter-validation - Used for validating the input by adding automatic validation and common annotation
   - spring-boot-starter-test -Used for integration and unit testing

<img width="895" alt="image" src="https://github.com/user-attachments/assets/7bcbf5aa-bc3a-4ee1-8674-c406aa420eb5">
