---
id: ai-meet-app
summary: Level up your development skills! In this hands-on workshop, we’ll take a pre-built board game app and supercharge it using Gemini. You’ll write the specific App Functions that allow Gemini to scan a user’s natural language prompt (e.g., “I have 4 friends, 2 hours, and I love strategy”) and instantly surface the perfect game from the app’s database. No loaded dice here—just pure, fast-paced AI integration.
categories: Android, App Functions, AI, Kotlin, Gemini
status: Published
authors: Katie Barnett
tags: tutorial, beginner
---

# AI, Meet App: Teaching Android Apps to Talk to Gemini via App Functions

## Download and Install Android Studio
Duration: 0

If you haven't yet, download and install Android Studio. It is best to have the latest stable version from the [Android Studio download page](https://developer.android.com/studio/?gclid=Cj0KCQiAjJOQBhCkARIsAEKMtO3zEhdK4_I0CEZic3UH4dl-9gVXuHFR9dCl3TOHKjmv3xWLU3UxfhYaApfAEALw_wcB&gclsrc=aw.ds&authuser=894489108).

For more detailed instructions, follow the [Download and install Android Studio codelab](https://developer.android.com/codelabs/basic-android-kotlin-compose-install-android-studio#0).

## Create an Android Emulator
Duration: 1

> **Note:** If you decide to use a physical device and connect it to your computer with a cable you can skip this step!

It can take a while for the emulator image to download, so while you are waiting, create a new emulator. 

> **Note:** For this codelab, you will need to use a System Image of **36.0** and above. 
> Usually I use a **Medium Phone** but any other one you have should be fine. 

For more detailed instructions, follow the [Run your first app on the Android Emulator codelab - Step 3](https://developer.android.com/codelabs/basic-android-kotlin-compose-emulator#2).


## Introducing Game Night Guru
Duration: 3

The demo app for this codelab is **Game Night Guru**, a boardgame collection manager and play tracker. It helps hobbyists keep track of the games they own and log their gaming sessions.  

[Download Source Code](https://github.com/KatieBarnett/GameNightGuru/archive/refs/heads/main.zip)

You can also clone it from github:
- Clone the the official App Functions repository using your terminal:
  ```bash
  git clone https://github.com/android/appfunctions.git
  ```
- Clone the official App Functions repository using your favourite source control application using:
  ```bash
  git@github.com:android/appfunctions.git
  ```


| Game Night Guru | Game List | Game Detail (Play Log) | Add Play Dialog |
| --- | --- | --- | --- |
| ![walkthrough_screenshot_1](assets/walkthrough_screenshot_1.png) | ![walkthrough_screenshot_2](assets/walkthrough_screenshot_2.png) | ![walkthrough_screenshot_3](assets/walkthrough_screenshot_3.png) | ![walkthrough_screenshot_4](assets/walkthrough_screenshot_4.png)|

> For demonstration purposes, a list of games has been added (exported from [Boardgame Geek](https://boardgamegeek.com/) ), if you want to replace them check out the [README](https://github.com/KatieBarnett/GameNightGuru/blob/main/README.md). 

### Core Features  
  
*   **Game Collection Management:** Browse a comprehensive list of board games in the library.  
*   **Detailed Insights:** View specific details for each game, including player counts, recommended ages, and average playtime.  
*   **Play Tracking:** Log gaming sessions, recording how many people played, how long it took, and how you'd rate the experience.  

### App Walkthrough  

#### 1. The Game List  
The main screen of the app displays the board game collection. Each entry provides a quick glance at the game's title and its player count range.  
*   **Navigation:** Tapping on any game in the list will take you to its details.  
  
#### 2. Game Details  
The detail screen provides a deeper dive into a specific game. Here you can see:  
*   **Metadata:** Playing time, player counts, and age recommendations.  
*   **Play History:** A history of your logged sessions for this specific game.  
*   **Actions:** A button to log a new play session.  
  
#### 3. Logging a Play  
From the Game Detail screen, you can open the **Add Play** dialog. This allows you to enter:  
*   The number of players for that specific session.  
*   The actual duration of the game.  
*   A rating (1-10) for the session.  
  
### Technical Stack  
  
The app is built using modern Android development practices:  
*   **Language:** Kotlin  
*   **UI Framework:** Jetpack Compose for a fully declarative UI.  
*   **Navigation:** Jetpack Navigation 3.  
*   **Database:** Room for local persistence of games and play records.  
*   **Dependency Injection:** Hilt for managing app components.  
  
### Project Structure  
  
- `data/database`: Room entity, DAO, and Database definition.  
- `data/repository`: Data source abstraction.  
- `data/importer`: Logic for parsing the CSV file.  
- `ui/list`: Screen and ViewModel for displaying the game list.  
- `ui/detail`: Screen and ViewModel for displaying the game detail (play log) & add play dialog.  
- `ui/components`: Reusable UI components.  
- `di`: Hilt modules for dependency injection.

## Preparing for App Functions [TODO]
Duration: 5

## Writing our first App Function [TODO]
Duration: 10

Content for step 2...

## Create an App Function using a Gemini Skill [TODO]
Duration: 10

## Testing Your App Functions
Duration: 2

Now that we have some App Functions for Game Night Guru, it’s time to see them in action. We are going to test the plumbing to make sure the Android system sees our functions using ADB from the command line, and then use a testing agent to watch Gemini interact with our app.

Make sure you have created an emulator (see step **Create an Android Emulator**) or connect a physical Android device via a cable and installed Game Night Guru onto the device. 

### Enable Developer Options
Before we can use ADB, we need to enable Developer Options on your physical device. 

1. Open your device **Settings**.
2. Scroll down and tap **About phone**. 
   *(Note: On Samsung devices, you will then need to tap **Software information**).*
3. Find the **Build number** and tap it **7 times** quickly.
4. You will see a small message saying "You are now a developer!".
5. Go back to the main Settings menu, and you will now see **Developer options** listed at the bottom.

[![Watch how to enable Developer Options](https://img.youtube.com/vi/_tUGcSy3uBk/0.jpg)](https://youtu.be/_tUGcSy3uBk)

## Install ADB (Android Debug Bridge)
Duration: 2

[ADB](https://developer.android.com/tools/adb) is the command-line tool that lets your computer talk to your Android device or emulator. Because we are using Android Studio for this codelab, you likely already have it installed! We just need to make sure you can run it from your terminal.

> **Note:** You can either use any terminal application on your device, or you can use the terminal within Android Studio. If you can't find it, you can open it via **Tools > Tool Windows > Terminal**

To check that you have it installed, open your terminal and run:

```bash
adb
```

You should see the version and install location:

```bash 
you@YourComputer GameNightGuru % adb
Android Debug Bridge version 1.0.41
Version 36.0.0-13206524
Installed as /opt/homebrew/bin/adb
Running on Darwin 25.5.0 (arm64)
```

If you don't see this, you can follow these install instructions:

**Using Android Studio**

Go to **Tools > SDK Manager > SDK Tools** and check **Android SDK Platform-Tools** and press `OK`

**For Mac Users:**

The easiest way is using Homebrew. Open your terminal and run:

```bash
brew install android-platform-tools
```

**For Windows Users:**

The fastest way is using Winget. Open PowerShell or Command Prompt and run:


```bash
winget install Google.PlatformTools
```

_(Alternatively, download the [SDK Platform-Tools zip file](https://developer.android.com/tools/releases/platform-tools), extract it, and [add the folder to your Windows system Environment Variables](https://www.youtube.com/watch?v=UVlNBv3Yhv8))._

**Verify it works:**

Try `adb` now!

### Check your devices are connected

Plug in your device or start your emulator and run:

```bash
adb devices
```

If you see a device listed, you are ready to go!

![adb_devices](assets/adb_devices.png)

> **Warning:** If you have two devices listed, then you will run into some issues later where you have to specify which device to run the commands on. I recommend disconnecting one so you only have one device listed - it is much easier!

## Test App Functions Integration via ADB
Duration: 2

Before we bring AI into the mix, let’s verify that the Android OS has successfully registered the App Functions we just built.

Run this command in your terminal to list all App Functions currently registered on your device:

```bash
adb shell cmd app_function list-app-functions
```

Because that list can get long, you can filter it specifically for Game Night Guru using `grep`:

```bash
adb shell cmd app_function list-app-functions | grep -A 20 dev.katiebarnett.gamenightguru
```

> **Note:** Make sure to replace the package name if you have updated it to your own name

If you see the functions we wrote (like `queryGames`), everything is working and Game Night Guru is exposing its capabilities to the OS!
![app_functions_json](assets/app_functions_json.png)

### Something fun to explore

If you don't include the grep command (delete the  `| grep -A 20 dev.katiebarnett.gamenightguru` part) , what other app functions do you see on this device? Perhaps try it when you have your actual phone connected. Are any of your favourite apps already using App Functions?

## Download & Install the Testing Agent
Duration: 5

Google provides a privileged Testing Agent app that acts as a stand-in for Gemini. This allows us to test end-to-end workflows without needing a full production system.

1. To use the testing agent, you need to enable Developer Options on your emulator or device

2. To get the Testing Agent app you can do one of the following:

[Download Source Code](https://github.com/android/appfunctions/archive/refs/heads/main.zip)

- Clone the the official App Functions repository using your terminal:
  ```bash
  git clone https://github.com/android/appfunctions.git
  ```
- Clone the official App Functions repository using your favourite source control application using:
  ```bash
  git@github.com:android/appfunctions.git
  ```

2. Open your terminal (or use the Android Studio Terminal) and change to the agent directory:
```bash
cd appfunctions/agent
```
    
3. Build and install the agent to your connected device using the provided script:
```bash
./run_privileged.sh --build
```

**⚠️ Important Note for Windows Users:**

Because this is a `.sh` shell script, it will not run in standard Command Prompt or PowerShell. You must run this command using **Git Bash** (which is installed automatically if you have Git for Windows) or WSL (Windows Subsystem for Linux).

![run_privlaged](assets/run_privlaged.gif)

## Using the Testing Agent 
Duration: 2

The first thing we can do is check that the agent sees the App Functions. You can enter parameters and get back the exact results the function returns (no AI involvement yet) and execute functions that update the data.

![agent_debug](assets/agent_debug.gif)

Now we know it can see our app and the available App Functions, let's hook it up to Gemini!

## Get a Gemini API Key to Test with Gemini
Duration: 2

To give our Testing Agent a brain, we need a Gemini API key. We will be using the free tier so no need to add a credit card or sign up to a pro plan.

1. Go to [Google AI Studio](https://aistudio.google.com/).
2. Sign in with your Google account.
3. Click the **Get API key** button in the left-hand menu.
 
 ![ai_studio_annotated_1](assets/ai_studio_annotated_1.png)
 
4. Click **Create API key**

![ai_studio_annotated_2](assets/ai_studio_annotated_2.png)

5. Click **Create project** in the **Choose an imported project** dropdown

![ai_studio_annotated_3](assets/ai_studio_annotated_3.png)

6. Enter the project name and click **Create project**

![ai_studio_annotated_4](assets/ai_studio_annotated_4.png)

7. Click **Create key**

![ai_studio_annotated_5](assets/ai_studio_annotated_5.png)

8. Copy the generated key using the copy icon or the **Copy key** button

![ai_studio_annotated_6](assets/ai_studio_annotated_6.png)  

9. If you missed copying it, you can now find it in the list of keys in [AI Studio](https://aistudio.google.com/app/apikey)

![ai_studio_annotated_7](assets/ai_studio_annotated_7.png)

## Test it Live!
Duration: 5

This is where the magic happens. We are going to ask the agent a natural language question and watch it trigger our app's specific function.

1. Open the **AppFunctions Testing Agent** app on your Android device/emulator.
    
2. Navigate to the **Settings** screen. Paste your Gemini API key (copied from the previous step) into the settings/configuration screen of the agent.

![api_key](assets/api_key.gif)
    
3. Navigate to the **Agent Demo**, type a natural language prompt that targets the function we wrote. For example:
    
     _"I have 4 friends coming over for 2 hours and we love strategy games. What should we play?"_
        
3. Press **send**.
    
![agent_demo_edited](assets/agent_demo_edited.gif)
*Some sequences may be shortened!*

You will see the agent process the prompt (note - it may take a bit longer than my video!), discover our `queryGames` App Function, map "4 friends" and "2 hours" to our function's parameters, and instantly surface the correct game from our database!

If we think back to our KDoc prompt:

```kotlin
/**  
 * Query what game to play based on duration, number of players, and age. 
 *  
 * @param maxDurationMinutes The maximum duration in minutes for the game.  
 * @param numPlayers The number of players.  
 * @param minAge The minimum age of the players.  
 * @return A list of games matching the criteria.  
 */
```

We did not ask for the agent to go grab any extra information, but it did include the description about the games from the internet to make it more engaging!

**Something for you to try:** Now ask Gemini to add a play then go and check to see if it has worked in the app.

## Wrapping Up
Duration: 1

This perfectly highlights the magic of agentic AI. Our app did the heavy lifting of querying the local database for the exact constraints. But Gemini took over the presentation layer. It seamlessly combined our app's strictly structured local data with its own vast world knowledge to create a conversational, engaging response for the user, entirely for free. 

Think about what this means for us as developers! In a traditional app, if we wanted to show a dynamic summary or thematic description of the game, we’d have to build another API call, design a new UI layout, and wire it all up to tie it all together. With App Functions, we just hand the raw data back to the agent, and it handles the formatting and contextual enrichment automatically. We get to focus on our app's core logic, and the AI handles the rest.

It does show exactly why your KDocs are so critical. We didn't explicitly tell the agent to go search the web for descriptions, but because our `@AppFunction` parameters and return types were clearly documented—and we set `isDescribedByKDoc = true`—the LLM understood exactly what kind of entity it was holding. It then intuitively knew that adding a thematic description would best answer the user's implicit intent. The better your comments, the smarter the agent behaves!

While the App Functions API is still in private preview, there is absolutely nothing stopping you from preparing your app right now. By annotating your key features today, testing them locally with ADB, and refining those KDocs, you'll be lightyears ahead when the API opens up to the public. If you're looking for a shortcut, try the dedicated AppFunctions agent skill in the Android skills repository to automatically generate the required Kotlin code and metadata configurations. Take a look at your app's core workflows, pick one that users would love to trigger with their voice, and start building!

Your app is about to get a whole lot smarter!