### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years


### Readme by Jimmy Gam
Hi, my name is Jimmy Gam and this is the challenge of AXA. I applied for Javascript Software Engineer in Open Engineering Technology.

Although, that is the case, I still wanted to challenge myself to learn Spring through this coding challenges and wanted to take this opportunity as not only to demonstrate my skills but also to learn something new.

Through this documentation, I want to mention what had been added to the project to enhance it into better ways, following the directories of the project.

- In config, I created Hazelcast configuration file to enable caching which will be discussed further later. Also, included SwaqgerConfiguration to customize based on the project structures.
- In Controllers
  - Api Response were customized with different status code & messages
  - logging was implemented
  - Basic Error checking : if employee exists or not? is considered
- Entities
  - dto to deserialize/serialize from repository layer
  - adding annotations for entity fields
- Exceptions
  - basic customized exceptions: EmployeeAlreadyExists and EmployeeNotExist
- Repositories
  - implemented with basic JPA
- Security
  - configured basic securities to avoid CORS error
- Services
  - EmployeeService to represent basic JPA operations
  - EmployeeServiceImpl to demonstrate actual service business logic. In here, I implemented cache with spring built in cache mechanisms.
- Tests
  - Implemented unit testings of services
  - Idealistically, there should be integration testings and e2e testings but this is single dependency service, I only demostrated unit testings of the service.

#### What could have been better?
There are several things that can be done better depending on the system architecture.

1. Containerization - using Docker/K8s, containerizing the application should make the service ready for scalabilities.
2. Integration Testing/ Unit Testing - setting up those tests to make the service less falut tolerant. Also, setting up these different testing methods require which cloud services to be used.
3. Spring Security - given authentication/permissions for different users using spring security could be another thing

#### Java Experience?
I have less than a year experience of Java language.

