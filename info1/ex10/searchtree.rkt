;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname searchtree) (read-case-sensitive #f) (teachpacks ((lib "image2.ss" "teachpack" "deinprogramm"))) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ((lib "image2.ss" "teachpack" "deinprogramm")))))
; David Herrmann / Felix Bartusch
; Tutorin: Nina Flad
; Aufgabe 2 - searchtree.rkt

; ####################### Vorlesungscode #######################

; Ein Knoten (node) eines Binärbaums besitzt
; - einen linken Zweig (left-branch),
; - eine Markierung (label) und
; - einen rechten Zweig (right-branch)
(: make-node (%a %b %c -> (node-of %a %b %c)))
(: node-left-branch  ((node-of %a %b %c) -> %a))
(: node-label        ((node-of %a %b %c) -> %b))
(: node-right-branch ((node-of %a %b %c) -> %c))
(define-record-procedures-parametric node node-of
  make-node
  node?
  (node-left-branch
   node-label
   node-right-branch))
 
; Ein leerer Baum (empty-tree) besitzt
; keine weiteren Eigenschaften
(: make-empty-tree (-> empty-tree))
(define-record-procedures empty-tree
  make-empty-tree
  empty-tree?
  ())

; Der leere Baum (Abkürzung)
(: the-empty-tree empty-tree)
(define the-empty-tree (make-empty-tree))

; Signatur für Binärbäume (btree-of t) mit Markierungen des Signatur t
; (im linken/rechten Zweig jedes Knotens findet sich jeweils wieder
; ein Binärbaum)
(define btree-of
  (lambda (t)
    (signature (mixed empty-tree
                      (node-of (btree-of t) t (btree-of t))))))

; Konstruiere Blatt mit Markierung x
(: make-leaf (%a -> (btree-of %a)))
(define make-leaf
  (lambda (x)
        (make-node the-empty-tree x the-empty-tree)))

; ####################### Loesung #######################
                                   
; Suchbaum fuer Tests
(define tree2 (make-node (make-node (make-leaf 20)
                                    25
                                    (make-leaf 30))
                         50
                         (make-node (make-leaf 70)
                                    75
                                    the-empty-tree)))

; btree-member? prueft, ob eine Markierung x in einem Suchbaum t vorkommt
(: btree-member? (integer (btree-of integer) -> boolean))
(check-expect (btree-member? 30 tree2) #t)
(check-expect (btree-member? 75 tree2) #t)
(check-expect (btree-member? 10 tree2) #f)
(define btree-member?
  (lambda (x t)
    (cond ((empty-tree? t) #f)                
          ((and (node? t) (= x (node-label t))) #t)
          ((and (node? t) (< x (node-label t))) (btree-member? x (node-left-branch t)))
          ((and (node? t) (> x (node-label t))) (btree-member? x (node-right-branch t))))))

; btree-insert fuegt eine Markierung x in einen Suchbaum ein. Das Ergebniss ist wieder ein Suchbaum
(: btree-insert (real (btree-of real) -> (btree-of real)))
(check-expect (btree-insert 5 the-empty-tree) (make-leaf 5))
(check-expect (btree-insert 75 tree2) tree2)
(check-expect (btree-insert 10 tree2) (list->searchtree (list 10 70 30 20 75 25 50)))
(check-expect (btree-insert 72 tree2) (list->searchtree (list 72 70 30 20 75 25 50)))
(define btree-insert
  (lambda (x t)
    (cond ((empty-tree? t) (make-leaf x))
          ((node? t)
           (cond ((= (node-label t) x) t)
                 ((< x (node-label t)) (make-node (btree-insert x (node-left-branch t))
                                                  (node-label t)
                                                  (node-right-branch t)))
                 ((> x (node-label t)) (make-node (node-left-branch t)
                                                  (node-label t)
                                                  (btree-insert x (node-right-branch t)))))))))

; list->searchtree wandelt eine Liste von Elementen in einen Suchbaum um
; btree-fold macht hier -überhaupt- keinen Sinn!
(: list->searchtree ((list-of real) -> (btree-of real)))
(check-expect (list->searchtree empty) the-empty-tree)
(check-expect (list->searchtree (list 10)) (make-leaf 10))
(check-expect (list->searchtree (list 70 30 20 75 25 50)) tree2)
(define list->searchtree
  (lambda (xs)
    (fold the-empty-tree
          btree-insert
          xs)))

; get smallest member in btree (Von Vorlesung kopiert)
(: btree-min ((btree-of real) -> real))

(define btree-min
  (lambda (tr)
      (cond ((empty-tree? tr) +inf.0)
            ((node? tr) 
             (letrec ((leftTr (node-left-branch tr))
                      (rightTr (node-right-branch tr))
                      (left (btree-min leftTr))
                      (right (btree-min rightTr)))
                     (min (node-label tr) left right))))))

; btree-delete entfernt eine Markierung x aus einem Suchbaum t
(: btree-delete ((btree-of real) -> (btree-of real)))
(check-expect (btree-delete 10 the-empty-tree) the-empty-tree)
(check-expect (btree-delete 3 (list->searchtree (list 3 1 2))) (list->searchtree (list 1 2)))
(check-expect (btree-delete 2 (list->searchtree (list 1 2))) (make-leaf 1))
(check-expect (btree-delete 1 (list->searchtree (list 3 1 2))) (list->searchtree (list 3 2)))
(check-within (btree-delete 25 tree2) (list->searchtree (list 70 75 20 30 50)) 0.01)

(define btree-delete
  (lambda (x t)
    (cond ((empty-tree? t) the-empty-tree)
          (else
           (letrec ((label (node-label t))
                    (rtree (node-right-branch t))
                    (ltree (node-left-branch t)))
             (cond ((< x label) (make-node (btree-delete x ltree)
                                           label
                                           rtree))
                   ((> x label) (make-node ltree
                                           label
                                           (btree-delete x rtree)))
                   ((and (= x label)
                         (empty-tree? ltree)) rtree)
                   ((and (= x label)
                         (empty-tree? rtree)) ltree)
                   ((= x label) 
                    (let ((min (btree-min rtree)))
                      (make-node ltree
                                 min
                                 (btree-delete min
                                               rtree))))))))))