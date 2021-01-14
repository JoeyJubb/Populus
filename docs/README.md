# Populus

[Requirements](requirements.md)


## App Architecture
Populus implements [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), made up of 3 main modules:

 ### domain 
Business logic lives here.
Reflects the business requirements for the app by defining and implementing use cases.

Our requirements define two use cases:

 - User can search for profiles by typing in a query. The search will include up to 20 items and be ordered alphabetically. Each item should show the user's name and their reputation.
 - User can view a profile by submitting a user id. Profile information should include name, reputation, badge counts, location, age, creation date and a profile avatar.

These are implemented in the domain layer at a high level. The requirements are fulfilled here (including 20 items max, sort order and what data should be available.

Domain defines interfaces for repositories to fetch/store data, but does not implement them.

 ### data
Persistence logic lives here.
Implements the repositories defined by domain. 

Implementation details should not "leak" into the domain layer. This can be demonstrated in `UserRepositoryImpl` by solving the following problem:
 - `GetUserDetailUseCase` defines that we should be able to load the user details based on a `UserId`
 - All of the relevant information is already available when the search query is performed.
 - It wouldn't be good practice to make another network call for the detail screen when all the info was already given to us

The solution was to implement a cache which stores the search results in memory, which allows the repository to get the detail information without making a network call.


 ### presentation
Navigation and view logic live here.

Implements the views in the app. This is where `Activity` and `Fragment` classes live along with layouts etc.

I've implemented a single activity model. Navigation is defined in `nav_graph.xml`, which would serve as the "single source of truth" for how each screen is related to another in the app. As the app would grow, this navigation file might have sub-graphs extracted out into smaller files - but the `nav_graph.xml` file itself would remain as the top-level "start here" file.

Each fragment defines a screen in the app and uses MVVM patterns. MVVM stands for "Model, View, View Model"
 ##### Model
 Implemented by the domain layer as Use Casses
 
 ##### View Model 
The View Model has two strict roles:
 - Define what the view should be displaying
 - React to user input

This is achieved in this project by implementing  android `ViewModel` classes which expose `LiveData` that emit `ViewState` objects. The `ViewState` type defines 4 mutually exclusive states:
 1. Ready - the view is ready to accept user input
 2. Busy - the view is waiting for a response from the Model
 4. Error - something has gone wrong. Optionally defines an action for the user to take
 5. Alert - the user must respond before continuing

The "ready" state can be further sub-divded depending on what the screen needs to show. In the "list" example, the ready state can be one of 3 things:
1. Idle - the user hasn't searched for anything yet
2. Empty - the user performed a search, but there were no results
3. HasResults - the user performed a search and there were 1 or more results

The main point of this is that all these states are **mutually exclusive**. The view implementation doesn't have to consider all of the different possibilities available, it simple has to show that one state that the view model is telling it to show.

**All user input should be funneled through the View Model**. Whilst this might seem like extra boilerplate code at first, it's important to have the view model make all the choices. Not only does it make it very easy to alter behavior later (or add analytics code), it also makes actions unit testable - keeping the logic in unit tests and the fragments as dumb as possible. Instrumentation tests can then be simplified to "when button is pressed, tell the view model". 

##### View
The "View" portion of "MVVM" is represented by `Fragment` objects in this project. View objects have two jobs:
 1. Display the View Model's view state on the screen
 a. This also means not showing the other states (i.e. don't show "Busy" if  "Error" is the current 
 2. Interpret user input and deliver those events to the View Model

Views, and by extension `Fragments` are therefore as "dumb" as possible. There are no choices for the View objects to make other than how to implement what the screens look like. 

#### Main Screen Extra Notes

 - The view model additionally exposes "Navigation" objects. These represent "one off" events that the View should only consume once. 
 
#### User Details screen Extra Notes

 - Doesn't open a new activity. Android development has been drifting towards a "Single Activity Model" for a while now, so I felt it was more important to demonstrate an understanding of the Navigation component instead.
 - Demonstrates consideration for screen readers, implementation makes sure that the labels are read in the correct order