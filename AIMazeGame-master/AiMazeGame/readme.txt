Student Details: 

Name: Aron O' Malley. 
GNumber: g00327019
Year: 4th
Subject: Artificial intelligence. 
Project: Artificial intelligence maze game. 

Introduction: 

For our A.I. module we have been tasked with creating a maze game using the stubs provided to us on Moodle and incorporate some forms of artificial intelligence into it including fuzzy logic.

How the game works: 

The game starts by bringing you to the main menu, the originally purpose of this menu was to allow the user to select different difficulties settings but due to errors and time constraints 
I could not implement the other difficulty settings so the only selectable option currently is easy, selecting and of the other options in the menu will set the default difficulty. 
Once the difficulty is selected the player will be spawned into the maze. The player starts with 0 health so the first course of action should be to replenish your health with any 
nearby medpacks. There are two types of enemy in my game, red spiders and black spiders. The red spiders are set to move at random due to the fact my fuzzy logic file does not seem to work, 
they are originally supposed to follow random behaviours such as hiding from the player if his health is too high or attacking him if his health is too low, but that does not work all the time
The other enemy are the black spiders, they use the A* algorithm to hunt down and attempt to kill the player, this works quite well although the spiders seem to get stuck on walls sometimes. 
There are three different weapons available to the player, the sword, the bomb, and the hydrogen bomb. Sword being the weakest, bomb being the second best, hydrogen bomb being the strongest. 
Once the player is spawned into the maze, they must find their way to the exit goal, pressing z will bring up the map screen which the player can use to keep track of spiders as well as navigate 
to the exit goal. Another pickup available to the player are small pins that look like way points, picking up one of these will make the A* algorithm attempt to find a path towards the exit, if 
The path is found a trail of coins will appear and go towards the exit that the player can follow to win the game. 

Problems and unimplemented features: 

Over all I am satisfied with the project but not too happy. Originally when training my neural network, it would take upwards of 30 seconds for the training to complete, after changing some values 
and inputs to try and make the training data a bit better it now says it only takes 0 seconds which in my opinion is not a good sign. I am happy with how the black spiders manage to find 
and hunt the player character as well as how the exit can be found after picking up a map pin, the A* algorithm works very well with the project. However fuzzy does not seem to work very well 
As the spider’s behaviour seems random and erratic no matter what behaviour state they are set too. On top of this, the game contains MANY bugs that I cannot fix, the program is quite big 
and incorporates a large number of threads so that would explain the multitude of bugs I am experiencing as well as random gameplay crashes. Overall though, I am quite happy with how it 
turned out. 

Known bugs: 

Common Bugs: 

* Black spiders seem to "blink" (Disappear and reappear) while moving about the maze 
* The game crashes often. I have noticed that its usually on start-up if the player attempts to move too fast but can also happen when picking up an item or weapon. 
* Receive a null pointer error in the console every time I open the map

Rare Bugs: 

* Player sprite sometimes does not load in and instead is replaced with a white square. (happened 5 times)
* Black spiders are totally invisible and can only be seen on the map even though they are in the game and can still fight the player (happened 7 times) 
* Maze sometimes does not load, this leads to the game crashing completely and a blank space being displayed in place of the game view (happened 3 times)

Resources and research: 

* Labs provided on the A.I. Moodle page for gmit 
* Plenty of error checking with stack overflow 
* Various YouTube videos that showed me how to implement A * for the black spiders


Built with: 

* Java version 8 
* Eclipse JEE 2018-09

===================================
IMPORTANT NOTICE 
===================================

After creating and running the jar file with the project I have noticed that for some reason the fight system does not work when the game is ran with a jar file (cmd "java –cp ./game.jar ie.gmit.sw.ai.GameRunner"
Due to this if you really want to see the app working correctly I would advise running it the normal way of importing the project into eclipse,  
right clicking the "GameRunner.java" file and selecting to run it as a java application. I would try and fix this error but I do not think I have the time for it. (one hour until deadline)
