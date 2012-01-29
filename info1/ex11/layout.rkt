;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname layout) (read-case-sensitive #f) (teachpacks ((lib "image.ss" "teachpack" "deinprogramm"))) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ((lib "image.ss" "teachpack" "deinprogramm")))))
; Felix Bartusch / David Herrmann
; Copied from CIS:

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
;                              \__________/   \__________/
;                                  ↑               ↑
;                                 zweifache Rekursion, s. (list-of t)


; Konstruiere Blatt mit Markierung x
(: make-leaf (%a -> (btree-of %a)))
(define make-leaf
  (lambda (x)
    (make-node the-empty-tree x the-empty-tree)))


; Beispiel: t1 (rechts-tief, listen-artig)
(: t1 (btree-of natural))
(define t1
  (make-node the-empty-tree 
             1 
             (make-node the-empty-tree
                        2
                        (make-node the-empty-tree
                                   3
                                   the-empty-tree))))

; Beispiel: t2 (balanciert)
(: t2 (btree-of natural))
(define t2
  (make-node (make-leaf 2)
             1
             (make-leaf 3)))

; Tiefe des Binärbaumes t
(: btree-depth ((btree-of %a) -> natural))
(check-expect (btree-depth the-empty-tree) 0)
(check-expect (btree-depth t1) 3)
(check-expect (btree-depth t2) 2)
(define btree-depth
  (lambda (t)
    (cond ((empty-tree? t) 
           0)
          ((node? t)       
           (+ 1
              (max (btree-depth (node-left-branch t))
                   (btree-depth (node-right-branch t))))))))

; Grösse (Anzahl Knoten) des Binärbaumes t
(: btree-size ((btree-of %a) -> natural))  
(check-expect (btree-size the-empty-tree) 0)
(check-expect (btree-size t1) 3)
(check-expect (btree-size t2) 3)
(define btree-size
  (lambda (t)
    (cond ((empty-tree? t) 
           0)
          ((node? t)       
           (+ (btree-size (node-left-branch t))
              1               
              (btree-size (node-right-branch t)))))))

; Falte Baum t bzgl. z und c
(: btree-fold (%b (%b %a %b -> %b) (btree-of %a) -> %b))
(define btree-fold
  (lambda (z c t)
    (cond ((empty-tree? t) 
           z)
          ((node? t)
           (c (btree-fold z c (node-left-branch t))
              (node-label t)
              (btree-fold z c (node-right-branch t)))))))

; Tranformiere Baum t bzgl. f
(: btree-map ((%c -> %b) (btree-of %a) -> (btree-of %b)))
(define btree-map
  (lambda (f t)
    (cond ((empty-tree? t) 
           the-empty-tree)
          ((node? t)
           (make-node (btree-map f (node-left-branch t))
                      (f (node-label t))
                      (btree-map f (node-right-branch t)))))))

; Konstruiere curried Variante von zweistelliger Funktion f
(define curry
  (lambda (f)
    (lambda (x)
      (lambda (y)
        (f x y)))))

; A tuple consists of:
; - key
; - value
(define-record-procedures-parametric tuple tuple-of
  make-tuple
  tuple?
  (tuple-1
   tuple-2))
(: make-tuple (%a %b -> (tuple-of %a %b)))
(: tuple? (any -> boolean))
(: tuple-1 ((tuple-of %a %b) -> %a))
(: tuple-2 ((tuple-of %a %b) -> %b))

; ---------------------------------------------------------------

; calculate power of 2
(: pow2 (integer -> natural))
(check-expect (pow2 0) 1)
(check-expect (pow2 10) 1024)
(define pow2
  (lambda (x)
    (if (<= x 0)
        1
        (* 2 (pow2 (- x 1))))))

; A position consists of:
; - x
; - y
(define-record-procedures pos
  make-pos pos?
  (pos-x pos-y))
(: make-pos (natural natural -> pos))
(: pos? (any -> boolean))
(: pos-x (pos -> natural))
(: pos-y (pos -> natural))

; Return positions of nodes in btree
(: layout ((btree-of %a) -> (btree-of (tuple-of %a pos))))

; trivial example
(check-expect (layout the-empty-tree) the-empty-tree)
; unbalanced tree
(check-expect (layout t1)
              (make-node the-empty-tree
                         (make-tuple 1 (make-pos 1 1))
                         (make-node the-empty-tree
                                    (make-tuple 2 (make-pos 3 2))
                                    (make-node the-empty-tree (make-tuple 3 (make-pos 4 3)) the-empty-tree))))
; balanced tree
(check-expect (layout t2)
              (make-node (make-node the-empty-tree (make-tuple 2 (make-pos 1 2)) the-empty-tree)
                         (make-tuple 1 (make-pos 2 1))
                         (make-node the-empty-tree (make-tuple 3 (make-pos 3 2)) the-empty-tree)))
; tree from the paper
(check-expect (layout (make-node
                       (make-node
                        (make-node
                         (make-node the-empty-tree "a" the-empty-tree)
                         "c"
                         (make-node
                          (make-node the-empty-tree "d" the-empty-tree)
                          "e"
                          (make-node the-empty-tree "g" the-empty-tree)))
                        "k"
                        (make-node the-empty-tree "m" the-empty-tree))
                       "n"
                       (make-node
                        (make-node
                         the-empty-tree
                         "p"
                         (make-node the-empty-tree "q" the-empty-tree))
                        "u"
                        the-empty-tree)))
              (make-node
               (make-node
                (make-node
                 (make-node the-empty-tree (make-tuple "a" (make-pos 1 4)) the-empty-tree)
                 (make-tuple "c" (make-pos 3 3))
                 (make-node
                  (make-node the-empty-tree (make-tuple "d" (make-pos 4 5)) the-empty-tree)
                  (make-tuple "e" (make-pos 5 4))
                  (make-node the-empty-tree (make-tuple "g" (make-pos 6 5)) the-empty-tree)))
                (make-tuple "k" (make-pos 7 2))
                (make-node the-empty-tree (make-tuple "m" (make-pos 11 3)) the-empty-tree))
               (make-tuple "n" (make-pos 15 1))
               (make-node
                (make-node
                 the-empty-tree
                 (make-tuple "p" (make-pos 19 3))
                 (make-node the-empty-tree (make-tuple "q" (make-pos 21 4)) the-empty-tree))
                (make-tuple "u" (make-pos 23 2))
                the-empty-tree)))

(define layout
  (lambda (tree)
    (letrec ((f (lambda (tree depth)
                  (cond ((empty-tree? tree) tree)
                        ((node? tree)
                         (letrec ((left (f (node-left-branch tree) (- depth 1)))
                                  (right (f (node-right-branch tree) (- depth 1)))
                                  (le (btree-map (lambda (x)
                                                   (make-tuple (tuple-1 x)
                                                               (make-pos (pos-x (tuple-2 x))
                                                                         (+ 1 (pos-y (tuple-2 x))))))
                                                 left))
                                  (unshift (if (empty-tree? right) 0 (pos-x (tuple-2 (node-label right)))))
                                  (xpos (if (empty-tree? le)
                                            0
                                            (+ (pow2 (- depth 2))
                                               (pos-x (tuple-2 (node-label le))))))
                                  (ri (btree-map (lambda (x)
                                                   (make-tuple (tuple-1 x)
                                                               (make-pos (- (+ (pow2 (- depth 2)) xpos (pos-x (tuple-2 x)))
                                                                            unshift)
                                                                         (+ 1 (pos-y (tuple-2 x))))))
                                                 right)))
                           (make-node le
                                      (make-tuple (node-label tree) (make-pos xpos 1))
                                      ri)))))))
      (btree-map (lambda (x)
                   (make-tuple (tuple-1 x)
                               (make-pos (+ 1 (pos-x (tuple-2 x)))
                                         (pos-y (tuple-2 x)))))
                 (f tree (btree-depth tree))))))