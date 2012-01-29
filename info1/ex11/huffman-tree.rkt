;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname huffman-tree) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #f #t none explicit #f ())))
; Felix Bartusch / David Herrmann
; Copied from CIS:

(: curry ((%a %b -> %c) -> (%a -> (%b -> %c))))
(define curry
  (lambda (f)
    (lambda (x)
      (lambda (y)
        (f x y)))))

(: compose ((%b -> %c) (%a -> %b) -> (%a -> %c)))
(define compose
  (lambda (f g)
    (lambda (x)
      (f (g x)))))

(: flatten ((list (list %a)) -> (list %a)))
(define flatten
  (lambda (xss)
    (fold empty
          append
          xss)))

(: filter ((%a -> boolean) (list %a) -> (list %a)))
(define filter
  (lambda (p xs)
    (cond ((empty? xs) empty)
          ((pair? xs)  (if (p (first xs))
                           (make-pair (first xs) (filter p (rest xs)))
                           (filter p (rest xs)))))))

; Ein Blatt eines Huffman-Tree (huff-leaf)
; - trägt eine Markierung (label):
(: make-huff-leaf (%a -> (huff-leaf-of %a)))
(: huff-leaf-label ((huff-leaf-of %a) -> %a))
(define-record-procedures-parametric huff-leaf huff-leaf-of
  make-huff-leaf
  huff-leaf?
  (huff-leaf-label))

; Ein innerer Knoten eines Huffman-Tree (huff-node) besitzt
; - einen linken Teilbaum (left) und
; - einen rechten Teibaum (right):
(: make-huff-node (%a %b -> (huff-node-of %a %b)))
(: huff-node-left ((huff-node-of %a %b) -> %a))
(: huff-node-right ((huff-node-of %a %b) -> %b))
(define-record-procedures-parametric huff-node huff-node-of
  make-huff-node
  huff-node?
  (huff-node-left
   huff-node-right))

; Signatur (huff-tree-of t): Huffman-Tree mit Blättern 
; mit Markierungen der Signatur t
(define huff-tree-of
  (lambda (t)
    (signature (mixed (huff-leaf-of t)
                      (huff-node-of (huff-tree-of t) (huff-tree-of t))))))

; Ein Bit eines Zeichencodes
(define bit
  (signature (one-of 0 1)))

; -----------------------------------------------------------

; A tuple consists of:
; - key
; - value
(define-record-procedures-parametric tuple tuple-of
  make-tuple
  tuple?
  (tuple-key
   tuple-value))
(: make-tuple (%a %b -> (tuple-of %a %b)))
(: tuple? (any -> boolean))
(: tuple-key ((tuple-of %a %b) -> %a))
(: tuple-value ((tuple-of %a %b) -> %b))

; An association-list is a list of tuples
(define association-list-of
  (lambda (key value)
    (signature (list-of (tuple-of key value)))))

; A sample association-list
(define sample (list (make-tuple "a" 5)
                     (make-tuple "b" 10)
                     (make-tuple "c" 0)
                     (make-tuple "d" 100)))
; A sample huff-tree
; Beispiel: Huffman-Tree für Text "erdbeermarmelade"  (s. oben)
(define sample2
  (make-huff-node 
   (make-huff-node 
    (make-huff-node 
     (make-huff-leaf "r")
     (make-huff-leaf "a"))
    (make-huff-node
     (make-huff-leaf "x")
     (make-huff-leaf "d")))
   (make-huff-leaf "e")))

; find a value in an association-list
(: lookup ((association-list-of string %b) string -> %b))

(check-expect (lookup sample "a") 5)
(check-expect (lookup sample "d") 100)
(check-error (lookup sample "x"))
(check-error (lookup empty "a"))

(define lookup
  (lambda (lis str)
    (cond ((empty? lis)
           (violation "Not found!"))
          ((pair? lis)
           (if (string=? (tuple-key (first lis))
                         str)
               (tuple-value (first lis))
               (lookup (rest lis) str))))))

; transform huffman tree into association list
(: make-association-list ((huff-tree-of string) -> (association-list-of string (list-of bit))))

(check-expect (make-association-list (make-huff-leaf "a")) (list (make-tuple "a" empty)))
(check-expect (make-association-list sample2)
              (list (make-tuple "r" (list 0 0 0))
                    (make-tuple "a" (list 0 0 1))
                    (make-tuple "x" (list 0 1 0))
                    (make-tuple "d" (list 0 1 1))
                    (make-tuple "e" (list 1))))

(define make-association-list
  (lambda (tree)
    (cond ((huff-leaf? tree)
           (list (make-tuple (huff-leaf-label tree)
                             empty)))
          ((huff-node? tree)
           (append (fold empty
                         (lambda (x xs)
                           (make-pair (make-tuple (tuple-key x)
                                                  (make-pair 0 (tuple-value x)))
                                      xs))
                         (make-association-list (huff-node-left tree)))
                   (fold empty
                         (lambda (x xs)
                           (make-pair (make-tuple (tuple-key x)
                                                  (make-pair 1 (tuple-value x)))
                                      xs))
                         (make-association-list (huff-node-right tree))))))))

; encode string into list of bit-lists by association list
(: assoc-encode ((association-list-of string (list-of bit)) string -> (list-of (list-of bit))))

(check-expect (assoc-encode (make-association-list sample2) "") empty)
(check-expect (assoc-encode (make-association-list sample2) "rae")
              (list (list 0 0 0)
                    (list 0 0 1)
                    (list 1)))

(define assoc-encode
  (lambda (assoc str)
    (let ((xs (string->strings-list str)))
      (fold empty
            (lambda (x xs)
              (make-pair (lookup assoc x)
                         xs))
            xs))))

; Die Assoziationsliste sollte abhängig ihrer Häufigkeit sortiert sein. Ein Buchstabe mit hoher
; Häufigkeit sollte vorne in der Liste stehen und einer mit niedriger Häufigkeit hinten in der
; Liste da (lookup ...) vorne zum Suchen anfängt.
; Zur Bestimmung der Häufigkeiten kann wieder dieselbe Methode wie für huffman-trees verwendet werden.