### Simple GitHub User Browser

This is a simple Android app that lets you search for GitHub users and view their profiles. It's built using modern Android development practices, including Jetpack Compose for the UI, Retrofit for networking, and a Room database for local caching.

---

### Key Features ✨

* **Search for GitHub users** by their username.
* **View user details** like their avatar, bio, and repository count.

---

### Tech Stack and Architecture 🏗️

The project is built using a **modular architecture**. This helps keep the codebase clean, organized, and easier to manage. Each module has a specific job:

* **`:app`**: The main application module. It brings everything together and contains the UI (built with Jetpack Compose).
* **`:data`**: Handles all the data logic. This module communicates with the GitHub API (using Retrofit) and the local database (Room).
* **`:domain`**: The core business logic. This module defines the app's rules and data models, making them reusable across different parts of the app.

---

### Key Libraries and Tools 🛠️

This app uses several popular libraries to make development easier and more efficient:

* **Retrofit & OkHttp**: These work together to handle all the network requests to the GitHub API.
* **Moshi**: This library takes the data we get from the API and turns it into readable objects for our app.
* **Room**: A powerful library that helps us create a local database to save user data, so you can see it even without an internet connection.
* **Dagger Hilt**: A tool that helps manage and provide all the dependencies needed for the app, like our API service and database.

---

### Getting Started

To run this app, you'll need a GitHub Personal Access Token (PAT). This is required to make requests to the GitHub API and avoid rate limiting. Don't worry, it's a quick process!

#### **How to Set Up Your GitHub API Key** 🔑

1.  **Generate a new token:** Go to your GitHub account settings: **Settings > Developer settings > Personal access tokens > Tokens (classic)**. Click the **"Generate new token"** button.
2.  **Add scopes:** Give the token a memorable name (like "My App Token") and grant it the `read:user` scope. That's all you need for this project.
3.  **Copy your token:** After you click **"Generate token"**, GitHub will show you the new token. **Copy it immediately**—you won't be able to see it again!
4.  **Create a `local.properties` file:** In the root directory of this project, create a new file named **`local.properties`**.
5.  **Add your token to the file:** Add the following line to the `local.properties` file, replacing the placeholder with your actual token:
    ```
    github.token="YOUR_GITHUB_PERSONAL_ACCESS_TOKEN"
    ```
6.  **Sync Gradle:** Finally, sync the project with your Gradle files. You're all set!
