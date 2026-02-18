# 🎮 Head Ball Play

A fun 1v1 head ball football game built for Android using Java and Canvas-based custom physics engine.

---

## 📸 Screenshots

| Home Screen | Avatar Selection |
|:-----------:|:----------------:|
| ![Home Screen](screenshots/home.png) | ![Avatar Selection](screenshots/avatar_selection.png) |

| Gameplay | Goal! |
|:--------:|:-----:|
| ![Gameplay](screenshots/gameplay.png) | ![Goal](screenshots/goal.png) |

| You Won | Game Over |
|:-------:|:---------:|
| ![You Won](screenshots/won.png) | ![Game Over](screenshots/lost.png) |

---

## 📱 Features

- 🧠 **AI Bot opponent** with intelligent ball tracking
- ⚽ **Realistic physics** with gravity, bounce, and collision detection
- 🎭 **Avatar selection** — choose from 4 different football player characters
- 🏆 **Win/Loss screens** with Play Again and Exit options
- 🎆 **Particle effects** on goals
- 🕹️ **On-screen controls** — move left/right, jump, shoot (High/Low)
- 📊 **Score tracking** displayed live during gameplay

---

## 🏗️ Project Structure

```
app/src/main/
├── java/com/hatsynk/headballclone/
│   ├── activities/
│   │   ├── AvatarSelectionActivity.java   # Avatar picker screen
│   │   ├── GameActivity.java              # Main game screen
│   │   └── MainMenuActivity.java          # Home/menu screen
│   ├── game/
│   │   ├── BotIntelligence.java           # AI opponent logic
│   │   ├── Constants.java                 # Game constants & config
│   │   ├── GameThread.java                # Game loop thread
│   │   └── HeadBallEngine.java            # Core game engine
│   ├── objects/
│   │   ├── Ball.java                      # Ball physics & rendering
│   │   ├── Goal.java                      # Goal post logic
│   │   ├── ParticleSystem.java            # Goal celebration particles
│   │   └── Player.java                    # Player physics & rendering
│   └── physics/
│       └── AdvancedPhysics.java           # Physics calculations
└── res/
    ├── drawable/
    │   ├── avatar_1.png ~ avatar_4.png    # Player avatars
    │   ├── avatar_bot.png                 # Bot avatar
    │   └── avatar_player.png             # Default player avatar
    ├── layout/
    │   ├── activity_main_menu.xml         # Home screen layout
    │   ├── activity_main.xml              # Game screen layout
    │   └── activity_avatar_selection.xml  # Avatar picker layout
    └── values/
        ├── colors.xml
        ├── strings.xml
        └── themes/
```

---

## 🎮 How to Play

1. Launch the app and tap **PLAY GAME** from the home screen
2. Optionally tap **SELECT AVATAR** to pick your player character
3. In-game controls:
   - **`<`** — Move left
   - **`>`** — Move right
   - **`J`** — Jump
   - **`HI`** — Shoot high
   - **`LO`** — Shoot low
4. Score more goals than the bot to **WIN!**
5. First to reach the goal limit wins the match

---

## 🛠️ Tech Stack

| Technology | Usage |
|-----------|-------|
| **Java** | Core language |
| **Android Canvas API** | Custom 2D game rendering |
| **SurfaceView + Thread** | Smooth 60fps game loop |
| **Custom Physics Engine** | Gravity, velocity, collision |
| **SharedPreferences** | Saving selected avatar |

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest stable)
- Android SDK 21+
- Java 8+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/HeadBallPlay.git
   ```

2. Open the project in **Android Studio**

3. Let Gradle sync complete

4. Run on an emulator or physical device (API 21+)

---

## 📦 APK

> You can download the latest APK from the [Releases](https://github.com/your-username/HeadBallPlay/releases) section.

---

## 🙌 Acknowledgements

- Inspired by the original **Head Ball 2** mobile game
- Avatar artwork used for educational/demo purposes

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
