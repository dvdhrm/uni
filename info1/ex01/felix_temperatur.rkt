;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname temperatur) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
;a)
;Kurzbeschreibung: Prozedur celsius->fahrenheit rechnet eine Temperatur
;von Celsius in Fahrenheit um
(: celsius->fahrenheit (real -> real))
(check-within (celsius->fahrenheit 0) 32 0.0001)
(check-within (celsius->fahrenheit -160/9) 0 0.0001)
(check-within (celsius->fahrenheit 40) 104 0.001)
(define celsius->fahrenheit
  (lambda(celsius)
    (+ 32
       (* 9/5 celsius))))

;b)
;Prozedur fahrenheit->celsius rechnet eine Temperatur der Einheit Fahrenheit
;in die Einheit Celsius um
;Umstellen der Gleichung aus a) ergibt C=(F-32)*5/9
(: fahrenheit->celsius (real -> real))
(check-within (fahrenheit->celsius 32) 0 0.0001)
(check-within (fahrenheit->celsius 0) -160/9 0.0001)
(check-within (fahrenheit->celsius 104) 40 0.0001)
(define fahrenheit->celsius
  (lambda(fahrenheit)
    (* 5/9
       (- fahrenheit 32))))