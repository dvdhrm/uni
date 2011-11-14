;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname emission-sticker) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; particle filter type
(define particle-filter (signature (one-of "PM1" "PM2" "PM3")))

; gas type
(define gas-type (signature (one-of "Diesel" "Benzin")))

; a vehicle consists of:
; - the gas type (gas)
; - the particle filter (filter)
; - the emission-counter (emission)

(: make-vehicle (gas-type particle-filter natural -> vehicle))
(: vehicle? (any -> boolean))
(: vehicle-emission (vehicle -> natural))
(: vehicle-filter (vehicle -> particle-filter))
(: vehicle-gas (vehicle -> gas-type))

(define-record-procedures vehicle
  make-vehicle
  vehicle?
  (vehicle-gas
   vehicle-filter
   vehicle-emission))

; an emission group
(define emission-group (signature (one-of 1 2 3 4)))

; Calculate emission group

(: calculate-emission-group (vehicle -> emission-group))

(check-expect (calculate-emission-group
               (make-vehicle "Diesel" "PM1" 10)) 1)
(check-expect (calculate-emission-group
               (make-vehicle "Diesel" "PM2" 70)) 4)
(check-expect (calculate-emission-group
               (make-vehicle "Diesel" "PM3" 66)) 4)
(check-expect (calculate-emission-group
               (make-vehicle "Diesel" "PM1" 98)) 1)
(check-expect (calculate-emission-group
               (make-vehicle "Benzin" "PM1" 77)) 4)
(check-expect (calculate-emission-group
               (make-vehicle "Benzin" "PM1" 2)) 4)
(check-expect (calculate-emission-group
               (make-vehicle "Benzin" "PM1" 10)) 1)

(define calculate-emission-group
  (lambda (car)
    (if (string=? (vehicle-gas car) "Diesel")
        (cond ((and (string=? (vehicle-filter car) "PM3")
                    (or (= (vehicle-emission car) 32)
                        (= (vehicle-emission car) 33)
                        (= (vehicle-emission car) 38)
                        (= (vehicle-emission car) 39)
                        (= (vehicle-emission car) 43)
                        (and (>= (vehicle-emission car) 53)
                             (<= (vehicle-emission car) 66))))
               4)
              ((and (string=? (vehicle-filter car) "PM2")
                    (or (= (vehicle-emission car) 30) 
                        (= (vehicle-emission car) 31) 
                        (= (vehicle-emission car) 36) 
                        (= (vehicle-emission car) 37) 
                        (= (vehicle-emission car) 42) 
                        (and (>= (vehicle-emission car) 44)
                             (<= (vehicle-emission car) 48))
                        
                        (and (>= (vehicle-emission car) 67)
                             (<= (vehicle-emission car) 70))))
               4)
              
              ((and (string=? (vehicle-filter car) "PM1")
                    (or (and (>= (vehicle-emission car) 49)
                             (<= (vehicle-emission car) 52))
                        (= (vehicle-emission car) 27)))
               4)
              ((and (string=? (vehicle-filter car) "PM1")
                    (or (= (vehicle-emission car) 14)
                        (= (vehicle-emission car) 16)
                        (= (vehicle-emission car) 18)
                        (= (vehicle-emission car) 21)
                        (= (vehicle-emission car) 22)
                        (and (>= (vehicle-emission car) 25)
                             (<= (vehicle-emission car) 29))
                        (= (vehicle-emission car) 34) 
                        (= (vehicle-emission car) 35) 
                        (= (vehicle-emission car) 40) 
                        (= (vehicle-emission car) 41) 
                        (= (vehicle-emission car) 71) 
                        (= (vehicle-emission car) 77)))
               3)
              ((= (vehicle-emission car) 32) 4)
              ((= (vehicle-emission car) 33) 4)
              ((= (vehicle-emission car) 38) 4)
              ((= (vehicle-emission car) 39) 4)
              ((= (vehicle-emission car) 43) 4)
              ((and (>= (vehicle-emission car) 53)
                    (<= (vehicle-emission car) 70))
               4)
              ((and (>= (vehicle-emission car) 73)
                    (<= (vehicle-emission car) 75))
               4)
              ((= (vehicle-emission car) 30) 3)
              ((= (vehicle-emission car) 31) 3)
              ((= (vehicle-emission car) 36) 3)
              ((= (vehicle-emission car) 37) 3)
              ((= (vehicle-emission car) 42) 3)
              ((and (>= (vehicle-emission car) 44)
                    (<= (vehicle-emission car) 52))
               3)
              ((= (vehicle-emission car) 72) 3)
              ((and (>= (vehicle-emission car) 25)
                    (<= (vehicle-emission car) 29))
               2)
              ((= (vehicle-emission car) 35) 2)
              ((= (vehicle-emission car) 41) 2)
              ((= (vehicle-emission car) 71) 2)
              ((and (>= (vehicle-emission car) 0)
                    (<= (vehicle-emission car) 24))
               1)
              ((= (vehicle-emission car) 34) 1)
              ((= (vehicle-emission car) 40) 1)
              ((= (vehicle-emission car) 77) 1)
              ((= (vehicle-emission car) 88) 1)
              ((= (vehicle-emission car) 98) 1)
              (else 1))
        (cond ((= (vehicle-emission car) 0) 1)
              ((and (>= (vehicle-emission car) 3)
                    (<= (vehicle-emission car) 13))
               1)
              ((= (vehicle-emission car) 15) 1)
              ((= (vehicle-emission car) 17) 1)
              ((= (vehicle-emission car) 88) 1)
              ((= (vehicle-emission car) 98) 1)
              ((= (vehicle-emission car) 1) 4)
              ((= (vehicle-emission car) 2) 4)
              ((= (vehicle-emission car) 14) 4)
              ((= (vehicle-emission car) 16) 4)
              ((and (>= (vehicle-emission car) 18)
                    (<= (vehicle-emission car) 75))
               4)
              ((= (vehicle-emission car) 77) 4)
              (else 1)))))

