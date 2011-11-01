;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname creditcard) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; Felix Bartusch / David Herrmann

; Return card-type by revenue
(: card-type (real -> (one-of "Plastik" "Gold" "Platin")))

(check-expect (card-type -1000) "Plastik")
(check-expect (card-type 0) "Plastik")
(check-expect (card-type 20000) "Plastik")
(check-expect (card-type 150000) "Gold")
(check-expect (card-type 200000) "Gold")
(check-expect (card-type 250000) "Platin")

(define card-type
  (lambda (revenue)
    (cond ((<= revenue 20000) "Plastik")
          ((<= revenue 200000) "Gold")
          (else "Platin"))))

; Returns true if the customer is a long-time customer
; Long time customers have the credit card for *over* 10 years.
(: long-time-customer? (natural -> boolean))

(check-expect (long-time-customer? 0) #f)
(check-expect (long-time-customer? 10) #f)
(check-expect (long-time-customer? 11) #t)

; "über 10 jahre" => (> x 10) not (>= x 10)
(define long-time-customer?
  (lambda (years)
    (> years 10)))

; Returns the refund a customer gets depending on his card-type
; and on the timespan that he was customer.
(: calc-refund (real natural -> real))

(check-expect (calc-refund 1000000 15) 100000)
(check-expect (calc-refund -100 1) 10)
(check-expect (calc-refund 30000 1) 900)
(check-expect (calc-refund 25000 20) 1250)

(define calc-refund
  (lambda (revenue years)
    (cond ((string=? (card-type revenue)
                     "Plastik")
           10)
          ((string=? (card-type revenue)
                     "Platin")
           (/ revenue 10))
          ((long-time-customer? years)
           (/ revenue 20))
          (else
           (* (/ revenue 100) 3)))))