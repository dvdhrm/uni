;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname fuel) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
;a) Prozedur liters-per-hundred-kilometers berechnet aus dem Volumen
;des eingesetzten Benzins und der Fahrstrecke den Verbrauch in l/100km
;(kilometer ungleich 0))
(: liters-per-hundred-kilometers (natural natural -> rational))
(check-expect (liters-per-hundred-kilometers 1 100) 1)
(check-expect (liters-per-hundred-kilometers 0 9999999) 0)
(check-expect (liters-per-hundred-kilometers 40 800) 5)
(define liters-per-hundred-kilometers
  (lambda (liter kilometer)
    (/ liter
       (/ kilometer 100))))

;b) Berechnet aus den gefahrenen Meilen und dem eingesetzten Benzin in Gallonen
;den Verbrauch in Meilen pro Gallone
(: miles-per-gallon (natural natural -> rational))
(check-expect (miles-per-gallon 100 1) 100)
(check-expect (miles-per-gallon 0 999) 0)
(check-expect (miles-per-gallon 300 30) 10)
(define miles-per-gallon
  (lambda (miles gallon)
    (/ miles gallon)))

;c)
;Definiere Kilometer/Meile und Meile/Kilometer
(define kilometers-per-mile 1.61)

;Umrechnung von Kilometern in Meilen
(: kilometers->miles (real -> real))
(check-within (kilometers->miles 1) 0.62111 0.00001)
(check-within (kilometers->miles 0) 0 0.00001)
(check-within (kilometers->miles 1.61) 1 0.00001)
(define kilometers->miles
  (lambda (kilometers)
    (/ kilometers kilometers-per-mile)))

;Umrechnung von Meilen in Kilometer
(: miles->kilometers (real -> real))
(check-within (miles->kilometers 1) 1.61 0.00001)
(check-within (miles->kilometers 0) 0 0.000001)
(check-within (miles->kilometers 0.62111) 1 0.0001)
(define miles->kilometers
  (lambda (miles)
    (* miles kilometers-per-mile)))

;d) Verhältnis Liter/Gallone
(define liters-per-gallon 3.79)

;Umrechnen von Liter zu Gallone
(: liters->gallons (real -> real))
(check-within (liters->gallons 0) 0 0.0001)
(check-within (liters->gallons 3.79) 1 0.0001)
(check-within (liters->gallons 35) 9.2348 0.001)
(define liters->gallons
  (lambda (liters)
    (/ liters liters-per-gallon)))

;Umrechnen von Gallonen zu Litern
(: gallons->liters (real -> real))
(check-within (gallons->liters 1) 3.79 0.0001)
(check-within (gallons->liters 0) 0 0.0001)
(check-within (gallons->liters 42) 159.18 0.0001)
(define gallons->liters
  (lambda (gallon)
    (* gallon liters-per-gallon)))

;e) Berechnen des Umrechnungsfaktors für l/100km->mi/gal;
(define umrechnungsfaktor
  (/ (kilometers->miles 100) (liters->gallons 1)))

;Prozedur der Umrechnung l/100km in mi/gal
(: l/100km->mi/gal (real -> real)) 
(check-within (l/100km->mi/gal 10) 23.54037 0.0001) 
(check-within (l/100km->mi/gal 42) 5.60485 0.0001)
(define l/100km->mi/gal
  (lambda (l/100km)
    (/ umrechnungsfaktor l/100km)))

;Umrechnen von mi/gal->l/100km
(: mi/gal->l/100km (real -> real))
(check-within (mi/gal->l/100km 23.54037) 10 0.0001)
(check-within (mi/gal->l/100km 5.60485) 42 0.0001)
(define mi/gal->l/100km
  (lambda (mi/gal)
    (/ umrechnungsfaktor mi/gal)))