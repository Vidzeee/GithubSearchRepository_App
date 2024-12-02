<<<<<<< HEAD
# GithubSearchRepository_App
=======
# GitHub Repository Search App

## Description
This mobile application allows users to search GitHub repositories through the GitHub API, view the details of selected repositories, and explore contributors and their associated repositories. It uses modern Android development practices such as MVVM architecture, Jetpack libraries, and the Repository pattern. The app also supports offline data storage, pagination, and displays detailed repository information.

## Features
1. **Search for Repositories**: 
   - A search bar allows users to search repositories by name or topic using the GitHub API.
   - A list of repositories is displayed in a recycler view (with card view layout) showing essential details like name, description, and owner.

2. **Repository Details**:
   - When a repository is selected, the app displays detailed information, including:
     - Name
     - Description
     - Project Link (opens in a WebView)
     - Contributors List
     - Image (if available)

3. **Offline Storage**:
   - The first 15 repositories displayed in the search results are saved locally using Room database.
   - Offline data is available even when the network is not accessible.

4. **Pagination**:
   - The app fetches a maximum of 10 items per page from the GitHub API. Pagination ensures that data is loaded in chunks, improving performance and user experience.

5. **Contributor Details**:
   - Clicking on a contributor's name will show a list of repositories associated with that contributor.

## Architecture
- **MVVM (Model-View-ViewModel)**: The app follows the MVVM pattern, separating the UI logic (View), business logic (ViewModel), and data (Model).
- **Jetpack Libraries**:
  - **Room** for offline data storage.
  - **LiveData** or **StateFlow** for managing UI state.
  - **Coroutines** for handling asynchronous network requests.
- **Repository Pattern**: A centralized data repository that abstracts the data sources (GitHub API and Room database).

## Libraries and Tools
- Jetpack Compose (or XML-based UI with RecyclerView)
- Retrofit for network calls to GitHub API
- Room for offline storage
- Coroutines for asynchronous operations
- WebView for displaying project links
- Paging for implementing pagination

## Screens

### Home Screen
- **Search Bar**: To search for repositories from the GitHub API.
- **RecyclerView**: Displays a list of repositories in a card view format, including repository name, description, and owner.
- **Pagination**: Only 10 repositories are fetched at a time from the GitHub API. Users can scroll to load more.
- **Offline Storage**: The first 15 items are cached in the Room database for offline viewing.
- **Click on Item**: Tapping on a repository item navigates to the Repo Details screen.

### Repo Details Screen
- **Repository Information**:
  - Name, description, project link, and owner image are displayed.
  - Clicking on the **project link** opens the repository’s web page in a WebView.
- **Contributors List**: Displays the contributors to the selected repository.

### Contributor Screen
- **Contributor Info**: Displays the name and profile image of the contributor.
- **List of Repositories**: Shows all repositories associated with the selected contributor.

## Data Model
- **RepositoryEntity**: Represents the repository data fetched from the GitHub API.
- **Repository**: A domain model used to display information in the app.
- **Contributor**: Represents contributors to a repository.
- **Room Database**: Stores the first 15 repositories locally.

## Dependencies
- **Retrofit**: For making network requests to the GitHub API.
- **Room**: For offline data storage.
- **Paging**: For implementing pagination to fetch data in chunks.
- **Glide** or **Coil**: For loading images (e.g., contributor's avatar).
- **Coroutines**: For managing background threads and network calls.

## Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/github-repo-search-app.git
cd github-repo-search-app
```

### 2. Open the project in Android Studio
- Open **Android Studio** and select "Open an existing project."
- Navigate to the cloned project folder and open it.

### 3. Add necessary API keys or credentials
- Ensure you have a valid GitHub API token if required (optional for public APIs).

### 4. Build and run the app
- Click on **Run** in Android Studio to build and run the app on an emulator or connected device.

## Code Structure
- **`ui/`**: Contains the Composables or Activities and Fragments for the Home and Repo Details screens.
- **`data/`**: Includes the Retrofit service, Room database entities, and data sources.
- **`repository/`**: Contains the Repository class that abstracts network and local database operations.
- **`viewmodel/`**: Contains ViewModel classes that manage the UI state and communicate with the Repository.
- **`network/`**: Contains Retrofit interface for network calls to GitHub API.

## Known Issues
- Pagination might load slightly slower on poor network connections.
- Offline data is currently limited to the first 15 repositories.

## Future Improvements
- Add better error handling for network issues.
- Implement a more complex offline data caching mechanism to store more than just the first 15 repositories.
- Enhance UI/UX with animations or more modern designs.

---
