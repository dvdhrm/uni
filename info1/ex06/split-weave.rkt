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
(: first-list ((paar-of %a %a) -> %a))
(: second-list  ((paar-of %a %a) -> %a))
(define-record-procedures-parametric paar paar-of
  make-paar
  paar?
  (first-list
   second-list))

; split-list konsumiert eine beliebige Liste und gibt ein paar von Listen zurück wobei
; abwechselnd je ein element verteilt wird.
(: split-list ((list-of %a) -> (paar-of %a %a)))
(check-expect (split-list empty) (make-paar empty empty))
(check-expect (split-list (list 1)) (make-paar (list 1) empty))
(check-expect (split-list (list 1 2)) (make-paar (list 1) (list 2)))
(check-expect (split-list (list 1 2 3 4 5 6)) (make-paar (list 1 3 5) (list 2 4 6)))
(define split-list
  (lambda (xs)
    (cond ((empty? xs) (make-paar empty empty))
          ((pair? xs)
           (let ((r (rest xs))
                 (f (first xs)))
             (cond ((empty? r) (make-paar (make-pair f
                                                     empty)
                                          empty))
                   ((pair? r)
                    (let ((l (split-list (rest r))))
                      (make-paar (make-pair f
                                            (first-list l))
                                 (make-pair (first r)
                                            (second-list l)))))))))))

;b)
; weave-list konsumiert ein paar von Listen und gibt eine Liste zurück, die Elemente der paar-Listen abwechselnd enthalten
(: weave-lists ((paar-of %a %a) -> (list-of %a)))
(check-expect (weave-lists (make-paar empty empty)) empty)
(check-expect (weave-lists (make-paar (list 1) empty)) (list 1))
(check-expect (weave-lists (make-paar (list 1) (list 2))) (list 1 2))
(check-expect (weave-lists (make-paar (list 1 3 5) (list 2 4 6))) (list 1 2 3 4 5 6))
(define weave-lists
  (lambda (paar)
    (let ((xs1 (first-list paar))
          (xs2 (second-list paar)))
      (cond ((empty? xs1) xs2)
            ((empty? xs2) xs1)
            ((and (pair? xs1) (pair? xs2))
             (make-pair (first xs1)
                        (make-pair (first xs2)
                                   (merge-list (rest xs1) (rest xs2)))))))))

; c)
(check-property (for-all ((xs (list-of natural)))
                  (expect (weave-lists (split-list xs)) xs)))