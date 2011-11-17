;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname creature-mutation) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; a feature scale
(: scale? (real -> boolean))

(check-expect (scale? 0) #t)
(check-expect (scale? 100) #t)
(check-expect (scale? 101) #f)

(define scale?
  (lambda (num)
    (and
     (<= num 100)
     (>= num 0))))

(define scale (signature
               (predicate scale?)))

; a creature type
(define type (signature (one-of "Garnolaf"
                                "Ronugor"
                                "Tschipotol"
                                "Ronulaf"
                                "Tschigor"
                                "Gapotol"
                                "Tschirgaronu")))

; a creature consists of:
; - a type (type)
; - knowledge (knowledge)
; - willingness (willingness)
; - strength (strength)

(: make-creature (type scale scale scale -> creature))
(: creature? (any -> boolean))

(check-expect (creature? (make-creature "Garnolaf" 0 0 0)) #t)

(define-record-procedures creature
  make-creature
  creature?
  (creature-type
   creature-knowledge
   creature-willingness
   creature-strength))

; Test creatures for their type and create appropriate predicates
(: garnolaf? (creature -> boolean))
(check-expect (garnolaf? (make-creature "Garnolaf" 0 0 0)) #t)
(check-expect (garnolaf? (make-creature "Ronulaf" 0 0 0)) #f)
(define garnolaf?
  (lambda (creat)
    (string=? "Garnolaf" (creature-type creat))))
(define garnolaf (signature (predicate garnolaf?)))

(: ronugor? (creature -> boolean))
(check-expect (ronugor? (make-creature "Garnolaf" 0 0 0)) #f)
(check-expect (ronugor? (make-creature "Ronugor" 0 0 0)) #t)
(define ronugor?
  (lambda (creat)
    (string=? "Ronugor" (creature-type creat))))
(define ronugor (signature (predicate ronugor?)))

(: tschipotol? (creature -> boolean))
(check-expect (tschipotol? (make-creature "Tschipotol" 0 0 0)) #t)
(check-expect (tschipotol? (make-creature "Ronulaf" 0 0 0)) #f)
(define tschipotol?
  (lambda (creat)
    (string=? "Tschipotol" (creature-type creat))))
(define tschipotol (signature (predicate tschipotol?)))

; mix Garnolaf and Ronugor to Ronulaf
(: mix-garnolaf-ronugor (garnolaf ronugor -> creature))

(check-within (mix-garnolaf-ronugor
               (make-creature "Garnolaf" 0 0 20)
               (make-creature "Ronugor" 50 0 0))
              (make-creature "Ronulaf" 50 0 21)
              0.001)

(define mix-garnolaf-ronugor
  (lambda (gar ron)
    (make-creature "Ronulaf"
                   (creature-knowledge ron)
                   0
                   (min 100 (* 1.05 (creature-strength gar))))))

; mix Garnolaf and Tschipotol to Gapotol
(: mix-garnolaf-tschipotol (garnolaf tschipotol -> creature))

(check-within (mix-garnolaf-tschipotol
               (make-creature "Garnolaf" 0 0 100)
               (make-creature "Tschipotol" 0 20 0))
              (make-creature "Gapotol" 0 19 100)
              0.001)

(define mix-garnolaf-tschipotol
  (lambda (gar tsch)
    (make-creature "Gapotol"
                   0
                   (* 0.95 (creature-willingness tsch))
                   (min 100 (* 1.08 (creature-strength gar))))))

; mix Ronugor and Tschipotol to Tschigor
(: mix-ronugor-tschipotol (ronugor tschipotol -> creature))

(check-within (mix-ronugor-tschipotol
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Tschipotol" 0 20 0))
              (make-creature "Tschigor" 45 20 0)
              0.001)

(define mix-ronugor-tschipotol
  (lambda (ron tsch)
    (make-creature "Tschigor"
                   (* 0.9 (creature-knowledge ron))
                   (creature-willingness tsch)
                   0)))

; mix two creatures of same type
(: mix-same-type (creature creature -> creature))

(check-within (mix-same-type
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Ronugor" 50 0 0))
              (make-creature "Ronugor" 200/3 0 0)
              0.001)

(define mix-same-type
  (lambda (c1 c2)
    (make-creature (creature-type c1)
                   (* 2/3 (+ (creature-knowledge c1)
                             (creature-knowledge c2)))
                   (* 2/3 (+ (creature-willingness c1)
                             (creature-willingness c2)))
                   (* 2/3 (+ (creature-strength c1)
                             (creature-strength c2))))))

; Mix all three creatures
(: mix-all (garnolaf ronugor tschipotol -> creature))

(check-within (mix-all
               (make-creature "Garnolaf" 0 0 100)
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Tschipotol" 0 10 0))
              (make-creature "Tschirgaronu" 48.5 9.7 97)
              0.001)

(define mix-all
  (lambda (gar ron tsch)
    (make-creature "Tschirgaronu"
                   (* 0.97 (creature-knowledge ron))
                   (* 0.97 (creature-willingness tsch))
                   (* 0.97 (creature-strength gar)))))

; mix two arbitrary creatures
(: mix2 (creature creature -> creature))

(check-within (mix2
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Ronugor" 50 0 0))
              (make-creature "Ronugor" 200/3 0 0)
              0.001)

(check-within (mix2
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Garnolaf" 0 0 20))
              (make-creature "Ronulaf" 50 0 21)
              0.001)

(check-within (mix2
               (make-creature "Garnolaf" 0 0 20)
               (make-creature "Ronugor" 50 0 0))
              (make-creature "Ronulaf" 50 0 21)
              0.001)

(check-within (mix2
               (make-creature "Garnolaf" 0 0 100)
               (make-creature "Tschipotol" 0 20 0))
              (make-creature "Gapotol" 0 19 100)
              0.001)

(check-within (mix2
               (make-creature "Tschipotol" 0 20 0)
               (make-creature "Garnolaf" 0 0 100))
              (make-creature "Gapotol" 0 19 100)
              0.001)

(check-within (mix2
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Tschipotol" 0 20 0))
              (make-creature "Tschigor" 45 20 0)
              0.001)

(check-within (mix2
               (make-creature "Tschipotol" 0 20 0)
               (make-creature "Ronugor" 50 0 0))
              (make-creature "Tschigor" 45 20 0)
              0.001)

(define mix2
  (lambda (c1 c2)
    (cond ((string=? (creature-type c1) (creature-type c2))
           (mix-same-type c1 c2))
          ((string=? "Garnolaf" (creature-type c1))
           (if (string=? "Ronugor" (creature-type c2))
               (mix-garnolaf-ronugor c1 c2)
               (mix-garnolaf-tschipotol c1 c2)))
          ((string=? "Garnolaf" (creature-type c2))
           (mix2 c2 c1))
          ((string=? "Ronugor" (creature-type c1))
           (mix-ronugor-tschipotol c1 c2))
          ((string=? "Ronugor" (creature-type c2))
           (mix2 c2 c1)))))

; mix three creatures
(: mix3 (creature creature creature -> creature))

(check-within (mix3
               (make-creature "Garnolaf" 0 0 100)
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Tschipotol" 0 10 0))
              (make-creature "Tschirgaronu" 48.5 9.7 97)
              0.001)

(check-within (mix3
               (make-creature "Tschipotol" 0 20 0)
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Tschipotol" 0 10 0))
              (make-creature "Tschigor" 45 20 0)
              0.001)

(check-within (mix3
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Ronugor" 50 0 0))
              (make-creature "Ronugor" 77.7777 0 0)
              0.001)

(check-within (mix3
               (make-creature "Ronugor" 50 0 0)
               (make-creature "Tschipotol" 0 20 0)
               (make-creature "Tschipotol" 0 10 0))
              (make-creature "Tschigor" 45 20 0)
              0.001)

(define mix3
  (lambda (c1 c2 c3)
    (cond ((string=? (creature-type c1) (creature-type c2))
           (mix2 (mix2 c1 c2) c3))
          ((string=? (creature-type c1) (creature-type c3))
           (mix2 (mix2 c1 c3) c2))
          ((string=? (creature-type c2) (creature-type c3))
           (mix2 (mix2 c2 c3) c1))
          (else
           (mix-all c1 c2 c3)))))