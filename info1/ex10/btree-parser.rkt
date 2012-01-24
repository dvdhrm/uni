;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-vanilla-reader.ss" "deinprogramm")((modname btree-parser) (read-case-sensitive #f) (teachpacks ((lib "image2.ss" "teachpack" "deinprogramm") (lib "universe.ss" "teachpack" "deinprogramm"))) (deinprogramm-settings #(#f write repeating-decimal #t #t none explicit #f ((lib "image2.ss" "teachpack" "deinprogramm") (lib "universe.ss" "teachpack" "deinprogramm")))))
; Felix Bartusch / David Herrmann

; Aus Vorlesung
  
; Konstruiere curried Variante von zweistelliger Funktion f
(define curry
  (lambda (f)
    (lambda (x)
      (lambda (y)
        (f x y)))))

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

;--------------------------------------------------------------------------------------------

; This accepts an input string like this:
;  "(" <something> ")" <rest>
; And splits this into several lists recursively:
;  (list "(" <something> ")" )
;  (list <rest>)
; This also checks the syntax so its not just a simple splitter.
(: split-list ((list-of string) -> (list-of (list-of string))))

(check-expect (split-list (list "(" "_" "3" "_" ")" "1" "_" ")"))
              (list (list "(" "_" "3" "_" ")") (list "1" "_" ")")))
(check-error (split-list empty)
             "Ungültige Eingabe!")

(define split-list
  (lambda (xs)
    (letrec ((rec (lambda (con acc ls)
                    (letrec ((f (if (pair? ls)
                                    (first ls)
                                    (violation "Ungültige Eingabe!")))
                             (new_con (cond 
                                        ((string=? ")" f)
                                         (if (empty? con)
                                             (violation "Ungültige Eingabe!")
                                             (rest con)))
                                        ((string=? f "(")
                                         (make-pair 1 con))
                                        (else con))))
                      (cond ((empty? (rest ls)) (violation "Ungültige Eingabe!"))
                            ((empty? new_con)
                             (cond ((string=? f "_")
                                    (list (list "_") (rest ls)))
                                   ((string=? f ")")
                                    (list (append acc (list f))
                                          (rest ls)))))
                            (else (rec new_con
                                    (append acc (list f))
                                    (rest ls))))))))
      (rec empty empty xs))))

; To avoid having a list with fixed number of members, we use a helper struct
; here so we can guarantee that we have all members and we can directly access
; the members we need.
; This record is used to split the input into left, right label and the rest of
; the input.
(define-record-procedures tlist
  make-tlist
  tlist?
  (tlist-left
   tlist-label
   tlist-right
   tlist-rest))
(: make-tlist ((list-of string) string (list-of string) (list-of string) -> tlist))
(: tlist? (any -> boolean))
(: tlist-left (tlist -> (list-of string)))
(: tlist-right (tlist -> (list-of string)))
(: tlist-label (tlist -> string))
(: tlist-rest (tlist -> (list-of string)))

; split left and right tree and put that into a tlist structure
(: split ((list-of string) -> tlist))
(define split
  (lambda (xs)
    (letrec ((lef (split-list xs))
             (f (first (rest lef)))
             (rig (split-list (rest f))))
      (make-tlist (first lef)
                  (first f)
                  (first rig)
                  (first (rest rig))))))

; btree-parse parses a string into a btree following the given rules
; This also removes all spaces as we are told to do so.
(: btree-parse (string -> (btree-of string)))

(check-error (btree-parse ""))
(check-expect (btree-parse "_") the-empty-tree)
(check-expect (btree-parse "(_1_)") (make-leaf "1"))
(check-expect (btree-parse "(((_7_)8_)9(_6_))")
              (make-node (make-node (make-leaf "7") "8" the-empty-tree)
                         "9"
                         (make-leaf "6")))
(check-expect (btree-parse "(((_1_)2_)3(_4_))")
              (make-node (make-node (make-leaf "1") "2" the-empty-tree)
                         "3"
                         (make-leaf "4")))

(define btree-parse
  (lambda (input)
    (letrec ((parse (lambda (xs)
                      (letrec ((f (if (pair? xs)
                                      (first xs)
                                      (violation "Ungültige Eingabe!")))
                               (r (rest xs))
                               (tree (if (pair? r)
                                         (split r)
                                         empty)))
                        (cond ((and (string=? f "_") (empty? r)) the-empty-tree)
                              ((and (string=? f "(") 
                                    (pair? r)
                                    (pair? (tlist-rest tree))
                                    (string=? (first (tlist-rest tree)) ")")
                                    (empty? (rest (tlist-rest tree))))
                               (make-node (parse (tlist-left tree))
                                          (tlist-label tree)
                                          (parse (tlist-right tree))))
                              (else (violation "Ungültige Eingabe!")))))))
      (parse
       (fold empty
             (lambda (x xs)
               (if (string=? x " ")
                   xs
                   (make-pair x xs)))
             (string->strings-list input))))))