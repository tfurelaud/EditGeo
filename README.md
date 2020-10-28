# EditGeo #

This is a software to edit geometric shapes made in JAVA. 

![EditGeo Snapshot](snap0.png?raw=true "snapshot")

## How to use ##

### Software

When you launch the software, the board is clean (white). You have 2 basics shapes that you can drag and drop on the board. 

When a shape is on the board, you can right click on it to open a menu which is going to help you to personalize your shape (color, ...). If you want to do a circle, choose an octogone then add a lot of sides. 

If you want to save a shape, then drag and drop from the board to the toolbar. 

If you want to create a group of objects, then select it by dragging and dropping on the board and right click in the selection's rectangle, then click on group or degroup. Of course, you can save a group of shapes. 

You can drag and drop any shape that you want to delete on the garbage icon (bottom left). 

### Save

If you want to save your project, then click on "save". A window will appear. After, you'll be able to lad a project. 

If you close the software without saving anything, the next time you'll open it, your toolbar will be the same. It's a safety way took for accident prevention. 

### Implementation

The implementation of this software follows a MVC architecture (Model/View/Controller).

The View part is implemented with JFX only. 

As you can see, tests are only did for undo/redo functions. 

You could find UMLs in the UML folder. 


## Download & Install ##

First clone the project available on GitHUB under GPL:

```
  git clone https://github.com/tfurelaud/EditGeo
```

You'll need javafx and JUnit for tests. JUnit is not needed for the software.

```
  sudo apt install openjdk-8-jdk openjfx junit
```

After that, you can execute the jar which is runnable:

```
  java -jar EditGeo.jar
```



