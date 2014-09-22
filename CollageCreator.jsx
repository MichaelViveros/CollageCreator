// global variables used by different functions
var isPortrait, index, doc, docRef, fileList, numPics;
index=0;
var collageCreatorPath = "/C/Program Files (x86)/CollageCreator";
var needArgs = false;
main(needArgs);
//saveCollage();

// creates a collage in photoshop of randomly chosen pictures from an input folder
function main(needArgs) {
    // setup photoshop document and get pictures
    $.writeln("Creating collage ... ");
    var defaultRulerUnits = preferences.rulerUnits;
    preferences.rulerUnits = Units.PIXELS;
    var docWidth = new UnitValue(17.0,"in");
    var docLength = 0.667*docWidth; // length = 2/3*width
    var dpi = 72.0; // dots per inch
    
    var inFolder, rows, columns, numCollages;
    if (needArgs) {
        inFolder = Folder.selectDialog("Please select folder to process");
        if (inFolder != null) {
           fileList = inFolder.getFiles(/\.(jpg)$/i);
        }
        //printPicNames();
        var dimensions = prompt('Enter the dimensions of the collage with an "x" in the middle\nEx. 3x3', "3x3");
        rows = parseInt(dimensions.split("x")[0]);
        columns = parseInt(dimensions.split("x")[1]);
        numCollages = 1;
    } else {
        var readFile = new File(collageCreatorPath+"/args.txt"); 
        readFile.open('r', undefined, undefined);
        var str = readFile.read();
        readFile.close();
        var args = str.split(",");
        $.writeln("args " + args);
        inFolder = new Folder(args[0]);
        fileList = inFolder.getFiles(/\.(jpg)$/i);
        numCollages = parseInt(args[1]);
        rows = parseInt(args[2]);
        columns = parseInt(args[3]);
        $.writeln("inFolder " + inFolder.absoluteURI + ", numCollages " + numCollages + ", rows " + rows + ", columns " + columns);
    }
    numPics = fileList.length;
    
    // loop numPics times
    //for (var num= 0; num < numCollages; num++) {
        
        // loop through rows and columns of collage and insert random pictures
        var halfWidth, oldNumPics, picWidth, picLength, selectionBounds, curDoc, curLayer; // declare local unitialized variables used by main function
        picLength = docLength/rows*dpi;
        picWidth = docWidth/columns*dpi;
        var gap = 0;
        var oddPortraitPics = false; // var for is there an odd # of portrait pics
        curDoc = app.documents.add(docWidth, docLength, dpi, "My New Doc");
        
        for(var i = 0; i < rows; i++){
            
            for(var j = 0; j < columns; j++){
                
                getRandomPic();
                resizeAndCopy(picLength, picWidth);
                activeDocument = curDoc;
                curLayer = curDoc.artLayers.add();
                selectionBounds = [[j*picWidth,i*picLength],[(j+1)*picWidth-gap,i*picLength], // topLeft,topRight
                [(j+1)*picWidth-gap,(i+1)*picLength-gap],[j*picWidth,(i+1)*picLength-gap]]; // bottomRight,bottomLeft
                
                if (isPortrait) {
                    halfWidth = (picWidth-gap) / 2;
                    // insert first portrait pic into first half of space
                    selectionBounds[1][0] -= halfWidth;
                    selectionBounds[2][0] -= halfWidth;
                    curDoc.selection.select(selectionBounds,SelectionType.REPLACE,0,false);
                    curDoc.paste();
                    curDoc.selection.deselect();          
                    // explore pics to find a second portrait pic
                    isPortrait = false;
                    oldNumPics = numPics;
                    while (!isPortrait && numPics > 0) {
                        getRandomPic();
                        if (!isPortrait) {
                            app.activeDocument.close(SaveOptions.DONOTSAVECHANGES);
                        }
                    }
                    if (numPics == 0) { // odd # of portrait pics so no more portrait pics left
                        $.writeln("odd # of portrait pics");
                        //remove last portrait pic
                        curLayer.remove();
                        // restore numPics to its old value - 1 so getRandomPic can still get landscape 
                        // pics you may have explored when trying to find second portrait pic
                        numPics = oldNumPics;
                        // get new random pic and restore selection bounds
                        getRandomPic();
                        resizeAndCopy(picLength, picWidth);
                        activeDocument = curDoc;
                        curDoc.artLayers.add();
                        selectionBounds[1][0] += halfWidth;
                        selectionBounds[2][0] += halfWidth;
                    } else { // found second portrait pic
                        resizeAndCopy(picLength, picWidth);          
                        activeDocument = curDoc;
                        curDoc.artLayers.add();   
                        // shift left bounds over
                        selectionBounds[0][0] += halfWidth;
                        selectionBounds[3][0] += halfWidth;
                        // shift right bounds over
                        selectionBounds[1][0] += halfWidth;
                        selectionBounds[2][0] += halfWidth;
                        // check if landscape pics were explored
                        if (numPics != (oldNumPics-1)) {
                            restoreLandscapePics(oldNumPics);
                         }
                    }
                  }
              
                  curDoc.selection.select(selectionBounds,SelectionType.REPLACE,0,false);
                  curDoc.paste();
                  curDoc.selection.deselect();   
                  
                 } // end column loop
            
            } // end rows loop
        
        saveCollage();
        app.activeDocument.close(SaveOptions.DONOTSAVECHANGES);
        
    //} // end numPics loop
    
    // restore default value of ruler units
    preferences.rulerUnits = defaultRulerUnits;
    
    // close photoshop
    var idquit = charIDToTypeID( "quit" );
    executeAction( idquit, undefined, DialogModes.ALL );
}

