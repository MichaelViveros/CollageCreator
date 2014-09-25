CollageCreator
==============

CollageCreator uses scripting to automate the process of creating a collage. There's scrips for Photoshop and GIMP.

Gimp script works. If you don't have GIMP, download it here (http://www.gimp.org/downloads/). Create collages by running CollageCreator_GIMP.bat and updating some parameters in the bat file first. The first path should be the path to GIMP on your computer. The remaining parameters of the create-collage function are below.

(create-collage files-in file-out num-rows num-cols num-collages) :

files-in = the input pictures, use "\*" character to select all pictures of a specific extension (Ex. "C:\\\Users\\\Owner\\\Pictures\\\Mexico 2013\\\*.jpg"), 

file-out = full path of output collage file (Ex. "C:\\\Users\\\Owner\\\Pictures\\\Mexico 2013\\\Collage1.png"), NOTE: file-out must be a .png file, looking into how to make it more generic, 

num-rows and num-cols are the number of rows and columns of the collage, 

num-collages = number of collages to create, 
NOTE: only makes 1 collage for now, looking into making multiple collages and saving them, 

Photoshop script also works but is quite a bit more complicated to set-up. Might add some more info for how to get it working.
