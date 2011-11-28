;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname insertion-sort) (read-case-sensitive #f) (teachpacks ((lib "image2.ss" "teachpack" "deinprogramm"))) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ((lib "image2.ss" "teachpack" "deinprogramm")))))
; Felix Bartusch / David Herrmann

; sorted? - Return true if list is sorted ascendingly
(: sorted? ((list-of real) -> boolean))

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

; insert-sorted expects a sorted list as second argument
(define sorted-list
  (signature (predicate sorted?)))

; insert-sorted - insert number into sorted list, the new list is sorted, too
(: insert-sorted (real sorted-list -> sorted-list))

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
