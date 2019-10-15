# Shift Weather

### About App

A simple android weather application to demonstrate the usage for MVVM design pattern using LiveData, RxJava, View-DataBinding, Retrofit, Moshi, Koin (Dependency Injection), based on Clean Architecture & Gradle Dependency Management (GDM) . The application supports caching using retrofits default caching mechanism.

The application uses the Weather API for Estonia and shows weather forecast with respect to 4 day forecast.
It presents weather information for day & night with respected to the date selected and also for multiple cities in Estonia.


### Components Used
-   [MVVM Architecture](https://developer.android.com/jetpack/arch/) - Robust, testable, and maintainable app with classes for managing your UI component lifecycle and handling data persistence.
- [Clean Architecture]() - Isolates UI, business logic and data sources' responsibilities creating a Testable system
-  [KOIN](https://github.com/InsertKoinIO/koin) - A pragmatic lightweight dependency injection framework for Kotlin
-    [AndroidX](https://developer.android.com/jetpack/androidx) - AndroidX is a major improvement to the original Android [Support Library]
-  [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle) - Create a UI that automatically responds to lifecycle events.
-  [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Build data objects that notify views when the underlying database changes.
-  [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.
-  [Test](https://developer.android.com/training/testing/) - An Android testing framework for unit and runtime UI tests.

## Screenshots

<img src="https://raw.githubusercontent.com/faiyyazs/ShiftWeather/master/screenshots/device-2019-10-15-125555.png" alt="drawing" width="200"/>
<img src="https://raw.githubusercontent.com/faiyyazs/ShiftWeather/master/screenshots/device-2019-10-15-125657.png" alt="drawing" width="200"/>
<img src="https://raw.githubusercontent.com/faiyyazs/ShiftWeather/master/screenshots/device-2019-10-15-125737.png" alt="drawing" width="200"/>


## Data Flow
Let’s start explaining Data Flow in Clean Architecture as follows,
> 1. UI calls method from ViewModel.
> 2. ViewModel executes Use case.
> 3. Use case combines data from Multiple Repositories.
> 4. Each Repository returns data from a Data Source (Cached or Remote). [ In current scope only Remote ]
> 5. Information flows back to the UI where we display the list of requested information.

## Clean Architecture 

In Clean Architecture adopted, relationship that exists between the different layers is very crucial.

Different layers & their boundaries are explained below,

**Presentation Layer** contains UI (Activities & Fragments)_ that are coordinated by ViewModels which execute one or more Use cases._ Presentation layer depends on Domain Layer.

**Domain Layer** It contains _Entities, Use cases & Repository Interfaces._ Use cases combine data from one or more Repository Interfaces.

**Data Layer** contains _Repository Implementations and one or more Data Sources._ Repositories are responsible to coordinate data from the different Data Sources. Data Layer depends on Domain Layer.
 
Clean Architecture helps Isolates UI, business logic and data sources' responsibilities creating a Testable system.

## Support

If you've found an error in this sample, please file an issue: [https://github.com/faiyyazs/ShiftWeather/issues](https://github.com/faiyyazs/ShiftWeather/issues)

Patches are encouraged, and may be submitted by forking this project and submitting a pull request through GitHub.



