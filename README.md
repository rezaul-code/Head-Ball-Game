# 🎮 Head Ball Play

A fun 1v1 head ball football game built for Android using Java and Canvas-based custom physics engine.

---
## 📸 Screenshots

| Home Screen | Avatar Selection |
|:-----------:|:----------------:|
| <img src="https://github.com/user-attachments/assets/fcc40b9b-e27b-4765-a547-f5f20501dda5" width="200"> | <img src="https://github.com/user-attachments/assets/8785f4fd-a6b3-4405-bc4b-d1a864cfc9a2" width="200"> |

| Gameplay | Goal! |
|:--------:|:-----:|
| ![Gameplay](https://github.com/user-attachments/assets/67aff957-5c28-488e-ba7c-eaaaa8f0f5d3) | ![Goal](https://github.com/user-attachments/assets/bc2d585f-6054-4cd3-901c-e141a5ee72d9) |

| You Won | Game Over |
|:-------:|:---------:|
| ![You Won](https://github.com/user-attachments/assets/3eeefc27-4470-428a-bbae-2524ee3af5e2) | ![Game Over](https://github.com/user-attachments/assets/856748d6-33e4-4cfb-83be-bbf26fecb80b) |

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
   [(https://github.com/rezaul-code/Head-Ball-Game.git)]
   ```

2. Open the project in **Android Studio**

3. Let Gradle sync complete

4. Run on an emulator or physical device (API 21+)

---

## 📦 APK

> You can download the latest APK from the -->(https://drive.google.com/file/d/1WXTaxeG82WEtyXBBYsOcb9QLI5w_2z_J/view?usp=drive_link)

---

## 🙌 Acknowledgements

- Inspired by the original **Head Ball 2** mobile game
- Avatar artwork used for educational/demo purposes

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
