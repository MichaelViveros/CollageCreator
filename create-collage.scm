(define (create-collage files-in file-out file-out-2 num-rows num-cols num-collages)
	(let* 
	
	(
		(image (car (gimp-image-new 500 500 RGB)))
		(drawable (car (gimp-image-active-drawable image)))
		(filelist (cadr (file-glob files-in 1)))
		(layer)
        (width 100)
        (height 100)
		(i)
		(j)
		(x)
		(y)
		(file)
		(num-pics (* num-rows num-cols))
		(pic)
		(retvals)
	)
	
	;append "" to list of files for future call to get-random-pic function
	(set! filelist (append filelist (list "")))
	 
	(set! i 0)
	(while (< i num-rows)
		(set! y (* i height))
		(set! j 0)
		(while (< j num-cols)
			(set! x (* j width))
			(set! retvals (get-random-pic filelist))
			(set! pic (car retvals))
			(set! filelist (cadr retvals))
			(set! layer (car (gimp-file-load-layer RUN-NONINTERACTIVE image pic)))
			(gimp-image-insert-layer image layer 0 -1)
			(gimp-layer-scale layer width height FALSE)
			(gimp-layer-translate layer x y)
			(set! j (+ j 1))
		)
		(set! i (+ i 1)) 
	)
	 
	;(gimp-xcf-save RUN-NONINTERACTIVE image drawable file-out "collage")
	
	(file-png-save-defaults 1 image (car (gimp-image-merge-visible-layers image CLIP-TO-IMAGE)) file-out-2 "collage")
	)
)

(define (get-random-pic file-list)
	(let*
	
	(
		(end-index 0)
		(file (list-ref file-list end-index))
		(index)
		(temp)
	)
	
	; find number of available pics (available -> not selected yet)
	;(print (string-append "file - " file))
	(while (not (equal? file ""))
		(set! end-index (+ end-index 1))
		(set! file (list-ref file-list end-index))
		;(print (string-append "file - " file))
	)
	(print (string-append "end-index - "(number->string end-index)))
	
	; select random pic and update file-list so that available pics stay at the
	; front of file-list and selected pics move to the back and get replaced by ""
	(set! index (random end-index))
	(print (string-append "index - "(number->string index)))
	(set! file (list-ref file-list index))
	(print (string-append "file - " file))
	(set! temp (list-ref file-list (- end-index 1)))
	(print (string-append "temp - " temp))
	(set-car! (list-tail file-list index) temp) ;file-list[index]=file-list[end-index-1]
	(set-car! (list-tail file-list (- end-index 1)) "") ;file-list[end-index-1]=""
	
	; return file
	(list file file-list)
	
	)
)
