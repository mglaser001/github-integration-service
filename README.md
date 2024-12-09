# GitHub Integration Service Application - Branch Demo
An application for fetching and aggregating GitHub data, built with Java and Spring Boot.

## Project Overview
The GitHub Integration Service is a backend service that integrates with GitHub's API. It aims to provide an aggregated view of Github data for users to easily parse. This application leverages Spring Boot's features, caching mechanisms, and asynchronous programming to deliver fast and scalable responses.

## Decisions and Architecture
### 1. Architecture
#### 1.1 Presentation Layer
* **GitHubUserController**
  *  Acts as the entry point for client requests.
  *  Returns a GitHubUserResponse in a ResponseEntity to clients.

#### 1.2 Service Layer
* **GitHubUserServiceImpl**
  *  Contains the main business logic for the request.
  *  Makes asynchronous calls with the GitHubClient to interact with GitHub APIs.
  *  Returns a GitHubUserResponse to the controller layer with the mapped values.
  *  Can return a cached responses to reduce API calls.
  
#### 1.3 Client Layer
* **GitHubClient**
  * A component class which is responsible for making GitHub API calls.

#### 1.3 Data Mapping Layer
* **GitHubUserMapper**
  * A utility which converts GitHub's responses into records to be returned in the response.

### 2. Design Decisions
#### 2.1 Separation of Concerns
* Classes and methods was developed following the Single Responsibility Principle to create a clean and maintainable codebase.
  * An example of this is shown in the business logic being held in the service layer while the controller handles the requests.

#### 2.2 Asynconous Processing
* The service uses CompletableFutures to perform non-blocking calls to the GitHub API. This leaverages asynchronous calls prevent blocking threads and reduce resource usage.
  
#### 2.3 Caching
* Caching GitHub response information at the service layer to minimizes the number of redundant API calls.
* Uses Spring Cache to store frequently requested data in memory to be quickly retrievable.
  
#### 2.4 Error Handling and Logging
* Created a custom ApiRequestException and custom exception handler to ensures that the client get meaningful error responses.
* SLF4J is used in the application to log info, debug, and error messages.

## How to Install and Run the Project
### Setup
#### 1. Clone the Repository 
```
git clone https://github.com/mglaser001/github-integration-service.git
```
#### 2. Navigate to the Project Directory
```
cd github-integration-service
```
#### 3. Build the Project
```
mvn clean install
```
#### 4. Run the Application
```
mvn spring-boot:run
```

## How to Use the Project
Once the application is running, users can fetch info from the following endpoints:

### API Endpoints
**1. Get User Details: /api/v1/github/users/{userName}**

 **Method: GET**
 
  Purpose: Fetch user details including login name, display name, avatar url, location, email, account creation date as well as a list of their public repositories.

#### Example Request:
```
curl http://localhost:8080/api/v1/github/users/octocat
```

#### Example Response:
```js
{
  "userName": "johndoe",
  "displayName": "John Doe",
  "avatar": "https://github.com/johndoe.png",
  "geoLocation": "San Francisco, CA",
  "email": "johndoe@example.com",
  "url": "https://github.com/johndoe",
  "createdAt": "2019-05-20",
  "repos": [
    {
      "name": "awesome-project",
      "htmlUrl": "https://github.com/johndoe/awesome-project"
    }
  ]
}
```

## Tests
#### Running Unit Tests
This project uses JUnit 5 for testing individual compenents. To run tests use the following command:

```js
mvn test
```
