#reader(lib "DMdA-advanced-reader.ss" "deinprogramm")((modname ex1_2) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #t #t none datum #f ())))

(define billig_strom (lambda (x) (/ (+ 490 (* x 19)) 100)))
(define watt_für_wenig (lambda (x) (/ (+ 820 (* x 16)) 100)))

(billig_strom 50)
(watt_für_wenig 50)


