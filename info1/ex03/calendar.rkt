;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-beginner-reader.ss" "deinprogramm")((modname calendar) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; A calendar consists of:
; - day of the month (day)
; - month of the year (month)
; - current year (year)

(: make-calendar (natural natural integer -> calendar))
(: calendar? (any -> boolean))
(: calendar-day (calendar -> natural))
(: calendar-month (calendar -> natural))
(: calendar-year (calendar -> integer))

(check-expect (calendar-day (make-calendar 1 2 3)) 1)

(define-record-procedures calendar
  make-calendar
  calendar?
  (calendar-day
   calendar-month
   calendar-year))

; Test whether a given calendar date is a valid date
(: calendar-date-ok? (calendar -> boolean))

(check-expect (calendar-date-ok?
               (make-calendar 1 1 1)) #t)
(check-expect (calendar-date-ok?
               (make-calendar 31 4 1)) #f)
(check-expect (calendar-date-ok?
               (make-calendar 31 12 1)) #t)
(check-expect (calendar-date-ok?
               (make-calendar 1 13 1)) #f)
(check-expect (calendar-date-ok?
               (make-calendar 28 2 1)) #t)
(check-expect (calendar-date-ok?
               (make-calendar 29 2 0)) #t)

(define calendar-date-ok?
  (lambda (date)
    (and (>= (calendar-month date) 1)
         (<= (calendar-month date) 12)
         (>= (calendar-day date) 1)
         (<= (calendar-day date) 31)
         (or (<= (calendar-day date) 28)
             (if (= (calendar-month date) 2)
                 (and (<= (calendar-day date) 29)
                      (= (modulo (calendar-year date) 4) 0))
                 (or (<= (calendar-day date) 30)
                     (or (= (calendar-month date) 1)
                         (= (calendar-month date) 3)
                         (= (calendar-month date) 5)
                         (= (calendar-month date) 7)
                         (= (calendar-month date) 8)
                         (= (calendar-month date) 10)
                         (= (calendar-month date) 12))))))))