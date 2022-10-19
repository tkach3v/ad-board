# Spring Boot, PostgreSQL, Spring Security, Spring Data, JPA, Rest API, JUnit

Built RESTful API for an ad board using Spring Boot, PostgreSQL, Spring Security, Spring Data, JPA, Rest API, JUnit, MapStruct, Luquibase, Swagger, Spring Validation.

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/tkach3v/ad-board.git
```

**2. Create PostgreSQL database**
```bash
create database ad_board
```

**3. Change PostgreSQL username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

**4. Run the app using Maven**

```bash
mvn spring-boot:run
```

- The app will start running at <http://localhost:8080>
- Swagger UI available at <http://localhost:8080/api/v1/swagger-ui/index.html>
