# Head-Ball-Game

Head Ball Play is a 2D arcade soccer game built natively for Android using Java/Android Studio. The project demonstrates the implementation of a custom game loop, collision detection, and basic AI logic without relying on heavy third-party game engines like Unity.

Technical Highlights:

Custom Physics Engine: Implemented AdvancedPhysics classes to handle ball trajectory, gravity, and player-ball collisions.

Game Loop Architecture: Utilizes a dedicated GameThread to manage frame updates and rendering separately from the UI thread for smooth performance.

Bot Intelligence: Features a custom AI (BotIntelligence.java) that tracks ball position and predicts trajectories to defend and attack autonomously.

Asset Management: Efficient handling of sprite sheets and drawable resources for avatar selection and gameplay elements.

Architecture: Organized modular structure separating Activities (UI), Game Logic, and Physics objects.
