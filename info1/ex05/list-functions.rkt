;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname list-functions) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; Felix Bartusch / David Herrmann

; last - Return last element of list
(: last ((list-of %a) -> %a))

(check-error (last empty) "Liste ist leer")
(check-expect (last (make-pair 5 empty)) 5)
(check-expect (last (make-pair 5
                               (make-pair 10
                                          (make-pair 20
                                                     empty)))) 20)

(define last
  (lambda (list)
    (cond ((empty? list)
           (violation "Liste ist leer"))
          ((pair? list)
           (let ((r (rest list)))
             (cond ((empty? r)
                    (first list))
                   ((pair? r)
                    (last r))))))))

; elem? - Check whether given element is already in list
(: elem? ((list-of real) real -> boolean))

(check-expect (elem? empty 5) #f)
(check-expect (elem? (make-pair 5
                                (make-pair 10
                                           empty)) 20) #f)
(check-expect (elem? (make-pair 5 empty) 5) #t)
(check-expect (elem? (make-pair 5
                                (make-pair 10
                                           (make-pair 20
                                                      empty))) 20) #t)

(define elem?
  (lambda (list ele)
    (cond ((empty? list)
           #f)
          ((pair? list)
           (if (= (first list) ele)
               #t
               (elem? (rest list) ele))))))

; max-list - return greatest element of list
(: max-list ((list-of natural) -> natural))

(check-error (max-list empty) "Liste ist leer")
(check-expect (max-list (make-pair 5
                                   (make-pair 10
                                              (make-pair 20
                                                         empty)))) 20)
(check-expect (max-list (make-pair 5 empty)) 5)
(check-expect (max-list (make-pair 10
                                   (make-pair 20
                                              (make-pair 5
                                                         empty)))) 20)

(define max-list
  (lambda (list)
    (cond ((empty? list)
           (violation "Liste ist leer"))
          ((pair? list)
           (let ((r (rest list))
                 (f (first list)))
             (cond ((empty? r)
                    f)
                   ((pair? r)
                    (max (max-list r) f))))))))

; min-list - return smallest element of list
(: min-list ((list-of natural) -> natural))

(check-error (min-list empty) "Liste ist leer")
(check-expect (min-list (make-pair 5
                                   (make-pair 10
                                              (make-pair 20
                                                         empty)))) 5)
(check-expect (min-list (make-pair 5 empty)) 5)
(check-expect (min-list (make-pair 10
                                   (make-pair 20
                                              (make-pair 5
                                                         empty)))) 5)

(define min-list
  (lambda (list)
    (cond ((empty? list)
           (violation "Liste ist leer"))
          ((pair? list)
           (let ((r (rest list))
                 (f (first list)))
             (cond ((empty? r)
                    f)
                   ((pair? r)
                    (min (min-list r) f))))))))

; sorted? - Return true if list is sorted ascendingly
(: sorted? ((list-of natural) -> boolean))

(check-expect (sorted? empty) #t)
(check-expect (sorted? (make-pair 5
                                  (make-pair 10
                                             (make-pair 9
                                                        empty)))) #f)
(check-expect (sorted? (make-pair 5
                                  (make-pair 10
                                             (make-pair 10
                                                        empty)))) #t)

(define sorted?
  (lambda (list)
    (cond ((empty? list)
           #t)
          ((pair? list)
           (let ((r (rest list)))
             (cond ((empty? r)
                    #t)
                   ((pair? r)
                    (and (<= (first list)
                             (first r))
                         (sorted? r)))))))))

; all-equal? - Test whether all list elements are equal
(: all-equal? ((list-of real) -> boolean))

(check-expect (all-equal? empty) #t)
(check-expect (all-equal? (make-pair 5
                                     (make-pair 5
                                                (make-pair 10
                                                           empty)))) #f)
(check-expect (all-equal? (make-pair 5
                                     (make-pair 5
                                                empty))) #t)

(define all-equal?
  (lambda (list)
    (cond ((empty? list)
           #t)
          ((pair? list)
           (let ((r (rest list)))
             (cond ((empty? r)
                    #t)
                   ((pair? r)
                    (and (= (first list)
                            (first r))
                         (all-equal? r)))))))))