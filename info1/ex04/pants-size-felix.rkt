;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-assignments-reader.ss" "deinprogramm")((modname pants-size-felix) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; David Herrmann / Felix Bartusch
; Aufgabe 1a)

; Eine deutsche Hose hat eine Größe zwischen 22 und 100 (ganze zahlen)
(: german-pants? (natural -> boolean))
(check-expect (german-pants? 42) #t)
(check-expect (german-pants? 101) #f)
(define german-pants?
  (lambda (x)
    (and (>= x 22) (<= x 100))))

(define german-pants
  (signature (predicate german-pants?)))

; Eine britische Hosengröße besteht aus
; - einer natürlichen Zahl zwischen 8 und 24
; - und einem Buchstaben S, M oder L

(: british-pants-size-natural? (natural -> boolean))
(check-expect (british-pants-size-natural? 12) #t)
(check-expect (british-pants-size-natural? 5) #f)
(define british-pants-size-natural?
  (lambda (x)
    (and (>= x 8) (<= x 24))))

(define british-pants-size-natural
  (signature (predicate british-pants-size-natural?)))
(define british-pants-size-character
  (signature (one-of "S" "M" "L")))

; Definieren einer britischen Hose
(: make-british-pants (british-pants-size-natural british-pants-size-character -> british-pants))
(: british-pants? (any -> boolean))
(: british-pants-natural (british-pants -> british-pants-size-natural))
(: british-pants-character (british-pants -> british-pants-size-character))

(check-expect (make-british-pants 22 "S") (make-british-pants 22 "S"))
(check-expect (british-pants? (make-british-pants 22 "S")) #t)
(check-expect (british-pants-natural (make-british-pants 22 "S")) 22)
(check-expect (british-pants-character (make-british-pants 22 "S")) "S")

(define-record-procedures british-pants
  make-british-pants
  british-pants?
  (british-pants-natural
   british-pants-character))

; Die Größe einer amerikanischen Hose besteht aus
; - dem Taillenumfang (in Zoll)
; - der Länge (in Zoll)
; Wir nehmen an, dass eine Amerikanische Hose einen Umfang und Länge > 0 hat, sonst wäre sie nicht existent
; Sie können aber unendlich groß werden

(: greater-zero? (real -> boolean))
(check-expect (greater-zero? -10.5) #f)
(check-expect (greater-zero? 22) #t)
(define greater-zero?
  (lambda (x)
    (> x 0)))

(define circumference
  (signature (predicate greater-zero?)))
(define pants-length
  (signature (predicate greater-zero?)))


(: make-american-pants (circumference pants-length -> american-pants))
(: american-pants? (any -> boolean))
(: american-pants-circumference (american-pants -> circumference))
(: american-pants-length (american-pants -> pants-length))

(check-expect (american-pants? (make-american-pants 12 32)) #t)
;(check-expect (american-pants? (make-american-pants -3 32)) #f)
;(check-expect (american-pants? (make-american-pants 12 -5)) #f) geben beide Signaturverletzung zurück
(check-within (american-pants-circumference (make-american-pants 1.337 0.45)) 1.337 0.001)
(check-within (american-pants-length (make-american-pants 1.337 0.45))0.45 0.001)

(define-record-procedures american-pants
  make-american-pants
  american-pants?
  (american-pants-circumference
   american-pants-length))

; b)
; Eine Hose ist entweder "short", "medium" oder "tall"
(define size
  (signature (one-of "short" "medium" "tall")))

; Größenklasse für Deutschland ermitteln
; x < 32 -> small
; 32 <= x < 64 -> medium
; x >= 64 -> tall

(: german-size (german-pants -> size))
(check-expect (german-size 30) "short")
(check-expect (german-size 32) "medium")
(check-expect (german-size 64) "tall")
(define german-size
  (lambda (x)
    (cond ((< x 32) "short")
          ((>= x 64) "tall")
          (else "medium"))))

; Größenklasse für britische Hosen ermitteln
(: british-size (british-pants -> size))
(check-expect (british-size (make-british-pants 10 "S")) "short")
(check-expect (british-size (make-british-pants 16 "M")) "medium")
(check-expect (british-size (make-british-pants 22 "L")) "tall")
(define british-size
  (lambda (british-pants)
    (cond ((string=? (british-pants-character british-pants) "S") "short")
          ((string=? (british-pants-character british-pants) "M") "medium")
          ((string=? (british-pants-character british-pants) "L") "tall"))))

; Größenklasse für amerikanische Hosen ermitteln
(: american-size (american-pants -> size))
(check-expect (american-size (make-american-pants 12 12)) "short")
(check-expect (american-size (make-american-pants 13 15)) "medium")
(check-expect (american-size (make-american-pants 14 20)) "tall")
(define american-size
  (lambda (pants)
    (cond ((>= (american-pants-circumference pants) (american-pants-length pants)) "short")
          ((< (abs (- (american-pants-length pants) (american-pants-circumference pants))) 6) "medium")
          (else "tall"))))

; c)
; pants-size nimmt eine deutsche, britische oder amerikanische hose entgegen und ermittelt die Größenklasse
(: pants-size ((mixed german-pants british-pants american-pants) -> size))
(check-expect (pants-size 65) "tall")
(check-expect (pants-size (make-british-pants 20 "M")) "medium")
(check-expect (pants-size (make-american-pants 14 21)) "tall")
(define pants-size
  (lambda (pants)
    (cond ((american-pants? pants) (american-size pants))
          ((british-pants? pants) (british-size pants))
          ((german-pants? pants) (german-size pants)))))
