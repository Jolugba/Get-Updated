# Get Updated Articles App 

## Overview
This repository contains the source code for an Android application developed in Kotlin using Jetpack Compose. The application fetches and displays a list of the most popular articles from the New York Times API, allowing users to see more details by tapping on any article in the list. This is a master/detail-style application that adheres to the best coding practices of today.

## High Level Design
- **Architecture**: The app uses the Model-View-ViewModel (MVVM) architecture to ensure separation of concerns and enhance testability.
- **API**: Utilizes the NY Times Most Popular Articles API to fetch articles based on different periods (1, 7, 30 days).

## Software Development Practices/Tools used 
- **Object-Oriented Programming**: The codebase follows OOP principles for modularity and reusability.
- **Code Quality**: Emphasis on clean, maintainable, and performant code using Kotlin.
- **Compose**
- **MVVM**

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Kotlin plugin installed in Android Studio

### Setup Instructions
1. Clone the repository using:
   ```bash
   git clone <repository-url>
   ```
2. Open the project in Android Studio.
3. Replace `sample-key` in the API URL with your NY Times API key in `src/main/java/com/example/nytimes/util/Constants.kt`.
4. Build the project using Android Studio or by running the following command in the terminal:
   ```bash
   ./gradlew build
   ```

## Running the Application
- Launch the app using Android Studio’s emulator or on a physical Android device.

## Running Tests and Generating Coverage Reports
- **Unit Tests**: Run unit tests using JUnit by executing:
  ```bash
  ./gradlew test
  ```
- **UI Tests**: Execute UI tests using Espresso by running:
  ```bash
  ./gradlew connectedAndroidTest
  ```
- **Code Coverage**: Generate coverage reports using:
  ```bash
  ./gradlew jacocoTestReport
  ```

### Automation Scripts
- **Build Script**: Use the Gradle script at `./gradlew assembleDebug` to build the app from the command line.
- **Lint Checks**: Run lint checks using `./gradlew lint`.
- **Automated Testing and Reporting**:
  - Execute `./gradlew test` to run unit tests.
  - Use `./gradlew jacocoTestReport` for code coverage reports.

## Contributing
Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments
- Thanks to the New York Times for providing the API used in this application.

