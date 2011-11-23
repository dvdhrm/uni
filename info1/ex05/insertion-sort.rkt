;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname insertion-sort) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; Felix Bartusch / David Herrmann

; insert-sorted - insert number into sorted list
(: insert-sorted (real (list-of real) -> (list-of real)))

(check-within (insert-sorted 0 empty) (make-pair 0 empty) 0.001)
(check-within (insert-sorted 0 (make-pair 5
                                          (make-pair 10
                                                     empty)))
              (make-pair 0
                         (make-pair 5
                                    (make-pair 10
                                               empty))) 0.001)
(check-within (insert-sorted -1 (make-pair -20
                                           (make-pair -10
                                                      (make-pair -5
                                                                 empty))))
              (make-pair -20
                         (make-pair -10
                                    (make-pair -5
                                               (make-pair -1
                                                          empty)))) 0.001)

(define insert-sorted
  (lambda (ele list)
    (cond ((empty? list)
           (make-pair ele empty))
          ((pair? list)
           (let ((f (first list)))
             (if (<= ele f)
                 (make-pair ele list)
                 (make-pair f
                            (insert-sorted ele
                                           (rest list)))))))))

; sort-list - Return sorted list of numbers
(: sort-list ((list-of real) -> (list-of real)))

(check-expect (sort-list (make-pair 10
                                    (make-pair 20
                                               (make-pair -5
                                                          (make-pair 3
                                                                     (make-pair 100
                                                                                (make-pair 0
                                                                                           empty)))))))
              (make-pair -5
                         (make-pair 0
                                    (make-pair 3
                                               (make-pair 10
                                                          (make-pair 20
                                                                     (make-pair 100
                                                                                empty)))))))
(check-expect (sort-list (make-pair 5
                                    (make-pair 10
                                               empty)))
              (make-pair 5
                         (make-pair 10
                                    empty)))
(check-expect (sort-list empty) empty)

(define sort-list
  (lambda (list)
    (cond ((empty? list)
           empty)
          ((pair? list)
           (insert-sorted (first list)
                          (sort-list (rest list)))))))