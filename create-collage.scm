; creates a collage of random pictures
; parameters: 
; files-in = directory where input pictures are (Ex. "C:\\Users\\Owner\\Pictures\\Mexico Trip")
; file-out = full path of output collage file (Ex. "C:\\Users\\Owner\\Pictures\\Mexico Trip\\Collage1.png")
; NOTE: file-out must be a .png file, looking into how to make it more generic
; num-rows and num-cols are the number of rows and columns of the collage
; num-collages = number of collages to create (= 1 for now)
(define (create-collage files-in file-out num-rows num-cols num-collages)
	(let* ((collage-width 1200)
		(collage-height 800)
		(image (car (gimp-image-new collage-width collage-height RGB)))
		(drawable (car (gimp-image-active-drawable image)))
		(filelist (cadr (file-glob files-in 1)))
		(layer 0)
        (pic-width (/ collage-width num-cols))
        (pic-height (/ collage-height num-rows))
		(i 0)
		(j 0)
		(x 0)
		(y 0)
		(file 0)
		(num-pics-available)
		(pic 0)
		(pic2 0)
		(retvals 0))
	
	; loop through rows and columns and insert random pics such that each cell 
	; is 1 landscape pic or 2 portrait pics
	(set! i 0)
	(set! num-pics-available (length filelist))
	(while (< i num-rows)
		(set! y (* i pic-height))
		(set! j 0)
		(while (< j num-cols)
			; get random pic and update filelist
			(set! x (* j pic-width))
			(set! retvals (get-random-pic filelist num-pics-available #f image))
			(set! num-pics-available (- num-pics-available 1))
			(set! pic (car retvals))
			(set! filelist (cadr retvals))
			
			; open pic and check if portrait
			(set! layer (car (gimp-file-load-layer RUN-NONINTERACTIVE image pic)))
			(if (> (car (gimp-drawable-height layer)) (car (gimp-drawable-width layer)))
			
				; then, portrait pic
				(begin
					; get 2nd portrait pic
					(set! retvals (get-random-pic filelist num-pics-available #t image))
					(set! num-pics-available (- num-pics-available 1))
					(set! pic2 (car retvals))
					(set! filelist (cadr retvals))
					
					; NOTE: need to add code to handle when a 2nd portrait pic can't be found, (caddr retvals) will be false
					
					; place 1st pic in left half of cell
					(gimp-image-insert-layer image layer 0 -1)
					(gimp-layer-scale layer (/ pic-width 2) pic-height FALSE)
					(gimp-layer-translate layer x y)
					
					; place 2nd pic in right half of cell
					(set! layer (car (gimp-file-load-layer RUN-NONINTERACTIVE image pic2)))
					(gimp-image-insert-layer image layer 0 -1)
					(gimp-layer-scale layer (/ pic-width 2) pic-height FALSE)
					(gimp-layer-translate layer (+ x (/ pic-width 2)) y))
					
				; else, landscape pic
				(begin
					; insert pic into full cell
					(gimp-image-insert-layer image layer 0 -1)
					(gimp-layer-scale layer pic-width pic-height FALSE)
					(gimp-layer-translate layer x y)))
					
			(set! j (+ j 1)))
			
		(set! i (+ i 1)))
	 
	;(gimp-xcf-save RUN-NONINTERACTIVE image drawable file-out "collage")
	(file-png-save-defaults 1 image (car (gimp-image-merge-visible-layers image CLIP-TO-IMAGE)) file-out "collage")
	)
	)

; -selects a random picture from file-list and updates file-list
; so selected picture can not be chosen again in future calls
; -can specifically select a random portrait pic
; -NOTE: random functions seems to create same collage every time, looking into how to resolve that
; parameters:
; file-list = list of files to select random picture from
; num-pics-available = number of pics not selected yet, used to avoid randomly selecting same pic multiple times
; need-portrait = indicates if randomly selected pic must be a portrait pic
; image = image where pics are being inserted, used for checking if a pic is portrait or not
; return values:
; file = randomly selected picture from file-list
; file-list = updated list of files
; success = indicates if random pic was successfully selected
(define (get-random-pic file-list num-pics-available need-portrait image)
	(let* ((file 0)
		(index 0)
		(temp 0)
		(layer 0))
	
	; select random pic 
	(set! index (random num-pics-available))
	(set! file (list-ref file-list index))
	
	; update file-list so that available pics stay at the front of file-list
	; and selected pics move to the back
	(set! temp (list-ref file-list (- num-pics-available 1)))
	(set-car! (list-tail file-list index) temp) ;file-list[index]=file-list[num-pics-available-1]
	(set-car! (list-tail file-list (- num-pics-available 1)) file) ;file-list[num-pics-available-1]=file-list[index]

	; check if need portrait pic
	(if (equal? need-portrait #t) 
	
		; then, need portrait pic
		(begin
			(set! layer (car (gimp-file-load-layer RUN-NONINTERACTIVE image file)))
			(if (not (> (car (gimp-drawable-height layer)) (car (gimp-drawable-width layer))))
			
				; then, selected pic is not portrait
				(begin
					; check if there's still pics available to select
					(if (equal? num-pics-available 1)
				
						; then, ran out of pics so return failure
						(list file file-list #f)
							
						; else, make recursive call to try and get a portrait pic
						(get-random-pic file-list (- num-pics-available 1) #t image)))
				
				; else, file is portrait pic so success
				(list file file-list #t)))
		
		; else, didn't need portrait pic so any pic is a success
		(list file file-list #t))))
