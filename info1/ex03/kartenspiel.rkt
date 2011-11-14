;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname kartenspiel) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; The color of a card is one of "Kreuz" "Pik" "Herz" Karo"
(define card-tcolor (signature (one-of "Kreuz" "Pik" "Herz" "Karo")))

; The value of a card is one of:
(define card-tvalue (signature (one-of "Sieben" "Acht" "Neun" "Zehn" "Bube" "Dame" "König" "Ass")))

; A card consists of:
; - color of card (color)
; - value of card (value)

(: make-card (card-tcolor card-tvalue -> card))
(: card? (any -> boolean))
(: card-color (card -> card-tcolor))
(: card-value (card -> card-tvalue))

(define-record-procedures card
  make-card
  card?
  (card-color
   card-value))

(check-expect (card-color (make-card "Kreuz" "Acht")) "Kreuz")
(check-expect (card-value (make-card "Kreuz" "Acht")) "Acht")

; Performs operation kartenop1 on two cards
(: kartenop1 (card card -> boolean))

; may use one of: (card-color,card-value,card? ...)
(define kartenop1
  (lambda (a b)
    (... a ... b ...)))

; Performs operation kartenop2
(: kartenop2 (natural string -> card))

(define kartenop2
  (lambda (num str)
    (... (make-card ... num ... str ...) ...)))

; Performs operation kartenop3
(: kartenop3 ((one-of 1 2 3 4) -> card))

(define kartenop3
  (lambda (num)
    (... (make-card ... num ...) ...)))