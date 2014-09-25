CollageCreator
==============

CollageCreator uses scripting to automate the process of creating a collage. There's scrips for Photoshop and GIMP.

If you don't have GIMP, download it here (http://www.gimp.org/downloads/). Place the GIMP script called create-collage.scm into the default folder where GIMP saves its scripts (Ex. "C:\\Users\\Mike\\.gimp-2.8\\scripts"). Create collages by running CollageCreator_GIMP.bat and updating some parameters in the bat file first. The first path should be the path to GIMP on your computer. The remaining parameters of the create-collage function are below.

(create-collage files-in file-out num-rows num-cols num-collages) :

files-in = the input pictures, use "\*" character to select all pictures of a specific extension

file-out = full path of output collage file, NOTE: file-out must be a .png file, looking into how to make it more generic 

num-rows and num-cols are the number of rows and columns of the collage 

num-collages = number of collages to create, NOTE: only makes 1 collage for now, looking into making multiple collages and saving them

Example of create-collage function for creating a 3 by 3 collage of pictures from a trip to Mexico:
Windows - (create-collage \\"C:\\\Users\\\Mike\\\Pictures\\\Mexico 2013\\\*.jpg\\" \\"C:\\\Users\\\Mike\\\Pictures\\\Mexico 2013\\\collage1.png\\" 3 3 1)
Mac - 

Photoshop script also works but is quite a bit more complicated to set-up. Might add some more info for how to get it working.
