# News App

## Description
This is a fully functional Android application that fetches news articles from NewsAPI, displays them in a user-friendly manner, and allows users to save articles for offline access. The app follows Android best practices and is built entirely using Kotlin and Jetpack Compose.

## Features
- **API Integration**: Fetches the latest news articles using NewsAPI.
- **Article List**: Displays articles in a scrollable list with headlines, descriptions, and images.
- **Article Details**: Opens articles in an in-app WebView when clicked.
- **Save Articles**: Allows users to save articles for offline access.
- **Local Database**: Uses Room database to store saved articles.
- **Saved Articles Section**: Displays saved articles and allows users to delete them.
- **Modern UI/UX**: Built using Jetpack Compose for a smooth and intuitive user experience.
- **Offline Support**: Works seamlessly without an internet connection (Bonus Feature).
- **Extra Features**:
  - Dark mode support.
  - Article categorization.
  - Share button for articles.

## Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM
- **Networking**: Retrofit
- **Database**: Room
- **Dependency Injection**: koin
- **Image Loading**: Coil
- **Navigation**: Jetpack Navigation

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/jay-vaghasiya/EkaCareTask.git
   ```
2. Open the project in Android Studio.
3. Use already Added  NewsAPI key in the  file: LandingScreen
5. Sync the project and build it.
6. Run the app on an emulator or physical device.

## API Integration
- Fetches news data from [NewsAPI](https://newsapi.org/).
- Uses Retrofit to make network calls.
- Parses JSON responses using Kotlin Serialization.

## Architecture
The app follows the **MVVM (Model-View-ViewModel)** architecture:
- **Model**: Represents the data layer, including API calls and Room database interactions.
- **ViewModel**: Handles UI-related data and business logic.
- **View (Jetpack Compose UI)**: Observes data from the ViewModel and displays it to the user.

## Project Structure
```
app/
├── data/            # Data layer (API, Database, Repositories)
├── ui/              # UI layer (Jetpack Compose screens and components)
├── viewmodel/       # ViewModels
├── di/              # Dependency Injection setup (Koin)
├── utils/           # Utility classes and helpers
└── MainActivity.kt  # App entry point
```

## Branching Strategy
- `main`: Stable production-ready code.
---
Made with ❤️ by [Jay Vaghasiya](https://github.com/jay-vaghasiya)

