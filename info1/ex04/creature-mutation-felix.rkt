;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname creature-mutation) (read-case-sensitive #f) (teachpacks ((lib "image2.ss" "teachpack" "deinprogramm"))) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ((lib "image2.ss" "teachpack" "deinprogramm")))))
; David Herrmann / Felix Bartusch
; Aufgabe 2 a)

; Ist x innerhalb der Knaubichler-Skala?
(: skala? (real -> boolean))
(check-expect (skala? 0) #t)
(check-expect (skala? 100) #t)
(check-expect (skala? 101) #f)
(define skala?
  (lambda (x)
    (and (>= x 0)
         (<= x 100))))

; Definieren der Signatur "skala"
(define skala
  (signature (predicate skala?)))

; Ein Garnolaf besitzt
; - Staerke
(: make-garnolaf (skala -> garnolaf))
(: garnolaf? (any -> boolean))
(check-expect (garnolaf? (make-garnolaf 50)) #t)
(define-record-procedures garnolaf
  make-garnolaf
  garnolaf?
  (garnolaf-staerke))

; Ein Ronugor bestehe aus
; - Wissen
(: make-ronugor (skala -> ronugor))
(: ronugor? (any -> boolean))
(check-expect (ronugor? (make-ronugor 50)) #t)
(define-record-procedures ronugor
  make-ronugor
  ronugor?
  (ronugor-wissen))

; Ein Tschipotol besteht aus
; - Risikobereitschaft
(: make-tschipotol (skala -> tschipotol))
(: tschipotol? (any -> boolean))
(check-expect (tschipotol? (make-tschipotol 50)) #t)
(define-record-procedures tschipotol
  make-tschipotol
  tschipotol?
  (tschipotol-risikobereitschaft))

; Ein Ronulaf besteht aus
; - Wissen
; - Staerke
(: make-ronulaf (skala skala -> ronulaf))
(: ronulaf? (any -> boolean))
(check-expect (ronulaf? (make-ronulaf 50 50)) #t)
(define-record-procedures ronulaf
  make-ronulaf
  ronulaf?
  (ronulaf-wissen
   ronulaf-staerke))

; Ein Tschigor besteht aus
; - Wissen
; - Risikobereitschaft
(: make-tschigor (skala skala -> tschigor))
(: tschigor? (any -> boolean))
(check-expect (tschigor? (make-tschigor 50 50)) #t)
(define-record-procedures tschigor
  make-tschigor
  tschigor?
  (tschigor-wissen
   tschigor-risikobereitschaft))

; Ein Gapotol bestehe aus
; - Staerke
; - Risikobereitschaft
(: make-gapotol (skala skala -> gapotol))
(: gapotol? (any -> boolean))
(check-expect (gapotol? (make-gapotol 50 50)) #t)
(define-record-procedures gapotol
  make-gapotol
  gapotol?
  (gapotol-staerke
   gapotol-risikobereitschaft))

; Ein Tschirgaronu besteht aus
; - Wissen
; - Staerke
; - Risikobereitschaft
(: make-tschirgaronu (skala skala skala -> tschirgaronu))
(: tschirgaronu? (any -> boolean))
(check-expect (tschirgaronu? (make-tschirgaronu 50 50 50)) #t)
(define-record-procedures tschirgaronu
  make-tschirgaronu
  tschirgaronu?
  (tschirgaronu-wissen
   tschirgaronu-staerke
   tschirgaronu-risikobereitschaft))

; b)
; Erschaffte einen Ronulaf durch Kreuzung eines Ronugors mit einem Garnolaf
(: erschaffe-ronulaf (ronugor garnolaf -> ronulaf))
(check-within (erschaffe-ronulaf (make-ronugor 50) (make-garnolaf 20)) (make-ronulaf 50 21) 0.001)
(check-within (erschaffe-ronulaf (make-ronugor 50) (make-garnolaf 99)) (make-ronulaf 50 100) 0.001)
(define erschaffe-ronulaf
  (lambda (ronugor garnolaf)
    (make-ronulaf (ronugor-wissen ronugor)
                  (min (* 1.05 (garnolaf-staerke garnolaf)) 100))))

; Erschaffe einen Tschigor durch Kreuzung eines Ronugors mit einem Tschipotol
(: erschaffe-tschigor (ronugor tschipotol -> tschigor))
(check-within (erschaffe-tschigor (make-ronugor 50) (make-tschipotol 20)) (make-tschigor 45 20) 0.001)
(define erschaffe-tschigor
  (lambda (ronugor tschipotol)
    (make-tschigor (* 0.9 (ronugor-wissen ronugor))
                   (tschipotol-risikobereitschaft tschipotol))))

; Erschaffe einen Gapotol durch Kreuzung eines Garnolafs mit einem Tschipotol
(: erschaffe-gapotol (garnolaf tschipotol -> gapotol))
(check-within (erschaffe-gapotol (make-garnolaf 50) (make-tschipotol 50)) (make-gapotol 54 47.5) 0.001)
(check-within (erschaffe-gapotol (make-garnolaf 99) (make-tschipotol 0)) (make-gapotol 100 0) 0.001)
(define erschaffe-gapotol
  (lambda (garnolaf tschipotol)
    (make-gapotol (min (* 1.08 (garnolaf-staerke garnolaf)) 100)
                  (* 0.95 (tschipotol-risikobereitschaft tschipotol)))))

; Erschaffe einen Tschirgaronu durch Kreuzung eines Ronugors, Garnolafs und eines Tschipotols
(: erschaffe-tschirgaronu (ronugor garnolaf tschipotol -> tschirgaronu))
(check-within (erschaffe-tschirgaronu (make-ronugor 100) (make-garnolaf 100) (make-tschipotol 100)) (make-tschirgaronu 97 97 97) 0.001)
(define erschaffe-tschirgaronu
  (lambda (ronugor garnolaf tschipotol)
    (make-tschirgaronu (* 0.97 (ronugor-wissen ronugor))
                       (* 0.97 (garnolaf-staerke garnolaf))
                       (* 0.97 (tschipotol-risikobereitschaft tschipotol)))))

; Erschaffe einen Garnolaf durch Kreuzung zweier Garnolafs
(: erschaffe-garnolaf (garnolaf garnolaf -> garnolaf))
(check-within (erschaffe-garnolaf (make-garnolaf 50) (make-garnolaf 50)) (make-garnolaf 66.66) 0.01)
(check-within (erschaffe-garnolaf (make-garnolaf 80) (make-garnolaf 80)) (make-garnolaf 100) 0.01)
(define erschaffe-garnolaf
  (lambda (g1 g2)
    (make-garnolaf (min 100
                        (* 2/3
                           (+ (garnolaf-staerke g1)
                              (garnolaf-staerke g2)))))))

; Erschaffe einen Ronugor durch Kreuzung zweier Ronugors
(: erschaffe-ronugor (ronugor ronugor -> ronugor))
(check-within (erschaffe-ronugor (make-ronugor 50) (make-ronugor 50)) (make-ronugor 66.66) 0.01)
(check-within (erschaffe-ronugor (make-ronugor 80) (make-ronugor 80)) (make-ronugor 100) 0.01)
(define erschaffe-ronugor
  (lambda (r1 r2)
    (make-ronugor (min 100
                        (* 2/3
                           (+ (ronugor-wissen r1)
                              (ronugor-wissen r2)))))))

; Erschaffe einen Tschipotol durch Kreuzung zweier Tschipotols
(: erschaffe-tschipotol (tschipotol tschipotol -> tschipotol))
(check-within (erschaffe-tschipotol (make-tschipotol 50) (make-tschipotol 50)) (make-tschipotol 66.66) 0.01)
(check-within (erschaffe-tschipotol (make-tschipotol 80) (make-tschipotol 80)) (make-tschipotol 100) 0.01)
(define erschaffe-tschipotol
  (lambda (t1 t2)
    (make-tschipotol (min 100
                        (* 2/3
                           (+ (tschipotol-risikobereitschaft t1)
                              (tschipotol-risikobereitschaft t2)))))))

; c)
; kreuze-2 kreuzt 2 beliebe Grundkreaturen

(define grundkreatur
  (signature (mixed garnolaf ronugor tschipotol)))
(define kreuzung
  (signature (mixed grundkreatur ronulaf tschigor gapotol tschirgaronu)))

(: kreuze-2 (grundkreatur grundkreatur -> kreuzung))
(check-within (kreuze-2 (make-garnolaf 50) (make-garnolaf 50)) (make-garnolaf 66.66) 0.01)
(check-within (kreuze-2 (make-ronugor 50) (make-ronugor 50)) (make-ronugor 66.66) 0.01)
(check-within (kreuze-2 (make-tschipotol 50) (make-tschipotol 50)) (make-tschipotol 66.66) 0.01)
(check-within (kreuze-2 (make-ronugor 50) (make-garnolaf 20)) (make-ronulaf 50 21) 0.001)
(check-within (kreuze-2 (make-garnolaf 20) (make-ronugor 50)) (make-ronulaf 50 21) 0.001)
(check-within (kreuze-2 (make-ronugor 50) (make-tschipotol 20)) (make-tschigor 45 20) 0.001)
(check-within (kreuze-2 (make-tschipotol 20) (make-ronugor 50)) (make-tschigor 45 20) 0.001)
(check-within (kreuze-2 (make-garnolaf 50) (make-tschipotol 50)) (make-gapotol 54 47.5) 0.001)
(check-within (kreuze-2 (make-tschipotol 50) (make-garnolaf 50)) (make-gapotol 54 47.5) 0.001)
(define kreuze-2
  (lambda (gr1 gr2)
    (cond ((and (garnolaf? gr1)
                (garnolaf? gr2)) (erschaffe-garnolaf gr1 gr2))
          ((and (ronugor? gr1)
                (ronugor? gr2)) (erschaffe-ronugor gr1 gr2))
          ((and (tschipotol? gr1)
                (tschipotol? gr2)) (erschaffe-tschipotol gr1 gr2))
          ((and (ronugor? gr1)
                (garnolaf? gr2)) (erschaffe-ronulaf gr1 gr2))
          ((and (ronugor? gr2)
                (garnolaf? gr1)) (erschaffe-ronulaf gr2 gr1))
          ((and (ronugor? gr1)
                (tschipotol? gr2)) (erschaffe-tschigor gr1 gr2))
          ((and (ronugor? gr2)
                (tschipotol? gr1)) (erschaffe-tschigor gr2 gr1))
          ((and (garnolaf? gr1)
                (tschipotol? gr2)) (erschaffe-gapotol gr1 gr2))
          ((and (garnolaf? gr2)
                (tschipotol? gr1)) (erschaffe-gapotol gr2 gr1)))))

; d)
; kreuze-3 kreuzt 3 beliebige Grundkreaturen
(: kreuze-3 (grundkreatur grundkreatur grundkreatur -> kreuzung))
(check-within (kreuze-3 (make-ronugor 100) (make-garnolaf 100) (make-tschipotol 100)) (make-tschirgaronu 97 97 97) 0.001)
(check-within (kreuze-3 (make-ronugor 100) (make-tschipotol 100) (make-garnolaf 100)) (make-tschirgaronu 97 97 97) 0.001)
(check-within (kreuze-3 (make-garnolaf 100) (make-ronugor 100) (make-tschipotol 100)) (make-tschirgaronu 97 97 97) 0.001)
(check-within (kreuze-3 (make-tschipotol 100) (make-ronugor 100) (make-garnolaf 100)) (make-tschirgaronu 97 97 97) 0.001)
(check-within (kreuze-3 (make-garnolaf 100) (make-tschipotol 100) (make-ronugor 100)) (make-tschirgaronu 97 97 97) 0.001)
(check-within (kreuze-3 (make-tschipotol 100) (make-garnolaf 100) (make-ronugor 100)) (make-tschirgaronu 97 97 97) 0.001)
(check-within (kreuze-3 (make-garnolaf 100) (make-garnolaf 100) (make-ronugor 100)) (make-ronulaf 100 100) 0.001)
(check-within (kreuze-3 (make-ronugor 100) (make-garnolaf 100) (make-garnolaf 100)) (make-ronulaf 100 100) 0.001)
(check-within (kreuze-3 (make-garnolaf 100) (make-ronugor 100) (make-garnolaf 100)) (make-ronulaf 100 100) 0.001)
(check-within (kreuze-3 (make-ronugor 100) (make-ronugor 100) (make-garnolaf 100)) (make-ronulaf 100 100) 0.001)
(check-within (kreuze-3 (make-garnolaf 100) (make-ronugor 100) (make-ronugor 100)) (make-ronulaf 100 100) 0.001)
(check-within (kreuze-3 (make-ronugor 100) (make-garnolaf 100) (make-ronugor 100)) (make-ronulaf 100 100) 0.001)
(check-within (kreuze-3 (make-tschipotol 100) (make-tschipotol 100) (make-ronugor 100)) (make-tschigor 90 100) 0.001)
(check-within (kreuze-3 (make-ronugor 100) (make-tschipotol 100) (make-tschipotol 100)) (make-tschigor 90 100) 0.001)
(check-within (kreuze-3 (make-tschipotol 100) (make-ronugor 100) (make-tschipotol 100)) (make-tschigor 90 100) 0.001)

(define kreuze-3
  (lambda (gr1 gr2 gr3)
    (cond ((and (ronugor? gr1) (garnolaf? gr2) (tschipotol? gr3)) (erschaffe-tschirgaronu gr1 gr2 gr3))
          ((and (ronugor? gr1) (garnolaf? gr3) (tschipotol? gr2)) (erschaffe-tschirgaronu gr1 gr3 gr2))
          ((and (ronugor? gr2) (garnolaf? gr1) (tschipotol? gr3)) (erschaffe-tschirgaronu gr2 gr1 gr3))
          ((and (ronugor? gr2) (garnolaf? gr3) (tschipotol? gr1)) (erschaffe-tschirgaronu gr2 gr3 gr1))
          ((and (ronugor? gr3) (garnolaf? gr1) (tschipotol? gr2)) (erschaffe-tschirgaronu gr3 gr1 gr2))
          ((and (ronugor? gr3) (garnolaf? gr2) (tschipotol? gr1)) (erschaffe-tschirgaronu gr3 gr2 gr1))
          ((and (garnolaf? gr1) (garnolaf? gr2)) (kreuze-2 gr3 (kreuze-2 gr1 gr2)))
          ((and (garnolaf? gr2) (garnolaf? gr3)) (kreuze-2 gr1 (kreuze-2 gr2 gr3)))
          ((and (garnolaf? gr3) (garnolaf? gr1)) (kreuze-2 gr2 (kreuze-2 gr1 gr3)))
          ((and (ronugor? gr1) (ronugor? gr2)) (kreuze-2 gr3 (kreuze-2 gr1 gr2)))
          ((and (ronugor? gr2) (ronugor? gr3)) (kreuze-2 gr1 (kreuze-2 gr2 gr3)))
          ((and (ronugor? gr3) (ronugor? gr1)) (kreuze-2 gr2 (kreuze-2 gr1 gr3)))
          ((and (tschipotol? gr1) (tschipotol? gr2)) (kreuze-2 gr3 (kreuze-2 gr1 gr2)))
          ((and (tschipotol? gr2) (tschipotol? gr3)) (kreuze-2 gr1 (kreuze-2 gr2 gr3)))
          ((and (tschipotol? gr3) (tschipotol? gr1)) (kreuze-2 gr2 (kreuze-2 gr1 gr3))))))

