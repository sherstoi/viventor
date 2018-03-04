# viventor
## Project description.
Backend application which provide API to work with accounts (deposit, withdrawn operations) and clients.

## Prerequisites
Viventor application use PostgreSQL version 9, Java 8 and Gradle 3.1 so please make sure that these components install
on your PC.

## Running application
Please build application with gradle tool by executing below command from root directory:
##### gradle build -x test
Or you can run gradle build task from Intellij Idea.

Finally you should able to see #####../viventor/build/libs/viventor-1.0.jar
Now you can run this jar with next command:
##### java -jar viventor-1.0.jar
