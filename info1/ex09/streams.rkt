;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname streams) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; #################### aus Vorlesung ####################

; "Versprechen", ein Wert der Signatur t liefern zu kÃ¶nnen
(define promise
  (lambda (t)
    (signature (-> t))))

; delay: Verzögere die Auswertung von e und liefere Versprechen,
;        e später auswerten zu können
; (: delay (%a -> (promise %a)))
; (delay <e>) â‰£ (lambda () <e>))

; "EinlÃ¶sung" (Auswertung) des Versprechens p
(: force ((promise %a) -> %a))
(define force
  (lambda (p)
    (p)))

; Polymorphe Paare (isomorph zu `pair')
(: make-cons (%a %b -> (cons-of %a %b)))
(: head ((cons-of %a %b) -> %a))
(: tail ((cons-of %a %b) -> %b))
(define-record-procedures-parametric cons cons-of
  make-cons 
  cons?
  (head
   tail))

; Streams mit Elementen der Signatur t
(define stream-of
  (lambda (t)
    (signature (cons-of t (promise cons)))))

(: from (number -> (stream-of number)))
(define from
  (lambda (n)
    (make-cons n (lambda () (from (+ n 1))))))

; Erzeuge die ersten n Elemente des Strom str (Stream â†’ Liste)
(: stream-take (natural (stream-of %a) -> (list-of %a)))
(check-expect (stream-take 5 (from 1)) (list 1 2 3 4 5))
(check-expect (stream-take 0 (from 1)) empty)
(define stream-take
  (lambda (n str)
    (if (= n 0) 
        empty
        (make-pair (head str)
                   (stream-take (- n 1) (force (tail str)))))))

; Filtere Stream str bzgl. PrÃ¤dikat p? (Stream â†’ Stream)
(: stream-filter ((%a -> boolean) (stream-of %a) -> (stream-of %a)))
(check-expect (stream-take 10 
                           (stream-filter (lambda (n) (= (remainder n 2) 0)) 
                                          (from 1)))
              (list 2 4 6 8 10 12 14 16 18 20))
(define stream-filter
  (lambda (p? str)
    (if (p? (head str))
        (make-cons (head str)
                   (lambda () (stream-filter p? (force (tail str)))))     
        (stream-filter p? (force (tail str))))))

; #################### Ab hier Loesungen ####################

; Aufgabe 1a)
; stream-merge verbindet zwei aufsteigend sortierte Streams zu einem aufsteigend sortierten Stream
(: stream-merge ((stream-of real) (stream-of real) -> (stream-of real)))

(check-expect (stream-take 6 (stream-merge (from 0) (from 2))) (list 0 1 2 2 3 3))
(check-expect (stream-take 6 (stream-merge (from 2) (from 2))) (list 2 2 3 3 4 4))
(check-expect (stream-take 6 (stream-merge (from 2) (from 0))) (list 0 1 2 2 3 3))

(define stream-merge
  (lambda (str1 str2)
    (if (<= (head str1) (head str2))
        (make-cons (head str1) (lambda () (stream-merge (force (tail str1)) str2)))
        (make-cons (head str2) (lambda () (stream-merge str1 (force (tail str2))))))))

; stream-distinct entfernt aus einem Stream doppelte Zahlen
(: stream-distinct ((stream-of number) -> (stream-of number)))

(check-expect (stream-take 6 (stream-distinct (stream-merge (from 0) (from 2))))
              (list 0 1 2 3 4 5))
(check-expect (stream-take 6 (stream-distinct (stream-merge (stream-merge (from 0) (from 0)) (from 0))))
              (list 0 1 2 3 4 5))
(check-expect (stream-take 6 (stream-distinct (from 0)))
              (list 0 1 2 3 4 5))

(define stream-distinct
  (lambda (str)
    (make-cons (head str)
               (lambda () (stream-distinct (stream-filter (lambda (x) (not (= x (head str))))
                                                          (force (tail str))))))))

; stream-add addiert jeweils die Elemente zweier Streams und erzeugt daraus einen neuen Stream
(: stream-add ((stream-of number) (stream-of number) -> (stream-of number)))

(check-expect (stream-take 5 (stream-add (from 1) (from 3)))
              (list 4 6 8 10 12))
(check-expect (stream-take 3 (stream-add (from 1) (from 1)))
              (list 2 4 6))
(check-expect (stream-take 3 (stream-add (from 0) (from 0)))
              (list 0 2 4))

(define stream-add
  (lambda (str1 str2)
    (make-cons (+ (head str1) (head str2))
               (lambda () (stream-add (force (tail str1))
                                      (force (tail str2)))))))

; fib berechnet Stream der Fibonacci-Folge
(: fib (stream-of natural))

(check-expect (stream-take 10 fib)
              (list 0 1 1 2 3 5 8 13 21 34))
(check-expect (stream-take 1 fib)
              (list 0))

(define fib
  (make-cons 0
             (lambda () (make-cons 1
                                   (lambda () (stream-add (force (tail fib))
                                                          fib))))))
