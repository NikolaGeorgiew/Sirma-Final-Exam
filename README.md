# Football Player Pairing Application

**Overview**
 - This is a Spring Boot application designed to manage football matches, teams, players and their associated records. The objective is to calculate the duration two football players have played together in common matches. The application reads input data from CSV files and populates the database automatically. Determining and calculating shared playing time for specific pairs of players.

**Features**
 - **CRUD Operations:** The application supports standard CRUD operations for teams, players and matches.
 - **CSV Data Import:** CSV files are used to import data for teams, players, matches and match records into the database.
 - **Football Player Pairing Algorithm:** An algorithm computes the duration two players have played together in common matches, returning the result as JSON response.
 - **Validation:** The application validates and ensures proper data consistency when performing operations.
 - **Global Exception Handling:** All exceptions are managed via global exception handler using @RestControllerAdvice

**Technologies Used**
 - Java
 - Spring Boot
 - Spring Data JPA
 - PostgreSQL
 - Jakarta Persistence API
 - CSV File Handling
 - RESTful Web Services

**CSV Data Import Flow**
When the application starts, CSV files for teams, players, matches and records are loaded in the following order:
1. Teams
2. Players
3. Matches
4. Records

The data is loaded in the database automatically, ensuring that records are inserted in the correct sequence to maintain data consistency.

**Algorithm for Player Pairing**
The core functionality of this application is an algorithm that calculates the duration of two football players have played together in the same matches. Here's a high-level overview of the algorithm:
1. Input: For each match, the system loads records for all players and the time they spent on the field.
2. Processing: The algorithm identifies pairs of players who have played together in the same matches. It calculates the shared playing time by comparing the fromMinutes and toMinutes values for each player in a given match.
3. Output: The results, which show the total time two players have played together across multiple matches, are returned in JSON format.

**Endpoints**

| HTTP Method | Endpoint          | Description                          |
|-------------|--------------------|--------------------------------------|
| GET         | `/teams`           | Retrieve all teams                   |
| GET         | `/teams/{id}`      | Retrieve a specific team by ID       |
| POST        | `/teams/create`    | Create a new team                    |
| PUT         | `/teams/{id}`      | Update an existing team by ID        |
| DELETE      | `/teams/{id}`      | Delete a team by ID                  |
| GET         | `/players`         | Retrieve all players                 |
| GET         | `/players/{id}`    | Retrieve a specific player by ID     |
| POST        | `/players/create`  | Create a new player                  |
| PUT         | `/players/{id}`    | Update an existing player by ID      |
| DELETE      | `/players/{id}`    | Delete a player by ID                |
| GET         | `/matches`         | Retrieve all matches                 |
| GET         | `/matches/{id}`    | Retrieve a specific match by ID      |
| POST        | `/matches/create`  | Create a new match                   |
| PUT         | `/matches/{id}`    | Update an existing match by ID       |
| DELETE      | `/matches/{id}`    | Delete a match by ID                 |
| GET         | `/player-pairs`    | Get player pairs and their playtime  |
