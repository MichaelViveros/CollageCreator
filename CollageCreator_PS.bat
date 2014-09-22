"C:\Program Files (x86)\Adobe\Photoshop Elements 9\PhotoshopElementsEditor.exe" "C:\Users\Owner\Documents\Adobe Scripts\CollageCreator.jsx"
taskkill /IM "ExtendScript Toolkit.exe"
FOR /F "delims=|" %%I IN ('DIR "C:\Program Files (x86)\CollageCreator\Collages" /B /O:D') DO SET NewestFile=%%I
explorer /select,"C:\Program Files (x86)\CollageCreator\Collages\%NewestFile%"