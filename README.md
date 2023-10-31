# TMDBCompose

This proof of concept project focuses on Android `Instrumentation Tests`, utilizing the `Robot Pattern` for test structure. The code is feature-modularized and built upon Clean Architecture and MVVM. Hilt handles dependency injection, while Kotlin Coroutines, Flow, StateFlow, and SharedFlow manage asynchronous tasks. Additionally, `Jetpack Compose` is incorporated for building the UI.

---

This project has been developed in Android Studio version:
-  `Giraffe | 2022.3.1 Patch 2`
-  `Build #AI-223.8836.35.2231.10811636`
-  `Java JDK 17`

To get started with this project, you'll need an API key from The Movie Database (TMDB). Find more details and instructions on how to obtain the key [here](https://developer.themoviedb.org/docs).

Once you've obtained your API key, run the project. Gradle will generate a file named `apiKey.properties` at the project root where you can insert your API key as follows:

`apiKey.properties`
```plaintext
  #Sat Oct 21 16:54:38 BRT 2023
  API_KEY=".....e5fc1.....f78fe.....1144a"
```

## Module Dependencies

- [Modularization](https://developer.android.com/topic/modularization)

<img src="images/graph.svg" width="70%"/>

## Screens
![Screenshots](images/screens.png)
