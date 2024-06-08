# Smart-Contact-Manager

## Overview
Smart-Contact-Manager is a learning project aimed at creating an application that stores contacts efficiently and provides a smarter way of handling contacts with full authentication and authorization. This project is built using Spring Boot and Thymeleaf with an MVC architecture. For authentication and authorization, Spring Security is used.

## Features
- Store and manage contacts efficiently
- Full authentication and authorization
- CRUD operations on contacts
- User-friendly front-end

## Technologies Used
- **Back-end**: Spring Boot
- **Front-end**: HTML5, CSS3, Bootstrap, Thymeleaf
- **Database**: MYSQL Workbench
- **Authentication and Authorization**: Spring Security
- **ORM**: Spring Data JPA

## Getting Started

### Prerequisites
- Java Development Kit (JDK)
- An IDE (IntelliJ IDEA, Eclipse, etc.)

### Running the Application
1. Clone the repository:
    ```sh
    git clone https://github.com/Ram-Gaire/Smart-Contact-Manager.git
    ```
2. Navigate to the project directory:
    ```sh
    cd smart-contact-manager
    ```
3. Open the project in your IDE.

4. Configure the MySQL database:
   - Create a database named `smart_contact_manager` in MySQL Workbench.
   - Update the `application.properties` file with your MySQL database configuration:
     ```properties
     #database configuration
     
     #user server.port number as per you are choice
     server.port=8282
     spring.datasource.url=jdbc:mysql://localhost:3306/smartcontact?serverTimezone=UTC
     spring.datasource.username =your_mysql_username
     spring.datasource.password=your_mysql_password
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
     
     #these properties used for create an automatic table with the help of the entity we are given.
     spring.jpa.hibernate.ddl-auto=update
     
      # Set the maximum file size for upload. -1 means no limit.
     spring.servlet.multipart.max-file-size=-1
     # Set the maximum request size for multipart/form-data requests. -1 means no limit.
     spring.servlet.multipart.max-request-size=-1
     ```
5.Run the application.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/documentation.html)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/index.html)
- [Thymeleaf Documentation](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)

