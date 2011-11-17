;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname pants-size) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; German pants-sizes
; A number between 22 and 110.
(: german-size? (natural -> boolean))

(check-expect (german-size? 5) #f)
(check-expect (german-size? 110) #t)
(check-expect (german-size? 500) #f)

(define german-size?
  (lambda (size)
    (and (>= size 22)
         (<= size 110))))

(define german-size (signature (predicate german-size?)))

; British pants-sizes
; They consist of:
; - a number between 8-24 (size)
; - one of S, M, L (type)
(: british-size-number? (natural -> boolean))

(check-expect (british-size-number? 7) #f)
(check-expect (british-size-number? 8) #t)
(check-expect (british-size-number? 24) #t)

(define british-size-number?
  (lambda (size)
    (and (>= size 8)
         (<= size 24))))

(define british-size-number
  (signature (predicate british-size-number?)))
(define british-size-attr
  (signature (one-of "S" "M" "L")))

(: make-british-size (british-size-number british-size-attr -> british-size))
(: british-size? (any -> boolean))
(: british-size-size (british-size -> british-size-number))
(: british-size-type (british-size -> british-size-attr))

(check-expect (british-size-type (make-british-size 10 "M")) "M")
(check-expect (british-size-size (make-british-size 10 "M")) 10)
(check-expect (british-size? (make-british-size 10 "M")) #t)

(define-record-procedures british-size
  make-british-size
  british-size?
  (british-size-size
   british-size-type))

; American sizes consists of:
; - waist size in inches (waist)
; - length in inches (length)

(: make-american-size (natural natural -> american-size))
(: american-size? (any -> boolean))
(: american-size-waist (american-size -> natural))
(: american-size-length (american-size -> natural))

(check-expect (american-size? (make-american-size 5 5)) #t)
(check-expect (american-size-waist (make-american-size 10 5)) 10)
(check-expect (american-size-length (make-american-size 10 5)) 5)

(define-record-procedures american-size
  make-american-size
  american-size?
  (american-size-waist
   american-size-length))

; universal pants-sizes
(define pants-size (signature (one-of "short" "medium" "tall")))

; Calculate pants size from German sizes
(: german-size-to-pants-size (german-size -> pants-size))

(check-expect (german-size-to-pants-size 22) "short")
(check-expect (german-size-to-pants-size 60) "medium")
(check-expect (german-size-to-pants-size 110) "tall")

(define german-size-to-pants-size
  (lambda (size)
    (cond ((< size 32) "short")
          ((>= size 64) "tall")
          (else "medium"))))

; Calculate pants size from British sizes
(: british-size-to-pants-size (british-size -> pants-size))

(check-expect (british-size-to-pants-size
               (make-british-size 10 "S")) "short")
(check-expect (british-size-to-pants-size
               (make-british-size 10 "M")) "medium")
(check-expect (british-size-to-pants-size
               (make-british-size 10 "L")) "tall")

(define british-size-to-pants-size
  (lambda (size)
    (cond ((string=? (british-size-type size) "S") "short")
          ((string=? (british-size-type size) "M") "medium")
          ((string=? (british-size-type size) "L") "tall"))))

; Calculate pants size from American sizes
(: american-size-to-pants-size (american-size -> pants-size))

(check-expect (american-size-to-pants-size
               (make-american-size 10 10)) "short")
(check-expect (american-size-to-pants-size
               (make-american-size 10 15)) "medium")
(check-expect (american-size-to-pants-size
               (make-american-size 10 16)) "tall")

(define american-size-to-pants-size
  (lambda (size)
    (cond ((>= (american-size-waist size)
               (american-size-length size))
           "short")
          ((< (abs (- (american-size-waist size)
                  (american-size-length size)))
              6)
           "medium")
          (else "tall"))))

; Calculate pants size by american, british and german size
(: to-pants-size ((mixed american-size german-size british-size) -> pants-size))

(check-expect (to-pants-size
               (make-american-size 10 10)) "short")
(check-expect (to-pants-size
               (make-british-size 10 "L")) "tall")
(check-expect (to-pants-size 110) "tall")

(define to-pants-size
  (lambda (size)
    (cond ((american-size? size)
           (american-size-to-pants-size size))
          ((british-size? size)
           (british-size-to-pants-size size))
          (else
           (german-size-to-pants-size size)))))