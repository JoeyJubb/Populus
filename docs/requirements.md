# The Challenge

For this challenge build an app that connects to the
StackExchange API and displays users as described below

## Description

The following wireframes describe the screens for the app.  
The ascii-drawings are for reference only.  
Just make sure that the required data is displayed.

### Main Screen

Display an input field and Button to search for users by name.  
Display up to 20 users alphabetically and show their reputation and username.  
When tapped, open a new `Activity` to display more information about the
user.

```
+--------------------+
|  AppName           |
|--------------------+
| __________  SEARCH | - input and button  
| ------------------ |
| 123  Username1     |
| ------------------ |
| 390  Username2     | -----*tap* -----> user details screen
| ------------------ |
|   0  Username3     |
| ------------------ |
| 275  Username4     |
| ------------------ |
| 122  Username5     |
+--------------------+
```

### User Details screen

Displays additional information about the user.

```
+--------------------+
| < User             | - up button and page title
|--------------------+
|                    |
|   +-----------+    |
|   |           |    |
|   |   Avatar  |    |
|   |           |    |
|   +-----------+    |
|   User Name        |
|   Reputation       |
|   Badges           |
|                    |
|   Location         |
|   Age              |
|   Creation Date    |
|                    |
+--------------------+
```

## Requirements and Constraints

  * Use a git repository to track your progress
  * Minimum Android API 19
  * The project must compile by executing gradle with `lint test assembleDebug`
  * Use RxJava(2) for handling async network calls
  * Use Retrofit2 for http requests
  * Use Kotlin instead of Java
  * Bonus points if you write any form of documentation (wiki, request for comments, readme, etc.)
  * Bonus points if you write any UI tests

  Of course you are allowed to use any resources available to you!

## Deliverables

Please provide a zip file containing your project files and git folder or
a link to your public repository.

## References

Retrofit2 - http://square.github.io/retrofit/  
RxJava - https://github.com/ReactiveX/RxJava  
StackExchange API - https://api.stackexchange.com/docs  
