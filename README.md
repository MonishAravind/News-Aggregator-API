# News Aggregator RESTful API

## Project Brief

This project is a News Aggregator application built using Java and Spring Boot. The API allows users to register, log in, and manage their news preferences. It fetches news articles from external APIs based on user preferences, implements user authentication with bcrypt and JWT, and provides various endpoints for managing news articles.

## Requirements

1. **Set up a basic Java project with Spring Boot**
2. **User Registration and Login**
   - Use bcrypt for password hashing
   - Use JWT for token-based authentication
3. **In-memory Data Store**
   - Store user information and news preferences
4. **Implement RESTful API Endpoints:**
   - `POST /register`: Register a new user
   - `POST /login`: Log in a user
   - `GET /preferences`: Retrieve the news preferences for the logged-in user
   - `PUT /preferences`: Update the news preferences for the logged-in user
   - `GET /news`: Fetch news articles based on the logged-in user's preferences
5. **Fetch News Articles**
   - Use external news APIs to fetch news articles from multiple sources
   - Incorporate async/await and Promises for fetching and filtering news data
6. **Error Handling**
   - Handle invalid requests, authentication errors, and authorization errors
7. **Input Validation**
   - Validate user registration and news preference updates
8. **Testing**
   - Test the API using Postman or Curl

## Optional Extensions

1. **Caching Mechanism**
   - Cache news articles to reduce external API calls
   - Use async/await and Promises for cache updates and retrievals
2. **Article Management**
   - `POST /news/:id/read`: Mark a news article as read
   - `POST /news/:id/favorite`: Mark a news article as a favorite
   - `GET /news/read`: Retrieve all read news articles
   - `GET /news/favorites`: Retrieve all favorite news articles
3. **News Search**
   - `GET /news/search/:keyword`: Search for news articles based on keywords
4. **Periodic Cache Updates**
   - Implement a mechanism to periodically update the cached news articles in the background

## Setup Instructions

### Prerequisites

- Java 11 or higher
- Maven

### Step-by-Step Setup

1. **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/news-aggregator-api.git
    cd news-aggregator-api
    ```

2. **Build the project:**
    ```bash
    mvn clean install
    ```

3. **Run the application:**
    ```bash
    mvn spring-boot:run
    ```

### Configuration

- Update the `application.properties` file with your external news API configurations.

## API Endpoints

### User Registration and Login

- **Register a new user:**
  - `POST /register`
  - Body (JSON):
    ```json
    {
        "username": "exampleuser",
        "password": "examplepassword"
    }
    ```

- **Log in a user:**
  - `POST /login`
  - Body (JSON):
    ```json
    {
        "username": "exampleuser",
        "password": "examplepassword"
    }
    ```

### User Preferences

- **Retrieve user preferences:**
  - `GET /preferences`

- **Update user preferences:**
  - `PUT /preferences`
  - Body (JSON):
    ```json
    {
        "preferences": ["technology", "sports"]
    }
    ```

### News Articles

- **Fetch news articles based on preferences:**
  - `GET /news`

### Article Management (Optional)

- **Mark a news article as read:**
  - `POST /news/:id/read`

- **Mark a news article as a favorite:**
  - `POST /news/:id/favorite`

- **Retrieve all read news articles:**
  - `GET /news/read`

- **Retrieve all favorite news articles:**
  - `GET /news/favorites`

- **Search for news articles based on keywords:**
  - `GET /news/search/:keyword`

## Error Handling

- Proper error messages for invalid requests
- Authentication and authorization errors

## Input Validation

- Ensure valid data for user registration and news preference updates

## Testing the API

### Using Postman

1. **Register a new user:**
   - Method: POST
   - URL: `http://localhost:8080/register`
   - Body (JSON):
     ```json
     {
         "username": "exampleuser",
         "password": "examplepassword"
     }
     ```

2. **Log in a user:**
   - Method: POST
   - URL: `http://localhost:8080/login`
   - Body (JSON):
     ```json
     {
         "username": "exampleuser",
         "password": "examplepassword"
     }
     ```

3. **Retrieve user preferences:**
   - Method: GET
   - URL: `http://localhost:8080/preferences`
   - Headers: `Authorization: Bearer <JWT_TOKEN>`

4. **Update user preferences:**
   - Method: PUT
   - URL: `http://localhost:8080/preferences`
   - Headers: `Authorization: Bearer <JWT_TOKEN>`
   - Body (JSON):
     ```json
     {
         "preferences": ["technology", "sports"]
     }
     ```

5. **Fetch news articles:**
   - Method: GET
   - URL: `http://localhost:8080/news`
   - Headers: `Authorization: Bearer <JWT_TOKEN>`

### Using Curl

1. **Register a new user:**
   ```bash
   curl -X POST http://localhost:8080/register -H "Content-Type: application/json" -d '{
       "username": "exampleuser",
       "password": "examplepassword"
   }'
