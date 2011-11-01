;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname clamp) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; Return smaller number of both given numbers
(: minimum (real real -> real))

(check-within (minimum 10 5) 5 0.001)
(check-within (minimum 0.1 0.1) 0.1 0.001)
(check-within (minimum -10 0.00001) -10 0.001)

(define minimum
  (lambda (n1 n2)
    (if (< n1 n2)
        n1
        n2)))

; Return greater number of both given numbers
(: maximum (real real -> real))

(check-within (maximum 10 5) 10 0.001)
(check-within (maximum 0.1 0.1) 0.1 0.001)
(check-within (maximum -10 0.00001) 0.00001 0.001)

(define maximum
  (lambda (n1 n2)
    (if (> n1 n2)
        n1
        n2)))

; Clamp value into the given range
; This function reorders arguments 2 and 3 if argument 3 is smaller than argument 2.
(: clamp (real real real -> real))

(check-within (clamp 5 0 10) 5 0.001)
(check-within (clamp -100 -0.001 0.001) -0.001 0.001)
(check-within (clamp 10 0 5) 5 0.001)
(check-within (clamp 10 5 0) 5 0.001)
(check-within (clamp -100 0.001 -0.001) -0.001 0.001)
(check-within (clamp 10 0 -5) 0 0.001)

(define clamp
  (lambda (x untergrenze obergrenze)
    (maximum (minimum untergrenze
                      obergrenze)
             (minimum (maximum obergrenze
                               untergrenze)
                      x))))