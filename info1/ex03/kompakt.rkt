;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname kompakt) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; Original
(: less-zero? (number -> boolean))
(define less-zero?
  (lambda (x)
    (if (not (< x 0))
        #f
        #t)))

; Improved
(: less-zero?* (number -> boolean))
(define less-zero?*
  (lambda (x)
    (< x 0)))

(check-property
 (for-all
     ((n number))
   (boolean=? (less-zero? n) (less-zero?* n))))

; Original
(: f (number -> boolean))
(define f
  ((lambda (x) x)
   (lambda (y)
     (cond
       ((> y 11) #t)
       ((< y 11) #f)
       ((= y 11) #t)))))

(: f* (number -> boolean))
(define f*
  (lambda (y)
    (>= y 11)))

(check-property
 (for-all
     ((n number))
   (boolean=? (f n) (f* n))))

; Original
(: g (boolean boolean -> boolean))
(define g
  (lambda (a b)
    (or (not b)
        (and a (not a)))))

; Improved
(: g* (boolean boolean -> boolean))
(define g*
  (lambda (a b)
    (not b)))

(check-property
 (for-all
     ((a boolean)
      (b boolean))
   (boolean=? (g a b) (g* a b))))

; Original
(: greater-equal-zero? (number -> boolean))
(define greater-equal-zero?
  (lambda (x)
    (cond
      ((>= x 0) #t)
      (else #f))))

; Improved
(: greater-equal-zero?* (number -> boolean))
(define greater-equal-zero?*
  (lambda (x)
    (>= x 0)))

(check-property
 (for-all
     ((n number))
   (boolean=? (greater-equal-zero? n) (greater-equal-zero?* n))))