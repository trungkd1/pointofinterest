![Alt text](screenshots/playstore.jpg?raw=true "Banner")

## Introduce Application (Points of interest)
==================


## English Introduce


This application allows users to easily receive and organize shared data, such as links, texts, and images via website links.
Create your favorites effortlessly with a minimalistic interface:
  - To utilize a wizard to automatically analyze link content
  - To make your own custom categories
The application also allows for easy searching and filtering of stored information, enabling you to quickly find the information you need.

Furthermore, this application utilizes the latest technologies such as Dagger Hilt, Jetpack Compose, and is coded in the Kotlin language.

## Architecture

This application is divided into 3 layers: Data, Domain and UI.
  - Data layer: To make databases and connect to servers, 
  - Domain layer: To interact with the UI layer and Data layer.
  - UI layer: Where the interface is contained and handles the display logic on the screen. The app uses the MVVM model to create the structure at the UI layer. UI is the View (UI), Model is used to transmit data between UI classes), and ViewModel is the part of handling logic on the UI screen).

### :data (Data layer)

 3rd party libraries and technologies used:
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) To use and manage dependencies injection
  - [Kotlin flows](https://developer.android.com/kotlin/flow) To load asynchronous data
  - [Room](https://developer.android.com/training/data-storage/room) This is a library used to manipulate databases
  - [Retrofit](https://square.github.io/retrofit/) Use to request HTTP and read the data form such as Json XML và ProtoBuf
  - [OkHttp](https://square.github.io/okhttp/) Use to hook up API server
  - [Jsoup](https://jsoup.org/) Convert String into an Object with the form of Document to access String's traits

 Features:
  - [Database](/data/src/main/java/com/trungkieu/data/database) Create and connect Database
  - [DI Modules](/data/src/main/java/com/trungkieu/data/di) Execute classes with module annotations to create instances
  - [Data level models](/data/src/main/java/com/trungkieu/data/features/poi/model) The models are used to transmit data in data layer
  - [DAO](/data/src/main/java/com/trungkieu/data/features/poi/dao) Write SQL statements
  - [Data sources](/data/src/main/java/com/trungkieu/data/features/poi/datasource) Call interfaces with DAO annotations to execute SQL statements
  - [Network calls](/data/src/main/java/com/trungkieu/data/features/poi/api) Connect network
  
### :domain (Domain Layer)

Features:
 - [Use cases](/domain/src/main/trungkieu/domain/features/poi/interactor)  Handle the logic of the system and serve as a bridge between Data layer and UI layer


### :app (UI layer)

3rd party libraries and technologies used:
  - [Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAiAoL6eBhA3EiwAXDom5uovlfrS1-2xp88b8zKsFzkiW36VKaFC01x9UM7zCvrIpCnRptZGJhoCq90QAvD_BwE&gclsrc=aw.ds) Use compose to create UI instead of being XML
  - [Android ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel?gclid=CjwKCAiAoL6eBhA3EiwAXDom5oKABL8-HMrHV2XjQTCwKqtV-iMS4fTKJwgFsJDnzSwuNmDy0vEHyxoCqwkQAvD_BwE&gclsrc=aw.ds0) Use to handle the logic of UI and connect with data layer
  - [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) Use to navigate screens
  - [Splash Screen Api](https://developer.android.com/develop/ui/views/launch/splash-screen) Product intro screen ahead of entering the main screen
  - [Coil for Compose](https://coil-kt.github.io/coil/compose/) Use to load image
  - [Flow layout](https://google.github.io/accompanist/flowlayout/) This is a layout manager of Android used to arrange views from left to right, top to bottom
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) Use to create and manage dependencies injection
  - [Kotlin flows](https://developer.android.com/kotlin/flow) Support asynchronous processings without crashing the application, and is used in conjunction with coroutines



![Alt text](screenshots/flow.png?raw=true "Banner")
