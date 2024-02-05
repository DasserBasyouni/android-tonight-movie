# Today's Movie

<p align="center">
  <img src="/docs/images/CoverImage.jpeg">
</p>

## Project Overview

Today's Movie is a simple MVVM two-screen sample app that shows a list of movies and some details about each of them upon user click. 
It is using [The Movie Database (TMDB)](https://www.themoviedb.org) REST API with a basic error handling and retry mechanism.

## Requirements

###  The Movie Database (TMDB) API Key

Update the following key value with yours. You can find it in the `gradle.properties` file.

```
tmdb_api_key=<your TMDB API key>
```

## Technologies 

- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel).
- [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines)
- [Data Binding](https://developer.android.com/topic/libraries/data-binding/).
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Single App Activity](https://www.youtube.com/watch?v=2k8x8V77CrU)
- [Navigation](https://developer.android.com/guide/navigation) 
- [Kotlin DSL](https://developer.android.com/build/migrate-to-kotlin-dsl) - As it is currently the recommened build configuration language.
- [Gradle Version Catalogs](https://developer.android.com/build/migrate-to-catalogs) - For adding and maintaining dependencies and plugins in a scalable way.
- [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - For loading a paginated list of movies.
- [Retrofit 2](https://github.com/square/retrofit) - For fetching the data from [The Movie Database (TMDB)](https://www.themoviedb.org) REST API.
- [Glide](https://github.com/bumptech/glide) - For loading, caching and bluring images.

---

#### Checkout 👉 [Code Conventions](docs/CodeConventions.md). Let's consider it in our future commits, and update it accordingly.
