# GSPP
GameSeed++

Like previous GameSeed this one is for making small games.

New here is Menu with highscore and Localisation.
A few changes in "Glob" (added loading images from jar and some refactoring).
New interface "NPC_Sprite" and implementation for it "RPGM2K_NPC_Sprite" a container for images to simplyfy work with NPC sprites. Has Flow pattern to make it even simplier to use. (RPGM2K_NPC_Sprite is not yet tested)
build script for ant.

# How to use.
1. Create a new Java application project.
2. Copy content of "src" folder to "src" folder in your project. Optional copy "build.xml" to your project folder.
3. There are two types of markers: "TODO" and "OPTIONAL". TODO marks places for you to put your code in. OPTIONAL are parts of code where you can add some optional changes like how highscore is sorted and represented, screen size, fullscreen...
4. Put sprites in graphic folder.
5. Change values in MyBundle_XX.properties
6. use build.xml to build your game.
