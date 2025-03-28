## Design Decisions

- **Backend**: Implemented using Spring Boot 3.4.2 with a RESTful architecture.
- **Database**: MySQL chosen for simplicity and relational data modeling.
- **API Documentation**: Integrated using Springdoc OpenAPI (Swagger 2.3.0).
- **Error Handling**: Custom exception handlers using `@ControllerAdvice` for clean JSON error responses.
- **Entities**: entities (`Account`, `Transaction`) designed for clarity, with JPA annotations.
## Challenges Encountered

- **Swagger Compatibility Issues**: Faced `NoSuchMethodError` due to version mismatches between `springdoc-openapi` and Spring Boot 3.x. Resolved by using `springdoc-openapi-starter-webmvc-ui` version 2.3.0.
- **Lombok Builder Errors**: Initial test cases failed because `@Builder` was missing from entity classes.
## API Documentation

The API is documented using **OpenAPI 3.0 / Swagger UI**.

 Swagger UI: [http://localhost:8094/swagger-ui.html](http://localhost:8094/swagger-ui.html)  

 
 ![Image](https://github.com/user-attachments/assets/e93f2781-56bb-40cf-a790-1caf5586f12e)

