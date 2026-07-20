# Game Night Guru

This is a simple demo app for the Game Night Guru workshop.

## Architecture

- **Jetpack Compose**: Modern UI toolkit.
- **Room Database**: Local data storage.
- **Hilt**: Dependency injection.
- **Repository Pattern**: Clean separation of concerns.

## How to add or replace games

The app loads games from a CSV file bundled in the assets.

1.  **Replace existing games**: Replace the file at `app/src/main/assets/collection.csv` with your own CSV file.
    - Ensure the column headers match the existing ones.
    - Specifically, `objectid` (Primary Key), `objectname`, `numplays`, and `rating` are used by the app.
2.  **Add more games**: You can add more rows to `collection.csv`.
    - Duplicates (based on `objectid`) will be replaced in the database.
3.  **Reset data**: Since the app only imports the CSV if the database is empty, you may need to clear the app data or uninstall/reinstall the app to see changes from a new CSV.

## Project Structure

- `data/database`: Room entity, DAO, and Database definition.
- `data/repository`: Data source abstraction.
- `data/importer`: Logic for parsing the CSV file.
- `ui/list`: Screen and ViewModel for displaying the game list.
- `ui/components`: Reusable UI components.
- `di`: Hilt modules for dependency injection.
