# Game Night Guru

Welcome to the **Game Night Guru** workshop! In this session, you'll learn how to extend an existing Android application by adding **App Functions**, allowing users to interact with the app via voice, system shortcuts, and AI agents.

This demo app serves as the starting point for the "AI, Meet App: Teaching Android Apps to 'Talk' to Gemini via App Functions" workshop.

## Screenshots

| Game List                               | Search                                 |
|-----------------------------------------|----------------------------------------|
| ![Game List](screenshots/game_list.png) | ![Search](screenshots/game_search.png) |

## What is Game Night Guru?

**Game Night Guru** is a board game collection manager and play tracker. It helps hobbyists keep track of the games they own and log their gaming sessions.

### Core Features

*   **Game Collection Management:** Browse a comprehensive list of board games in your library.
*   **Detailed Insights:** View specific details for each game, including player counts, recommended ages, and average playtime.
*   **Play Tracking:** Log your gaming sessions, recording how many people played, how long it took, and how you'd rate the experience.

---

## App Walkthrough

### 1. The Game List
The main screen of the app displays your board game collection. Each entry provides a quick glance at the game's title and its player count range.

*   **Navigation:** Tapping on any game in the list will take you to its details.

### 2. Game Details
The detail screen provides a deeper dive into a specific game. Here you can see:
*   **Metadata:** Playing time, player counts, and age recommendations.
*   **Play History:** A history of your logged sessions for this specific game.
*   **Actions:** A button to log a new play session.

### 3. Logging a Play
From the Game Detail screen, you can open the **Add Play** dialog. This allows you to enter:
*   The number of players for that specific session.
*   The actual duration of the game.
*   A rating (1-10) for the session.

---

## How to add or replace games

The app loads games from a CSV file bundled in the assets.

1.  **Replace existing games**: Replace the file at `app/src/main/assets/collection.csv` with your own CSV file.
    - Ensure the column headers match the existing ones.
    - Specifically, `objectid` (Primary Key), `objectname`, `numplays`, and `rating` are used by the app.
2.  **Add more games**: You can add more rows to `collection.csv`.
    - Duplicates (based on `objectid`) will be replaced in the database.
3.  **Reset data**: Since the app only imports the CSV if the database is empty, you may need to clear the app data or uninstall/reinstall the app to see changes from a new CSV.

## Technical Stack

The app is built using modern Android development practices:
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose for a fully declarative UI.
*   **Navigation:** Jetpack Navigation 3.
*   **Database:** Room for local persistence of games and play records.
*   **Dependency Injection:** Hilt for managing app components.

## Project Structure

- `data/database`: Room entity, DAO, and Database definition.
- `data/repository`: Data source abstraction.
- `data/importer`: Logic for parsing the CSV file.
- `ui/list`: Screen and ViewModel for displaying the game list.
- `ui/detail`: Screen and ViewModel for displaying the game detail & add play dialog.
- `ui/components`: Reusable UI components.
- `di`: Hilt modules for dependency injection.

## Data Attribution

The game collection data used in this app is sourced from [BoardGameGeek](https://boardgamegeek.com).

- **Data Source**: The sample `collection.csv` is based on the collection of user [katie5](https://boardgamegeek.com/collection/user/katie5) on BoardGameGeek.
- **API**: Data was retrieved using the [BoardGameGeek XML API2](https://boardgamegeek.com/wiki/page/BGG_XML_API2).
- **Disclaimer**: This app is not affiliated with, maintained, authorized, or sponsored by BoardGameGeek. All product and company names are the registered trademarks of their original owners. The use of any trade name or trademark is for identification and reference purposes only and does not imply any association with the trademark holder of their product brand.
