# FoodCravies Android App

A modern Android application built with Kotlin and Jetpack Compose, demonstrating the implementation of the Core Splashscreen API. This project serves as a starting point for a food-centric mobile app.

## 🌟 Features

*   **Splash Screen**: A custom animated splash screen that provides a smooth entry experience.
*   **User Authentication**:
    *   **External API Authorization**: Securely log in using external APIs.
    *   **Social Logins**: Convenient sign-in options with Google and Facebook.
    *   **OTP Verification**: Secure one-time password feature for phone number verification.
*   **100% Kotlin & Jetpack Compose**: Built entirely with modern, declarative UI toolkit for native Android development.
*   **Modern Architecture**: Follows a single-activity architecture.
*   **Gradle Version Catalogs**: Manages dependencies efficiently and centrally using `libs.versions.toml`.
*   **Material 3**: Utilizes the latest Material Design components and themes.

## 📸 Screenshots

*(Add a GIF or screenshot of the splash screen and the main screen here)*

![Splash Screen Demo](link_to_your_splash_screen.gif)

## 🛠️ Technologies Used

*   **Kotlin**: Official programming language for Android development.
*   **Jetpack Compose**: Android's modern toolkit for building native UI.
*   **Android Core Splashscreen API**: For implementing backward-compatible splash screens.
*   **Material 3**: The latest version of Google's design system.
*   **Gradle Kotlin DSL**: For writing build scripts in Kotlin.

## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

*   Android Studio Iguana | 2023.2.1 or newer
*   Android SDK API Level 25 or higher

### Installation

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/your_username/FoodCravies.git
    ```
2.  **Open in Android Studio:**
    *   Open Android Studio.
    *   Select `File > Open` and navigate to the cloned project directory.
3.  **Build the project:**
    *   Let Android Studio sync the Gradle files.
    *   Click the `Run 'app'` button or use the shortcut `Shift + F10`.

## 📂 Project Structure

The project follows a standard Android app structure:

```
FoodCravies/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/jtautomation02/foodcravies/
│   │   │   │   └── MainActivity.kt      # Main entry point
│   │   │   ├── res/
│   │   │   │   ├── drawable/            # Splash screen icons
│   │   │   │   ├── values/
│   │   │   │   │   ├── themes.xml       # App and splash screen themes
│   │   │   │   └── AndroidManifest.xml  # App manifest
│   │   └── build.gradle.kts             # App-level build script
│
└── gradle/
    └── libs.versions.toml               # Dependency version catalog
```

## 📈 Future Scope

This boilerplate project can be extended with the following features:
- [ ] Fetching restaurant data from a REST API
- [ ] Displaying a list of nearby restaurants
- [ ] Search and filter functionality
- [ ] Restaurant detail view with menus and reviews

## 📄 License

Distributed under the MIT License. See `LICENSE.txt` for more information.

---
*This README was generated with assistance from an AI tool within Android Studio.*