; install emission filter and return new vehicle
(: install-emission-filter (vehicle -> vehicle))

(check-expect (install-emission-filter
               (make-vehicle "Benzin" "PM1" 5))
              (make-vehicle "Benzin" "PM1" 5))
(check-expect (install-emission-filter
               (make-vehicle "Diesel" "PM1" 30))
              (make-vehicle "Diesel" "PM2" 30))
(check-expect (install-emission-filter
               (make-vehicle "Diesel" "PM1" 66))
              (make-vehicle "Diesel" "PM3" 66))
(check-expect (install-emission-filter
               (make-vehicle "Diesel" "PM2" 43))
              (make-vehicle "Diesel" "PM3" 43))
(check-expect (install-emission-filter
               (make-vehicle "Diesel" "PM3" 5))
              (make-vehicle "Diesel" "PM3" 5))

(define install-emission-filter
  (lambda (car)
    (cond ((string=? (vehicle-gas car) "Benzin") car)
          ((string=? (vehicle-filter car) "PM3") car)
          ((string=? (vehicle-filter car) "PM2")
           (if (or (= (vehicle-emission car) 32)
                   (= (vehicle-emission car) 33)
                   (= (vehicle-emission car) 38)
                   (= (vehicle-emission car) 39)
                   (= (vehicle-emission car) 43)
                   (and (>= (vehicle-emission car) 53)
                        (<= (vehicle-emission car) 66)))
               (make-vehicle (vehicle-gas car) "PM3" (vehicle-emission car))
               car))
          ((string=? (vehicle-filter car) "PM1")
           (cond ((or (= (vehicle-emission car) 30) 
                      (= (vehicle-emission car) 31) 
                      (= (vehicle-emission car) 36) 
                      (= (vehicle-emission car) 37) 
                      (= (vehicle-emission car) 42) 
                      (and (>= (vehicle-emission car) 44)
                           (<= (vehicle-emission car) 48))
                      
                      (and (>= (vehicle-emission car) 67)
                           (<= (vehicle-emission car) 70)))
                  (make-vehicle (vehicle-gas car) "PM2" (vehicle-emission car)))
                 ((or (= (vehicle-emission car) 32)
                      (= (vehicle-emission car) 33)
                      (= (vehicle-emission car) 38)
                      (= (vehicle-emission car) 39)
                      (= (vehicle-emission car) 43)
                      (and (>= (vehicle-emission car) 53)
                           (<= (vehicle-emission car) 66)))
                  (make-vehicle (vehicle-gas car) "PM3" (vehicle-emission car)))
                 (else car)))
          (else car))))