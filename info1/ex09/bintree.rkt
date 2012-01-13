;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname bintree) (read-case-sensitive #f) (teachpacks ((lib "image2.ss" "teachpack" "deinprogramm"))) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ((lib "image2.ss" "teachpack" "deinprogramm")))))
; ######################## Aus Vorlesung  ########################

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

; ######################## Loesung ########################
; Aufgabe 3
; Baeume fuer Testfaelle
(define tree1 (make-node (make-node the-empty-tree
                                    10
                                    (make-leaf 20))
                         5
                         (make-leaf 7)))

(define tree2 (make-node (make-node (make-leaf 20)
                                    25
                                    (make-leaf 30))
                         50
                         (make-node (make-leaf 70)
                                    75
                                    the-empty-tree)))

; btree-min ermittelt die maximale Markierung eines Binaerbaums
(: btree-min ((btree-of real) -> real))

(check-within (btree-min tree1) 5 0.01)
(check-within (btree-min tree2) 20 0.01)
(check-property (for-all ((n real))
                  (< n (btree-min the-empty-tree))))

(define btree-min
  (lambda (b)
    (cond ((empty-tree? b) +inf.0)
          ((node? b) (min (node-label b)
                          (btree-min (node-left-branch b))
                          (btree-min (node-right-branch b)))))))

; bree-max ermittelt die maximale Markierung eines Binaerbaums
(: btree-max ((btree-of real) -> real))

(check-within (btree-max tree1) 20 0.01)
(check-within (btree-max tree2) 75 0.01)
(check-property (for-all ((n real))
                  (> n (btree-max the-empty-tree))))

(define btree-max
  (lambda (b)
    (cond ((empty-tree? b) -inf.0)
          ((node? b) (max (node-label b)
                          (btree-max (node-left-branch b))
                          (btree-max (node-right-branch b)))))))

; ist ein Binaerbaum b ein search-tree? (Suchbaum)
; in einem Suchbaum gilt fuer jeden Knoten mit Markierung x
; - alle Markierungen im linken Teilbaum sind kleiner als x
; - alle Markierungen im rechen Teilbaum sind groesser als x
(: search-tree? ((btree-of real) -> boolean))

(check-expect (search-tree? tree1) #f)
(check-expect (search-tree? tree2) #t)
(check-expect (search-tree? the-empty-tree) #t)

(define search-tree?
  (lambda (b)
    (cond ((empty-tree? b) #t)
          ((node? b)
           (let ((label (node-label b))
                 (left-section (node-left-branch b))
                 (right-section (node-right-branch b)))
             (and (> label (btree-max left-section))
                  (< label (btree-min right-section))
                  (search-tree? left-section)
                  (search-tree? right-section)))))))
