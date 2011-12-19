;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname quicksort) (read-case-sensitive #f) (teachpacks ((lib "image2.ss" "teachpack" "deinprogramm"))) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ((lib "image2.ss" "teachpack" "deinprogramm")))))
; Felix Bartusch - David Herrmann
; Aufgabe 2:

; filter - copied from source
(: filter ((%a -> boolean) (list-of %a) -> (list-of %a)))

(define filter
  (lambda (p? xs)
    (fold empty
          (lambda (e xs)
            (if (p? e)
                (make-pair e xs)
                xs))
          xs)))

; quicksort - sort a list based on a comparison function
(: quicksort ((%a %a -> boolean) (list-of %a) -> (list-of %a)))

(check-expect (quicksort <=
                         (list 3 2 1 0))
              (list 0 1 2 3))
(check-expect (quicksort >
                         (list 5 6 100 99))
              (list 100 99 6 5))
(check-expect (quicksort <= empty) empty)
(check-expect (quicksort <= (list 1)) (list 1))

(define quicksort
  (lambda (le? xs)
    (cond ((empty? xs)
           empty)
          ((pair? xs)
           (append (quicksort le?
                              (filter (lambda (x)
                                        (le? x (first xs)))
                                      (rest xs)))
                   (list (first xs))
                   (quicksort le?
                              (filter (lambda (x)
                                        (not (le? x
                                                  (first xs))))
                                      (rest xs))))))))