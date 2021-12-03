Our project uses a frontend that runs with React and a backend that runs with Java/Spring Boot.


To test the frontend without the connection to the database, use the console/command prompt to navigate to the my-app directory of the project and run the command “npm start” without the quotes. This requires downloading npm. The website should open in localhost:3000.
Front-end testing is done with Selenium. There is a folder found at carfinder/src/main/java/com/example/carfinder/frontendtesting with a file that shows how to use it. Keep in mind that you need to change the path of the Chrome driver to match the location that you have it at. When building the website from the console, comment out or remove the code from the file since Maven gets confused and doesn’t know how to find Selenium.
The entire website is located in index.js which is in carfinder/my-app/src.


Testing the backend requires using an IDE that can use Java. The REST APIs should be accessible at localhost:8080/.
Backend testing is done with Postman.
The backend is located in carfinder/src/main/java/com/example/carfinder.


When making changes, make sure to do it on a branch. Pull from the main branch if it’s been a while since you’ve done so to reduce the chances of a merge conflict. When you’re ready to merge to the main branch, make a pull request and alert others that you’re waiting for it to be reviewed.


To build the website, navigate to the carfinder directory in the console/command prompt and run “mvnw spring-boot:run” without quotes. If there is any problem, then it will let you know. Else, the website should be up and running at localhost:8080/. Check the user guide to see how the website works.