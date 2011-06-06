;; Die ersten drei Zeilen dieser Datei wurden von DrRacket eingefügt. Sie enthalten Metadaten
;; über die Sprachebene dieser Datei in einer Form, die DrRacket verarbeiten kann.
#reader(lib "DMdA-advanced-reader.ss" "deinprogramm")((modname secd2) (read-case-sensitive #f) (teachpacks ()) (deinprogramm-settings #(#f write repeating-decimal #t #t none datum #f ())))
(define e1 '((lambda (x) (+ x 5)) 7))

; Ein Lambda-Term ist eins der folgenden:
; - ein Basiswert
; - eine Variable
; - eine Applikation
; - eine primitive Applikation
; - eine Abstraktion
(define term
  (signature
   (mixed base
          variable
          application
          primitive-application
          abstraction)))

; Ein Basiswert ist eins der folgenden:
; - eine Zahl
; - ein Boolean
(define base 
  (signature
   (mixed number boolean)))

; Prädikat für Basiswerte
(: base? (any -> boolean))
(check-expect (base? 5) #t)
(check-expect (base? #t) #t)
(check-expect (base? '(x y)) #f)
(define base?
  (lambda (x)
    (or (number? x)
        (boolean? x))))


; Eine Variable ist ein Symbol
(define variable (signature symbol))

; Prädikat für Variablen
(: variable? (any -> boolean))
(check-expect (variable? 'x) #t)
(check-expect (variable? 5) #f)
(check-expect (variable? '(x y)) #f)
              
(define variable?
  (lambda (x)
    (symbol? x)))
              
; Eine Applikation ist eine n-elementige Liste aus:
; - Operator
; - operand
(: application? (any -> boolean))
(check-expect (application? '(x y)) #t)
(check-expect (application? '(x y z)) #t)
(check-expect (application? '(+ x)) #f)
(check-expect (application? '(+ x y)) #f)
(check-expect (application? '((x y) z)) #t)
(check-expect (application? '(lambda (x) (+ x 5))) #f)
(define application?
  (lambda (x)
    (and (pair? x)
         (pair? (rest x))
         (not (equal? (first x) 'lambda))
         (not (primitive-operation? (first x))))))
                 
(define application
  (signature (predicate application?)))

; Operator extrahieren
(: application-operator (application -> term))
(check-expect (application-operator '(x y)) 'x)
(define application-operator
  (lambda (a)
    (first a)))

; Operanden extrahieren
(: application-operand (application -> term))
(check-expect (application-operand '(x y)) 'y)
(define application-operand
  (lambda (a)
    (first (rest a))))

; Alle Operanden extrahieren
(: application-operands (application -> term))
(check-expect (application-operands '(x y z)) '(y z))
(define application-operands
  (lambda (a)
    (rest a)))

; Eine primitive Applikation ist eine Liste,
; deren erstes Element eine primitive Operation ist
(: primitive-application? (any -> boolean))
(check-expect (primitive-application? '(+ x y)) #t)
(check-expect (primitive-application? '(x y)) #f)
(define primitive-application?
  (lambda (x)
    (and (pair? x)
         (primitive-operation? (first x)))))
    
(define primitive-application
  (signature (predicate primitive-application?)))

; Primitiven Operator extrahieren
(: primitive-application-operator (primitive-application -> primitive-operation))
(define primitive-application-operator
  (lambda (pa)
    (first pa)))

; Primitive Operanden extrahieren
(: primitive-application-operands (primitive-application -> term))
(define primitive-application-operands
  (lambda (pa)
    (rest pa)))

; Ein Lambda-Ausdruck ist eine Liste, deren
; erstes Element das Symbol lambda ist:
(: abstraction? (any -> boolean))
(check-expect (abstraction? '(x y)) #f)
(check-expect (abstraction? '(lambda (x) 5)) #t)
(check-expect (abstraction? '(lambda (x y) 5)) #t)
(check-expect (abstraction? '(+ 1 2)) #f)
(define abstraction?
  (lambda (x)
    (and (pair? x)
         (equal? (first x) 'lambda))))

(define abstraction
  (signature (predicate abstraction?)))

; Variable einer Abstraktion extrahieren
(: abstraction-variable (abstraction -> variable))
(check-expect (abstraction-variable '(lambda (x) 5)) 'x)

(define abstraction-variable
  (lambda (x)
    (first (first (rest x)))))

(define abstraction-variable-rest
  (lambda (x)
    (rest (first (rest x)))))

; Rumpf einer Abstraktion einer Abstraktion extrahieren
(: abstraction-body (abstraction -> term))
(check-expect (abstraction-body '(lambda (x) (+ x 1))) '(+ x 1))

(define abstraction-body
  (lambda (x)
    (first (rest (rest x)))))


(: primitive-operation? (any -> boolean))
(define primitive-operation?
  (lambda (x)
    (equal? x '+)))
  
                              
(define primitive-operation (signature (predicate primitive-operation?)))

; Stelligkeit einer primitiven Operation ermitteln
(: primitive-operation-arity (primitive-operation -> natural))

(check-expect (primitive-operation-arity '+) 2)

(define primitive-operation-arity
  (lambda (op)
    (cond
      ((equal? op '+) 2)
      (else
       (violation "unbekannte primitive Operation")))))

; Eine Instruktion ist eins der folgenden:
; - ein Basiswert
; - eine Variable
; - ap
; - ein primFk
; - eine Abstraktionsinstruktion
(define instruction
  (signature
   (mixed base
          variable
          ap
          prim
          absins)))

; Ein ap hat keine Bestandteile!
(define-record-procedures ap
  make-ap ap?
  ())
(define the-ap (make-ap))

; Ein prim hat:
; - Name
; - Stelligkeit
(define-record-procedures prim
  make-prim prim?
  (prim-name prim-arity))
(: make-prim (symbol natural -> prim))

; Eine absins besteht aus:
; - Variable
; - Code
(define-record-procedures absins
  make-absins absins?
  (absins-variable absins-code))
(: make-absins (variable code -> absins))

; Code ist eine Folge von Instruktionen
(define code
  (signature (list-of instruction)))


; Lambda-Ausdruck compilieren
(: compile (term -> code))

(check-expect (compile '(+ 1 2))
              (list 1 2 (make-prim '+ 2)))

(define compile
  (lambda (t)
    (cond
      ((base? t) (list t))
      ((variable? t) (list t))
      ((application? t)
       (append
        (compile (application-operator t))
        (append-lists
         (map
          (lambda (op)
            (append
             (compile op)
             (list the-ap))) (application-operands t)))))
;       (append
;        (append
;         (compile (application-operator t))
;         (compile (application-operand t)))
;        (list the-ap)))
      ((primitive-application? t)
       (append
        (append-lists
         (map compile (primitive-application-operands t)))
        (let ((op (primitive-application-operator t)))
          (list
           (make-prim
            op
            (primitive-operation-arity (primitive-application-operator t)))))))
      ((abstraction? t)
       (if
        (pair? (abstraction-variable-rest t))
        (list
         (make-absins
          (abstraction-variable t)
          (compile (list 'lambda (abstraction-variable-rest t) (abstraction-body t)))))
        (list
         (make-absins
          (abstraction-variable t)
          (compile (abstraction-body t)))))))))
;      ((abstraction? t)
;       (list
;        (make-absins
;         (abstraction-variable t)
;         (compile (abstraction-body t))))))))

; Listen aneinanderhängen
(: append-lists ((list-of (list-of %a)) -> (list-of %a)))
(check-expect (append-lists '((1 2 3) (4 5) (6 7 8)))
              '(1 2 3 4 5 6 7 8))
(define append-lists
  (lambda (lislis)
    (fold '() append lislis)))
   

; Ein Stack ist eine Folge von Werten
(define stack (signature (list-of value)))

; Eine Umgebung ist eine Menge von Paaren aus V und W - Bindungen
(define environment
  (signature (list-of binding)))

; Eine Bindung ist ein Paar aus Variable und Wert
(define-record-procedures binding
  make-binding binding?
  (binding-variable binding-value))
(: make-binding (variable value -> binding))

; Ein Dump ist eine Folge von Tripeln ... Frames
(define dump (signature (list-of frame)))

; Ein Frame ist ein Tripel aus Stack, Environment und Code
(define-record-procedures frame
  make-frame frame?
  (frame-stack frame-environment frame-code))
(: make-frame (stack environment code -> frame))

; Ein Wert ist ein Basiswert oder eine Closure
(define value
  (signature
   (mixed base closure)))

; Eine Closure ist eine Tripel aus Variable, Code und Environment
(define-record-procedures closure
  make-closure closure?
  (closure-variable closure-code closure-environment))
(: make-closure (variable code environment -> closure))

; Ein Zustand der SECD-Maschine besteht aus:
; - Stack
; - Environment
; - Code
; - Dump
(define-record-procedures secd
  make-secd secd?
  (secd-stack secd-environment secd-code secd-dump))
(: make-secd (stack environment code dump -> secd))

; Einen Schritt der SECD-Maschine berechnen
(: secd-step (secd -> secd))

(define secd-step
  (lambda (st)
    (let ((s (secd-stack st))
          (e (secd-environment st))
          (c (secd-code st))
          (d (secd-dump st)))
      (cond
        ((pair? c)
         (let ((ins (first c)))
           (cond
             ((base? ins)
              (make-secd 
               (make-pair ins s)
               e
               (rest c)
               d))
             ((variable? ins) 
              (make-secd
               (make-pair (lookup e ins) 
                          s)
               e
               (rest c)
               d))
             ((prim? ins)
              (let ((a (prim-arity ins)))
                (make-secd
                 (make-pair
                  (apply-primitive ins (take-reverse s a))
                  (drop s a))
                 e
                 (rest c)
                 d)))
             ((absins? ins)
              (make-secd
               (make-pair
                (make-closure (absins-variable ins)
                              (absins-code ins)
                              e)
                s)
               e
               (rest c)
               d))
             ((ap? ins)
              (let ((cl (first (rest s))))
                (make-secd
                 empty
                 (extend-environment (closure-environment cl)
                                     (closure-variable cl)
                                     (first s))
                 (closure-code cl)
                 (make-pair
                  (make-frame (rest (rest s)) e (rest c))
                  d)))))))
        ((empty? c)
         (let ((fr (first d)))
           (make-secd
            (make-pair (first s)
                       (frame-stack fr))
          (frame-environment fr)
          (frame-code fr)
          (rest d))))))))
    
;                                                                                                                          
;                                                                                                                          
;                                                                                                                          
;                                                     ;;             ;;                       ;;                           
;  ;;;; ;;;;                                           ;              ;                        ;                           
;   ;     ;                                            ;              ;                        ;                           
;   ;  ;  ;   ;;   ;;   ;; ;;;     ;;;; ;     ;;;; ;   ; ;;;      ;;; ;     ;;;     ;; ;;;     ;  ;;;     ;;;     ;; ;;;   
;   ;  ;  ;    ;    ;    ;;   ;   ;    ;;    ;    ;;   ;;   ;    ;   ;;    ;   ;     ;;   ;    ;  ;      ;   ;     ;;   ;  
;   ; ; ; ;    ;    ;    ;    ;   ;         ;          ;    ;   ;     ;   ;     ;    ;    ;    ; ;      ;     ;    ;    ;  
;   ; ; ; ;    ;    ;    ;    ;    ;;;;;    ;          ;    ;   ;     ;   ;;;;;;;    ;    ;    ;;;      ;;;;;;;    ;    ;  
;   ; ; ; ;    ;    ;    ;    ;         ;   ;          ;    ;   ;     ;   ;          ;    ;    ;  ;     ;          ;    ;  
;   ; ; ; ;    ;   ;;    ;    ;   ;     ;    ;     ;   ;    ;    ;   ;;    ;    ;    ;    ;    ;   ;     ;    ;    ;    ;  
;    ;   ;      ;;; ;;  ;;;  ;;;  ;;;;;;      ;;;;;   ;;;  ;;;    ;;; ;;    ;;;;    ;;;  ;;;  ;;  ;;;;    ;;;;    ;;;  ;;; 
;                                                                                                                          
;                                                                                                                          
;                                                                                                                          
;                                                                                                                          
;                                                                                                                          

; Variable in Umgebung nachschauen
(: lookup (environment variable -> value))

(define env1 
  (list (make-binding 'x 5)
        (make-binding 'y 17)))
(check-expect (lookup env1 'x) 5)
(check-expect (lookup env1 'y) 17)

(define lookup
  (lambda (e v)
    (cond
      ((empty? e)
       (violation "Variable nicht in Umgebung"))
      ((pair? e)
       (let ((b (first e)))
         (if (equal? v (binding-variable b))
             (binding-value b)
             (lookup (rest e) v)))))))
   
; Die ersten k Elemente einer Liste weglassen
(: drop ((list-of %a) natural -> (list-of %a)))
   
(check-expect (drop '(1 2 3 4 5) 2) '(3 4 5))

(define drop
  (lambda (lis k)
    (cond
      ((= k 0) lis)
      ((> k 0)
       (drop (rest lis) (- k 1))))))
                
; Primitive Operation anwenden
(: apply-primitive (prim (list-of base) -> base))
    
(check-expect (apply-primitive (make-prim '+ 2) '(3 4)) 7)

(define apply-primitive
  (lambda (p lis)
    (let ((n (prim-name p)))
      (cond
        ((equal? n '+)
         (+ (first lis) (first (rest lis))))
        (else
         (violation "unbekannte primitive Operation"))))))
    
                                          
; Erste k Elemente einer Liste in umgekehrter Reihenfolge
(: take-reverse ((list-of %a) natural -> (list-of %a)))

(check-expect (take-reverse '(1 2 3 4 5) 3) '(3 2 1))
(check-property
 (for-all ((lis (list-of integer))
           (k natural))
   (==> (<= k (length lis))
        (expect (append (reverse (take-reverse lis k))
                        (drop lis k))
                lis))))
(define take-reverse
  (lambda (lis k)
    (letrec ((helper
              (lambda (lis k acc)
                (cond
                  ((= k 0) acc)
                  ((> k 0)
                   (helper
                    (rest lis)
                    (- k 1)
                    (make-pair (first lis) acc)))))))
      (helper lis k '()))))

; Umgebung erweitern
(: extend-environment (environment variable value -> environment))

(check-expect (extend-environment env1 'z 18)
              (make-pair (make-binding 'z 18) env1))
(check-expect (extend-environment env1 'x 18)
              (list (make-binding 'x 18)
                    (make-binding 'y 17)))

(define extend-environment
  (lambda (e v w)
    (make-pair (make-binding v w)
               (remove-from-environment e v))))

; Alle Bindungen einer Variable aus Umgebung entfernen
(: remove-from-environment (environment variable -> environment))

(check-expect (remove-from-environment env1 'x)
              (list (make-binding 'y 17)))
(check-expect (remove-from-environment env1 'y)
              (list (make-binding 'x 5)))

(define remove-from-environment
  (lambda (e v)
    (cond
      ((empty? e) empty)
      ((pair? e) 
       (let ((r
              (remove-from-environment (rest e) v)))

         (if (equal? v (binding-variable (first e)))
             r
             (make-pair (first e) r)))))))
               
; Anfangszustand für SECD-Maschine herstellt
(: secd-start (term -> secd))

(define secd-start
  (lambda (t)
    (make-secd 
     empty
     empty
     (compile t)
     empty)))

; SECD-Maschine bis zum bitteren Ende laufenlassen
(: run-secd (secd -> secd))

(define run-secd
  (lambda (st)
    (if (and (empty? (secd-code st))
             (empty? (secd-dump st)))
        st
        (run-secd (secd-step st)))))