// resizes image to fit into collage and copies it to the clipboard
function resizeAndCopy(length, width) {
    if (isPortrait) {
        docRef.resizeImage(null, UnitValue(length, "px"), null, ResampleMethod.BICUBIC);
    } else {
        docRef.resizeImage(UnitValue(width, "px"), null, null, ResampleMethod.BICUBIC);
    }
    activeDocument.selection.selectAll();
    activeDocument.selection.copy();
    app.activeDocument.close(SaveOptions.DONOTSAVECHANGES);
}

// selects a random picture and updates fileList
function getRandomPic() {
     index = Math.floor(Math.random() * numPics);
     doc = open(fileList[index]);
     var tempPic = fileList[index];
     fileList[index] = fileList[numPics-1]; // move pic not chosen yet to chosen pic's position in fileList
     fileList[numPics-1] = tempPic; // move chosen pic to back of fileList so it can't be chosen again
     numPics--;
     docRef = app.activeDocument;
     isPortrait = docRef.height > docRef.width;
     $.writeln("index "+index+", numPics "+numPics+", isPortrait "+isPortrait);
     //printPicNames();
}  

// prints name of pictures in folder
function printPicNames() {
    var folders;
    var delim = "/";
     $.writeln(".");
    for (var i = 0; i <fileList.length; i++) {
        folders = (fileList[i]+"").split(delim);
        $.writeln("pic"+i+" = "+folders[folders.length-1]);
    }
     $.writeln(".");
}

// updates fileList so landscape pics that may have been explored while looking 
// for portrait pic can still be chosen by getRandomPic in the future
function restoreLandscapePics(oldNumPics) {
    // swap out second portrait pic found
    $.writeln("swapping out 2nd portrait pic");
    var tempPic = fileList[numPics];
    fileList[numPics] = fileList[oldNumPics-1];
    fileList[oldNumPics-1] = tempPic;   
    //printPicNames();
    // restore numPics to its old value - 1 so getRandomPic can still get landscape 
    // pics you may have explored when trying to find second portrait pic
    numPics = oldNumPics - 1;         
    $.writeln("numPics restored, "+numPics);
}

function saveCollage() {
    var folder = new Folder(collageCreatorPath+"/Collages");
    //$.writeln("folder " + folder.absoluteURI);
    var files = folder.getFiles();
    var count = 1;
    for (var i = 0; i < files.length; i++) {
        if (files[i].name.indexOf("Collage") > -1)
            count++;
    }
    var file = new File(folder.absoluteURI+ "/Collage" + count + ".jpg");
    //$.writeln("file " + file.absoluteURI);
    var options = new JPEGSaveOptions();
    app.activeDocument.saveAs(file, options, true);
}
