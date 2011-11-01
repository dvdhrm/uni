;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname fussball) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; Felix Bartusch / David Herrmann

; Return true if shirt number is valid for keepers
(: is-keeper (number -> boolean))

(check-expect (is-keeper 1) #t)
(check-expect (is-keeper 0) #f)

(define is-keeper
  (lambda (number)
    (= number 1)))

; Return true if shirt number is valid for defense
(: is-defense (number -> boolean))

(check-expect (is-defense 5) #t)
(check-expect (is-defense 1) #f)

(define is-defense
  (lambda (number)
    (and (>= number 2)
         (<= number 5))))

; Return true if shirt number is valid for midfield players
(: is-midfield (number -> boolean))

(check-expect (is-midfield 6) #t)
(check-expect (is-midfield 10) #t)
(check-expect (is-midfield 9) #f)

(define is-midfield
  (lambda (number)
    (and (>= number 6)
         (<= number 10)
         (not (= number 9)))))

; Return true if shirt number is valid for strikers
(: is-striker (number -> boolean))

(check-expect (is-striker 9) #t)
(check-expect (is-striker 11) #t)
(check-expect (is-striker 10) #f)

(define is-striker
  (lambda (number)
    (or (= number 9)
        (= number 11))))

; Return true if shirt number is valid for substitutes
(: is-substitute (number -> boolean))

(check-expect (is-substitute 12) #t)
(check-expect (is-substitute 100) #f)

(define is-substitute
  (lambda (number)
    (and (>= number 12)
         (<= number 99))))

; Return position of player by given shirt number
(: number->position
   (number -> (one-of "Torwart"
                      "Abwehr"
                      "Mittelfeld"
                      "Sturm"
                      "Ersatz"
                      "Ungültig")))

(check-expect (number->position 1) "Torwart")
(check-expect (number->position 2) "Abwehr")
(check-expect (number->position 6) "Mittelfeld")
(check-expect (number->position 9) "Sturm")
(check-expect (number->position 12) "Ersatz")
(check-expect (number->position 0) "Ungültig")

(define number->position
  (lambda (number)
    (cond ((is-keeper number) "Torwart")
          ((is-defense number) "Abwehr")
          ((is-midfield number) "Mittelfeld")
          ((is-striker number) "Sturm")
          ((is-substitute number) "Ersatz")
          (else "Ungültig"))))