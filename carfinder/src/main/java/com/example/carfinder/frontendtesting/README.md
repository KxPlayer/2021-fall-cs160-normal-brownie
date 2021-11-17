I couldn't get Selenium to be implemented with the tests so what you have to do to run the tests is: 

1. Download the Chrome web driver for Selenium (and Chrome)
2. Take all of the code out of FrontEndTesting.java
3. Build the connected website and database by entering "mvnw spring-boot:run" while in the carfinder directory (the directory before this one).
4. Once the server is active, put the code back into FrontEndTesting.java
5. Write the path to your Chrome web driver in FrontEndTesting.java (it should be marked with a comment)
6. Run the java file.
7. It should open up Chrome and automatically start running the tests.
8. If any message appears in the console, then an error has occurred (I couldn't get assert to work properly)


The file is testing 3 main functions:

Navigation from the main page to the quiz buttons, signing up, and logging in.

testQuizResponse runs through a set of button inputs to get a single entry that matches the inputs.
It expects only one result from the inputs used.

testSignUp tries to send POST requests to the backend but the inputs get checked by the website before being sent.
The username field must have something in it and the password length needs to be at least 8 characters.
The test will try different combinations of username and password lengths.
If successful, the page changes to the homepage.

testSignUpWithDuplicateName sees how the database handles repeat names. The server doesn't like it, but nothing explodes on the front end which is good.

testSignUpWithNewName sees how the database reacts after being sent a duplicate name. The userID gets incremented from duplicate calls which makes a hole,
but signing up itself has no issues.

testLogIn tries to log in with nothing, no username with a password, a valid username with the wrong password, and finally a valid username and password.
Successful attempts will lead it to the start of the quiz. After this, the test will close.
