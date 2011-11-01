;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname heiner-or) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; Felix Bartusch / David Herrmann

; heiner-or definition
(define heiner-or
  (lambda (test-1 test-2)
    (if test-1
        #t
        test-2)))

; some test-cases
(check-expect (heiner-or #f #f) #f)
(check-expect (heiner-or #f #t) #t)
(check-expect (heiner-or #t #t) #t)
(check-expect (heiner-or #t #f) #t)

(check-expect (or #f #f) #f)
(check-expect (or #f #t) #t)
(check-expect (or #t #t) #t)
(check-expect (or #t #f) #t)

; differences bewteen (or) and (heiner-or)
; This test should fail as (or) expects boolean values,
; however, heiner-or succeeds here as there is no signature.
(check-error (or #f 5))
(check-expect (heiner-or #f 5) 5)

; When a signature for heiner-or is added, the following
; heiner-or will throw a signature exception, while the special-form
; of (or) will run fine:
;
; (: heiner-or (boolean boolean -> boolean))
; (check-expect (heiner-or #t 5) #t)
; (check-expect (or #t 5) #t)
;
; This only works in DMdA-mode not in other drracket-modes.

; Beweis:
; (or xy) akzeptiert nur boolesche Werte und zeigt folglich einen Fehler
; für Parameter 2 von (or #f 5).
; Or zeigt aber keinen Fehler für (or #t 5) an, weil es das zweite Argument
; erst auswertet, wenn das erste #f ist. Dies trifft für (heiner-or) nicht zu.
;
; Aber:
; (heiner-or #f 5) wird reduziert zu:
; => ((lambda (test-1 test-2)
;       (if test-1
;           #t
;           test-2))) #f 5)
; => ((if #f
;         #t
;         5))
; => (5)
; => 5
;
; heiner-or liefert also bei (or #f 5) einfach 5 zurück, anstatt einen Fehler
; anzuzeigen. Wenn die Signaturprüfung angewand wird, liefern
; (heiner-or #t 5) und (heiner-or #f 5) beide eine SIgnaturverletzung, obwohl
; nur letztere fehlschlagen sollte. Aus dem selben Grund wie bei a).

; Ein anderer Unterschied ist, dass (or) das zweite Argument erst
; auswertet, wenn das erste #false ist. Bei nicht-Sonderformen wie
; (heiner-or x y) werden aber ERST die Argumente ausgewertet, dann
; die lambda-Abstraktion ausgeführt.
; Da die Aufgabe aber nur einen Unterschied erwartet, muss das hier
; nicht bewiesen werden.