# Vision Housing Server

This server demonstrates how one might implement services to return and update data concerning houses and their owners from a housing database.
- List all house records in the database, returning details for each house.
- Return the details for a house with a specified ID.
- Update the details for a house with a specified ID.

The service is implemented using Spring Boot, Java, and a variety of open-source libraries.

# Environment
This server was implemented on a Mac desktop. In principle it should work on any *NIX computer. (Enhancement for a Windows platform is touched on below.) The entire development project - including generated code for runtime - is included in this zip file.

# Deployment and Execution

Unzip these project materials to the directory of your choice.

Maven is required to build the project. To run the server, you can use maven, or you can run it from the generated executable jar file.

The project was developed using maven and java:
- apache-maven-3.6.0
- Java version: 1.8.0_131

The server can be started from the executable jar that resides in the /target directory, from there, or after copying to a directory of your choice. The following command will start the server - suitably adjusted for the working directory and the location of the jar.

    java -jar target/housing-0.0.1-SNAPSHOT.jar

The server can be started using maven. From the project root directory (/vision), use the following command to start the server.

    mvn spring-boot:run

# Exercising the Server
After starting, the server listens on port 8080. You can use Curl from the command line to retrieve data, using the following commands. (The URLS should also work if typed or copied into a browser address bar.)

    // Retrieve the info for one house.
    curl localhost:8080/api/houses/1

    // Retrieve the info for all of the houses 
    curl localhost:8080/api/houses
    
You can also use curl to update the information for one of the houses.

    // Update the info for house with ID 10
    curl -X PUT -H "Content-Type: application/json" -d '{"ownerFirstName":"Joe","ownerLastName":"Jones","street":"South St","city":"Hudson","state":"MA","zip":"01749","propertyType":"Multi Family","location":"http://localhost:8080/api/houses/1"}' http://localhost:8080/api/houses/10

# Building the Server

As mentioned, maven was used for building and testing the server. To (re-)build, use the following command from the project root.

    // Build the server
    mvn clean install

Use the following command to run integration tests.

    // Run integration tests
    mvn verify

The following command will run the unit tests (which don't test anything).

	// Run unit tests
	mvn test


# Implementation Details

The service is implemented in two layers:
- A controller, which implements the endpoints for the services. This handles all incoming requests, queries for house data and for house data to be updated, and returns the results of the request.
-  A data-access access layer which holds the initial house data, makes updates to house records, and responds to requests for the data. (Note that there is no real database management system or "external" storage; when the server is stopped, updated data is lost. This was the result of a judgement call on scope.)

The project was started by using the Spring Boot (online) Initializer. Maven was used for all building and automated testing.

At runtime, the Spring Boot environment 
- starts the application
- routes requests to the correct handlers in the controller,
- integrates the controller and DAO layers (injecting DAO into the controller).

Automated testing is limited to Integration Tests, which test the three services "happy path". (This was also the result of a judgement call on scope - and because of time spent wrangling "mocking" logic in - abandoned - unit tests.)


# Possible Enhancements
Here is a list of desirable changes to this implementation. 

The application design could be expanded to support
- an endpoint to add a new house.
- an endpoint to delete a house.
- endpoints for retrieving subsets of houses in the database - say by state, city, zip, propert type, etc.

The way the server was implemented immediately suggests at least the following.
- Implement using a real database server. Use a more robust scheme for identifying houses - perhaps DB-assigned GUIDs.
- Unit tests, including the "unhappy paths". This will probably reveal the need for better exception handling.
- More robust back-end architecture - for example introducing a tier for business logic - maybe "managers".
- Configurability of listening port, logging output.
- Deployment to Docker.

