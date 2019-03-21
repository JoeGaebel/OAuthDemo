# OAuth Demo
This project shows an example of how to get the OAuth2 Code Flow working with Spring and Google.

## Quick start guide:
1. Create an app on https://console.developers.google.com/
     - Select `Credentials` on the side bar
     - Click `Create` button and select `OAuth Client ID`
     - Select `Web Application` 
     - In the `Authorized redirect URIs` add `http://localhost:8080/api/login/oauth2/code/google`
     <br>*Remember to hit enter in the textbox, otherwise it won't actually add*
     - Input the Client ID and Client Secret into the `application.yml` (or more securely add it as environment variables)
2. Create a google account for use in automated testing
 <br>*NOTE: Do not specify a phone number or backup email - these will be used as a second factor, thus limiting your ability
 to login in an automated fashion.* 
     - Specify the username and password in the `JourneyTest.js`
   
3. Start the backend and frontend with
    - `./gradlew applications:backend:bootRun`
    - `cd applications/frontend && yarn install && yarn serve`

4. Run `cd e2e && yarn install && yarn debug` to run automated testing


## Deploy Guide:
1. Login to CF
2. Specify a route in `manifest.yml`
3. Specify the `FRONTEND_URL`
4. Ship it ⛵
