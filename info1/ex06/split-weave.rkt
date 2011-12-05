;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname split-weave) (read-case-sensitive #f) (teachpacks ((lib "image2.ss" "teachpack" "deinprogramm"))) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ((lib "image2.ss" "teachpack" "deinprogramm")))))
; David Herrmann / Felix Bartusch
; Aufgabe 1a)

; Ein Paar von Listen (paar-of %a %a) besteht aus
; - einer ersten Liste (first)
; - einer zweiten Liste (rest)
(: make-paar (%a %a -> (paar-of %a %a)))
(: paar? (any -> boolean))
(: first-list ((paar-of %a %b) -> %a))
(: second-list  ((paar-of %a %b) -> %b))
(define-record-procedures-parametric paar paar-of
  make-paar
  paar?
  (first-list
   second-list))

; list-segmenting bekommt eine Liste und erstellt eine neue Liste, in dem nur jedes 2. Elemente der Ausgangsliste vorkommt.
(: list-segmenting ((list-of %a) (list-of %a) (list-of %a) -> (paar-of %a %a)))
(check-expect (list-segmenting (list 1 2 3 4 5 6) empty empty) (make-paar (list 1 3 5) (list 2 4 6)))
(check-expect (list-segmenting (list 1) empty empty) (make-paar (list 1) empty))
(check-expect (list-segmenting empty empty empty) (make-paar empty empty))
(define list-segmenting
  (lambda (xs acc1 acc2)
    (cond ((empty? xs) (make-paar (reverse acc1) (reverse acc2)))
          ((pair? xs) (if (empty? (rest xs))
                          (list-segmenting empty (cons (first xs) acc1) acc2)
                          (list-segmenting (rest (rest xs)) (cons (first xs) acc1) (cons (first (rest xs)) acc2)))))))
           
; split-list konsumiert eine beliebige Liste, gibt diese an list-segmenting weiter und bildet dann ein
; paar aus den beiden Listen welche von den beiden list-segmenting zurückgegeben werden
(: split-list ((list-of %a) -> (paar-of %a %a)))
(check-expect (split-list empty) (make-paar empty empty))
(check-expect (split-list (list 1)) (make-paar (list 1) empty))
(check-expect (split-list (list 1 2)) (make-paar (list 1) (list 2)))
(check-expect (split-list (list 1 2 3 4 5 6)) (make-paar (list 1 3 5) (list 2 4 6)))
(define split-list
  (lambda (xs)
    (list-segmenting xs empty empty)))

;b)
; merge-list konsumiert zwei Listen und fügt sie zu einer neuen Liste zusammen. Die Elemente der beiden Ausgangslisten werden abwechselnd
; in die entstehende Liste eingefügt
(: merge-list ((list-of %a) (list-of %a) -> (list-of %a)))
(check-expect (merge-list empty empty) empty)
(check-expect (merge-list (list 1) empty) (list 1))
(check-expect (merge-list (list 1) (list 2)) (list 1 2))
(check-expect (merge-list (list 1 3 5) (list 2 4 6)) (list 1 2 3 4 5 6))
(define merge-list
  (lambda (xs1 xs2)
    (cond ((empty? xs1) xs2)
          ((empty? xs2) xs1)
          ((and (pair? xs1) (pair? xs2)) (make-pair (first xs1)
                                                    (make-pair (first xs2)
                                                               (merge-list (rest xs1) (rest xs2))))))))

; weave-list konsumiert ein paar von Listen und gibt eine Liste zurück, die Elemente der paar-Listen abwechselnd enthalten
(: weave-lists ((paar-of %a %a) -> (list-of %a)))
(check-expect (weave-lists (make-paar empty empty)) empty)
(check-expect (weave-lists (make-paar (list 1) empty)) (list 1))
(check-expect (weave-lists (make-paar (list 1) (list 2))) (list 1 2))
(check-expect (weave-lists (make-paar (list 1 3 5) (list 2 4 6))) (list 1 2 3 4 5 6))
(define weave-lists
  (lambda (paar)
    (merge-list (first-list paar) (second-list paar))))

; c)
(check-property (for-all ((xs (list-of natural)))
                  (expect (weave-lists (split-list xs)) xs)))