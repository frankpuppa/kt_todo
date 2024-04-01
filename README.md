## Ktor based ToDo API

A simple ToDo list API written in kotlin. <br>
The queries to the database are executed in the form of JDBC prepared statements.

#### Start the Database
cd into the the docker folder and run: <br>
```docker-compose up```
<br>
This will create the database and initialize it with some test data

#### Postam collection
Inside the postman folder you can find the API collection

#### Build the application
```./gradlew build -x test```

#### Run default tests
```./gradlew test```

#### Run the application
```./gradlew run```
