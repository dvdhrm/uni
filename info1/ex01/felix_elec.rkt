;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname electricity) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
;Prozedur billig-strom berechnet aus kwh
;den monatlichen Rechnungsbetrag
(: billig-strom (natural -> real))
(check-within (billig-strom 0) 4.90 0.0001)
(check-within (billig-strom 10) 6.80 0.00001)
(define billig-strom 
  (lambda(kwh)
    (+ 4.90 (* kwh 0.19))))

;Prozedur watt-für-wenig berechnet den monatlichen Rechnungsbeitrag anhand
;der verbrauchten kwh
(: watt-für-wenig (natural -> real))
(check-within (watt-für-wenig 0) 8.2 0.0001)
(check-within (watt-für-wenig 10) 9.8 0.0001)
(define watt-für-wenig
  (lambda(kwh)
    (+ 8.20 (* kwh 0.16))))