# Car Dealership Management System🚖🚘

Welcome to the Car Dealership Management System repository! This project is an intermediate-level skills demonstration that encompasses various tools and frameworks around Java. The primary goal of this project is to develop a robust Java-based application tailored for managing various aspects of a car dealership's operations.

## Project Overview💻

The Car Dealership Management System allows users to perform the following tasks:
- Purchasing vehicles
- Requesting services
- Specifying services and spare parts used during servicing
- Viewing service history

## Features and Technologies Used🚀

- **REST API**: Exposed endpoints to facilitate interaction with the application.
- **OpenAPI Documentation**: Comprehensive documentation of the API is available at [Swagger UI](http://localhost:8080/car-dealership/swagger-ui/index.html)
- **External API Consumption (CEPiK)**: Integrated with the CEPiK API to retrieve random vehicles.
- **Spring MVC**: Used as an entry point to basic knowledge of HTML and CSS. Treat MVC as the first level to grow and learn new technologies like REST API and Angular.
- **Spring Security**: Implemented secure authentication and authorization mechanisms to ensure controlled access to sensitive information.
- **Spring Boot**: Core framework for rapid application development and management.
- **Hibernate and Spring Data JPA**: Integrated for seamless interaction with the PostgreSQL database, ensuring efficient data persistence and management.
- **Flyway**: Employed for database schema management and version control to maintain consistency across environments.
- **MapStruct**: Used for mapping between Java bean types, reducing boilerplate code and ensuring type-safety.
- **HTML, CSS, and Thymeleaf**: Incorporated for dynamic and responsive front-end development.
- **Unit and Integration Tests**: Demonstrated examples to ensure reliability and functionality.

## User Portals🧑‍🔧👩‍💻👩‍🔧👨‍💻

The application is built of two parts: Salesman Portal and Mechanic Portal.

### Salesman Portal

In the Salesman Portal, users can:
- View a list of available cars for sale
- Access information on available salesmen and mechanics
- Purchase cars
- Request car servicing
- Check service history

To access the Salesman Portal, use the provided login credentials:

- Username: `jack_salesman`
- Password: `test`
  
### Mechanic Portal

In the Mechanic Portal, users can:
- See available services and parts
- Fill out forms for current service requests they are working on
- Complete service requests or leave them for further work

To access the Mechanic Portal, use the provided login credentials:

- Username: `robert_mechanic`
- Password: `test`

## Database Design📊
You can find the initial design of the database in the provided **ERD** (Entity-Relationship Diagram) file.

## API Overview🛠️

This API enables users to carry out a variety of operations related to car services, purchases, mechanic tasks, and accessing vehicle data. Moreover, the application is integrated with another API called CEPiK, allowing users to retrieve random vehicles from it.

## Security Considerations🔒

The implementation of security in this project is at a very basic level and is not intended to be used in a production environment. The current security setup uses session-based authentication with cookies, which is stateful, while RESTful applications are inherently stateless. This implementation serves as a conceptual introduction to Spring Security and demonstrates basic ideas of authentication and authorization.

I am ready to further develop this skill and gain more knowledge in professional settings to implement more secure and scalable security solutions.

## Purpose of the Project📝
The Car Dealership Management System project aims to provide a comprehensive solution for managing various operations within a car dealership. By offering functionalities such as vehicle purchasing, service requests, service history tracking, and specifying services and spare parts used during servicing, the project seeks to streamline dealership operations, enhance efficiency, and improve overall management

## Why This Project is Useful✨

This project is useful for several reasons:

1. **Efficiency**: It helps automate and streamline manual processes involved in managing a car dealership, saving time and effort for dealership staff.
  
2. **Accuracy**: By digitizing operations, the system reduces the likelihood of errors that can occur with manual data entry and management.
  
3. **Customer Satisfaction**: The system facilitates better customer service by providing quick access to vehicle information, service history, and seamless processing of purchases and service requests.
  
4. **Comprehensive Service Management**: It allows detailed tracking of services and spare parts used, ensuring better service management and transparency.

5. **Security**: With built-in authentication and authorization mechanisms, the system ensures that sensitive dealership information is accessed only by authorized personnel, enhancing data security.
   
6. **Integration with External Systems**: The integration with the CEPiK API adds value by enabling access to additional vehicle data, enhancing the overall functionality of the system.

## Getting Started👌

To get started with the Car Dealership Management System, follow these steps:

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/Car-Dealership-Management-System.git

2. **Setup PostgreSQL Database**:
- Install PostgreSQL if you haven't already.
- Create a new database named `car_dealership`.
- Configure the database connection settings in `application.properties`.

3. **Configure Application Properties**:
- Ensure the database connection settings in application.properties are correct.
- Update the CEPiK API integration settings if necessary.

4. **Run Database Migrations**:
- Apply database migrations using Flyway to set up the initial schema:
```bash
./gradlew flywayMigrate
```

5. **Run the Application**:
- Build and run the project using your preferred IDE or with Gradle:
```bash
./gradlew bootRun
```

6. **Access the Application**:
- Once the application is running, access it via your web browser at ` http://localhost:8080`.

7. **API Documentation**:
- View the comprehensive API documentation at [Swagger UI](http://localhost:8080/car-dealership/swagger-ui/index.html)
  
8. **Explore External API Integration**:
- Utilize the integrated CEPiK API for additional vehicle data.

By following these steps, you'll be able to set up and explore the Car Dealership Management System, taking advantage of its various features and technologies.

## Work in Progress✍️

Greetings! I'm diligently working on improving the Car Dealership Management System to provide you with an even better experience. Here's what I'm currently focused on:

- **Unit Tests**:
  - Ensuring that every aspect of the system is thoroughly tested for reliability and stability.
  - Currently, our test coverage for _classes_ is at **51% (300/588)**, for _methods_ it's at **25% (1058/4166)**, and for _lines_ it's at **19% (2112/10756)**.
- **Integration Tests**:
  - Checking that all the different parts of the system work seamlessly together.
- **Exception Handling**:
  - Implementing measures to handle any unexpected issues with care and efficiency.

Stay tuned for more updates and shenanigans as we continue to level up our Car Dealership Management System!

## Contributing😎

- [Katarzyna Czubak] - [@kaskaCzubak](https://github.com/kaskaCzubak/kaskaCzubak.git)
  
Contributions are welcome! If you have any ideas, suggestions, or improvements, feel free to open an issue or create a pull request.
