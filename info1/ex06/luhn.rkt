;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname luhn) (read-case-sensitive #f) (teachpacks ((lib "image2.ss" "teachpack" "deinprogramm"))) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ((lib "image2.ss" "teachpack" "deinprogramm")))))
; David Herrmann / Felix Bartusch
; Aufgabe 2a)
; sum berechnet die Summe einer Liste von natuerlichen Zahlen
(: sum ((list-of natural) -> natural))
(check-expect (sum (list  2 4 6 8)) 20)
(check-expect (sum empty) 0)
(check-expect (sum (list 9)) 9)
(define sum
  (lambda (xs)
    (cond ((empty? xs) 0)
          ((pair? xs) (+ (first xs)
                         (sum (rest xs)))))))

; digits zerlegt eine natuerliche Zahl in ihre Ziffern und macht aus ihnen eine Liste
(: digits (natural -> (list-of natural)))
(check-expect (digits 0) (list 0))
(check-expect (digits 1024) (list 4 2 0 1))
(check-expect (digits 293486) (list 6 8 4 3 9 2))
(define digits
  (lambda (n)
    (if (< n 10)
        (make-pair n empty)
        (make-pair (remainder n 10)
                   (digits (quotient n 10))))))

; double-every-other-number konsumiert eine Liste von Zahlen und verdoppelt jede zweite Zahl
(: double-every-other-number ((list-of real) -> (list-of real)))
(check-expect (double-every-other-number empty) empty)
(check-expect (double-every-other-number (list 1 2 3 4 5)) (list 1 4 3 8 5))
(check-expect (double-every-other-number (list 1 3 5 6 9)) (list 1 6 5 12 9))
(define double-every-other-number
  (lambda (xs)
    (cond ((empty? xs) empty)
          ((pair? xs) (if (empty? (rest xs))
                          (make-pair (first xs)
                                     empty)
                          (make-pair (first xs)
                                     (make-pair (* 2 (first (rest xs)))
                                                (double-every-other-number (rest (rest xs))))))))))

; map-digits nimmt eine Liste von natuerlichen Zahlen und schreibt die Ziffern jeder Zahl in eine Liste
; Heraus kommt eine Liste von Listen
(: map-digits ((list-of natural) -> (list-of (list-of natural))))
(check-expect (map-digits empty) empty)
(check-expect (map-digits (list 2 13 9)) (list (list 2) (list 3 1) (list 9)))
(check-expect (map-digits (list 1)) (list (list 1)))
(define map-digits
  (lambda (xs)
    (cond ((empty? xs) empty)
          ((pair? xs) (make-pair (digits (first xs))
                                 (map-digits (rest xs)))))))

; concat nimmt eine Liste von Listen und macht aus den Elementen (also den einzelnen Listen)
; eine ganze Liste
(: concat ((list-of (list-of natural)) -> (list-of natural)))
(check-expect (concat empty) empty)
(check-expect (concat (list (list 1 2) (list 3 4) (list 5))) (list 1 2 3 4 5))
(define concat
  (lambda (xs)
    (cond ((empty? xs) empty)
          ((pair? xs) (append (first xs)
                              (concat (rest xs)))))))

; luhn-check fuehrt Luhn-Check durch und gibt #t aus, falls der Test bestanden wurde
(: luhn-check (natural -> boolean))
(check-expect (luhn-check 5678) #t)
(check-expect (luhn-check 6789) #f)
(define luhn-check
  (lambda (n)
    (= 0 (modulo (sum (concat (map-digits (double-every-other-number (digits n)))))10))))