CollageCreator
==============

CollageCreator uses scripting to automate the process of creating a collage. It is a plugin for the [GIMP](http://www.gimp.org/) Image Manipulation program written in the Scheme functional programming language.

To create collages: <br>
1. Download [GIMP](http://www.gimp.org/downloads/). <br>
2. Place create-collage.scm into the default folder for GIMP scripts (Ex. "C:\\Program Files\\GIMP 2\\share\\gimp\\2.0\\scripts"). <br>
3. Update the parameters to the create-collage function in CollageCreator.bat and then run it. <br>

Usage: <br>
create-collage files-in out-path num-rows num-cols num-collages

files-in = the input pictures <br>
out-path = directory where collages will be saved <br>
num-rows = number of rows in the collage <br>
num-cols = number of columns in the collage  <br>
num-collages = number of collages to create

Example to create 2 3x3 collages of pictures from a trip to Mexico: <br>
create-collage \\"C:\\\Users\\\Mike\\\Pictures\\\Mexico\\\\*.jpg\\" \\"C:\\\Users\\\Mike\\\Pictures\\\Mexico\\\\\" 3 3 2
<br>

## Sample 2x2 Collage:
![2x2 Collage](./images/Collage2x2.jpg)
<br>

## Sample 3x3 Collage:
![3x3 Collage](./images/Collage3x3.jpg)
<br>

## Command Line Output:
![Command Line Output](./images/Screenshot.JPG)
