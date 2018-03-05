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

Finally you should able to see **../viventor/build/libs/viventor-1.0.jar**
Now you can run this jar with next command:
##### java -jar viventor-1.0.jar

To check whether your application is up and running please type this url to your browser:
http://localhost:8080/auth/health_check

## Work with application
Before start working with application you should create system user with login and password by executing below URL:
http://localhost:8080/auth/createAppUser with POST method and put below JSON:
*{"userId":"", "userName":"your_login", "password":"your_password"}*. This information should store in application_user table (password should be crypted).

Next you should add detail information for above user (client) like name, surname etc.
You can use this url http://localhost:8080/bank/createClient with POST method and below JSON:
*{"clientId":"","clientName":"your_name","accounts":[], "applicationUserDTO":{"userId":"", "userName":"", "password":""}}*
Don't forget to use login/password from previous API to execute above service (I used HttpRequester application to test all services so had to put user credentials to Auth field).

After adding information for client you should be able to create new account with this URL and POST method:
http://localhost:8080/bank/createAccount and next JSON:
*{"accountId":"","accountNum":"","balance":5, "client":{}}*
Account automatically will be linked to current loggin user.

To put or get money from account please use two below URL's:</br>
http://localhost:8080/bank/deposit</br>
http://localhost:8080/bank/withdrawn

and this JSON:
*{"accountId":"","accountNum":"21c90106-d046","balance":7, "client":{}}*
where 21c90106-d046 - account number, 7 - how much money we would like to get/put from account.

To get detail information from client accounts please use next URL:
http://localhost:8080/bank/statements


## Running tests

Tests were created with help of Spock framework. 
So first you should run test under **AuthorizedControllerSpec** class. This tests will create application user.
Then you can run tests from **BankControllerSpec** class. Theses tests will create client, account, put some deposit to account and also check that user with wrong login/password should not be able to work with API.
