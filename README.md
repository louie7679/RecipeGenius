## Recipe Recommender

### Overview

#### Project Original Thoughts:

The original concept behind this project was to create a recipe recommendation system that leverages the power of technology to assist users in discovering new and exciting recipes. The idea stemmed from a passion for cooking and the desire to simplify the process of finding recipes that match users' preferences and dietary restrictions. The project aimed to combine modern web development techniques and cloud computing to offer a seamless experience for food enthusiasts.


#### Project Technical Overview:

This application is developed in Spring Framework by using Spring Boot, Spring Data, Hibernate, Spring RESTful web services, Postman, Maven, PostgresSql, Docker, Amazon SQS, and Amazon S3.


#### Project Business Rules:

1. Object: User, Recipe, Ingredient

2. Relationships:
   * Each user can curate multiple recipes.
   * A recipe is the creative product of one user.
   * A recipe may consist of numerous ingredients.
   * Ingredients can be shared across multiple recipes.

#### Project Approach:

1. Created User, Recipe, Ingredient domain
1. Used Hibernate to do the database schema migration
1. Used JDBC to connect project with Postgres database
1. Configured Spring Security for Authentication
1. Created repository, service and did test
1. Did mock test for AWS S3 Storage service
1. Created Controllers and Restful APIs
1. Integrated third-party application AWS SQS and did Mock test
1. Used Postman to interact with back-end project
1. Package my project into a Docker image

#### Local PostgreSQL Container Setup

```
docker pull postgres
docker run --name RecipeRecDB -e POSTGRES_DB=RecipeRecDB -e POSTGRES_USER=${admin} -e POSTGRES_PASSWORD=${password} -p 5431:5432 -d postgres
```

#### Database Migration

```
mvn compile flyway:migrate -P unit -Ddb_url=${url} -Ddb_password=${password} -Ddb_username=${username}
```
#### Unit Testing

Prior to running unit tests, ensure you package and install the folder.

```
mvn clean compile install -DskipTests=true
```

Unit tests are executed using JUnit and Mockito. Navigate to the RecipeGenius/mvc folder and run:

```
mvn compile test -Dspring.profiles.active=${env} -P ${env}
mvn compile test -Dspring.profiles.active=${unit} -Daws.region=${region} -Ddb_url=${url} -Ddb.username=${username} -Ddb.password=${password} 
```

#### Packaging

```
mvn compile package -DoutputDirectory=./target
```

### Reference Demo

#### User Sign Up

Endpoint

```
POST - http://localhost:8080/signup
```

Request Body

 ```$xslt
{
    "name": "Edward",
    "password": "123456789",
    "secretKey": null,
    "firstName": "Edward",
    "lastName": "Louie",
    "email": "example@gmail.com"
}
```

Postman snapshot for user sign up

![User Sign Up](screenshots/Postman-02.png)

### User Login

Endpoint

```$xslt
POST http://localhost:8080/auth
```

Request Body

 ```$xslt
{
    "email": "dwang@training.ascendingdc.com",
    "password": "123456789"
}
```
Response Body
* Your JWT token here

```$xslt
eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoiZHdhbmciLCJpYXQiOjE2OTYwOTk4MDAsImlzcyI6ImNvbS5hc2NlbmRpbmciLCJleHAiOjE2OTYxODYyMDAsImFsbG93ZWRSZXNvdXJjZXMiOiIvLC91c2VyLC9yZWNpcGUsL2luZ3JlZGllbnQsL3VzZXIsL3JlY2lwZSwvaW5ncmVkaWVudCIsImFsbG93ZWRSZWFkUmVzb3VyY2VzIjoiLywvdXNlciwvcmVjaXBlLC9pbmdyZWRpZW50LC91c2VyLC9yZWNpcGUsL2luZ3JlZGllbnQiLCJhbGxvd2VkQ3JlYXRlUmVzb3VyY2VzIjoiLywvdXNlciwvcmVjaXBlLC9pbmdyZWRpZW50IiwiYWxsb3dlZFVwZGF0ZVJlc291cmNlcyI6Ii8sL3VzZXIsL3JlY2lwZSwvaW5ncmVkaWVudCIsImFsbG93ZWREZWxldGVSZXNvdXJjZXMiOiIvIn0.uwxLouKjQCK-G2_89EQReFhEbkNlU6HbwKH_qYY5Whs
```

Postman snapshoot for user login

![User Login](screenshots/Postman-01.png)

### Successful AWS CloudFormation Deployment

Screenshot of the successful deployment of AWS CloudFormation stack, which sets up the infrastructure for the project.

![CloudFormation Stack](screenshots/CloudFormation.png)

### Successful AWS CodePipeline Deployment

Screenshot of AWS CodePipeline dashboard, indicating a successful deployment pipeline execution.

![CodePipeline Dashboard](screenshots/CodePipeline-01.png)
![CodePipeline Dashboard](screenshots/CodePipeline-02.png)
![CodePipeline Dashboard](screenshots/CodePipeline-03.png)

