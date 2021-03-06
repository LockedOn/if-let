(ns if-let.core)

(defmacro ^{:private true} assert-args
  [& pairs]
  `(do (when-not ~(first pairs)
         (throw (IllegalArgumentException.
                 (str (first ~'&form) " requires " ~(second pairs) " in " ~'*ns* ":" (:line (meta ~'&form))))))
       ~(let [more (nnext pairs)]
          (when more
            (list* `assert-args more)))))

(defmacro if-let*
  "bindings => binding-form test
  If test is true, evaluates then with binding-form bound to the value of
  test, if not, yields else"
  ([bindings then]
   `(if-let* ~bindings ~then nil))
  ([bindings then else & oldform]
   (assert-args
    (vector? bindings) "a vector for its binding"
    (nil? oldform) "1 or 2 forms after binding vector"
    (< 1 (count bindings)) "at least 2 forms in binding vector"
    (= 0 (mod (count bindings) 2)) "even number of forms in binding vector")
   (let [form (bindings 0) tst (bindings 1) more (vec (drop 2 bindings))]
     `(let [temp# ~tst]
        (if temp#
          (let [~form temp#]
            ~(if (seq more)
               `(if-let* ~more ~then ~else)
               then))
          ~else)))))

(defmacro let-pred
  "bindings => binding-form test
  If pred fails - the return the input to the pred."
  [pred bindings & body]
  (assert-args
   (vector? bindings) "a vector for its binding"
   (< 1 (count bindings)) "at least 2 forms in binding vector"
   (= 0 (mod (count bindings) 2)) "even number of forms in binding vector")
  (let [form (bindings 0) tst (bindings 1) more (vec (drop 2 bindings))]
    `(let [temp# ~tst]
       (if (~pred temp#)
         (let [~form temp#]
           ~(if (seq more)
              `(let-pred ~pred ~more ~@body)
              `(do ~@body)))
         temp#))))

(defmacro when-let*
  "bindings => binding-form test
  When test is true, evaluates body with binding-form bound to the value of test"
  [bindings & body]
  (assert-args
   (vector? bindings) "a vector for its binding"
   (< 1 (count bindings)) "at least 2 forms in binding vector"
   (= 0 (mod (count bindings) 2)) "even number of forms in binding vector")
  (let [form (bindings 0) tst (bindings 1) more (vec (drop 2 bindings))]
    `(let [temp# ~tst]
       (when temp#
         (let [~form temp#]
           ~(if (seq more)
              `(when-let* ~more ~@body)
              `(do ~@body)))))))

(defmacro if-some*
  "bindings => binding-form test
   If test is not nil, evaluates then with binding-form bound to the
   value of test, if not, yields else"
  ([bindings then]
   `(if-some* ~bindings ~then nil))
  ([bindings then else & oldform]
   (assert-args
    (vector? bindings) "a vector for its binding"
    (nil? oldform) "1 or 2 forms after binding vector"
    (< 1 (count bindings)) "at least 2 forms in binding vector"
    (= 0 (mod (count bindings) 2)) "even number of forms in binding vector")
   (let [form (bindings 0) tst (bindings 1) more (vec (drop 2 bindings))]
     `(let [temp# ~tst]
        (if (nil? temp#)
          ~else
          (let [~form temp#]
            ~(if (seq more)
               `(if-some* ~more ~then ~else)
               then)))))))

(defmacro when-some*
  "bindings => binding-form test
   When test is not nil, evaluates body with binding-form bound to the
   value of test"
  [bindings & body]
  (assert-args
   (vector? bindings) "a vector for its binding"
   (< 1 (count bindings)) "at least 2 forms in binding vector"
   (= 0 (mod (count bindings) 2)) "even number of forms in binding vector")
  (let [form (bindings 0) tst (bindings 1) more (vec (drop 2 bindings))]
    `(let [temp# ~tst]
       (if (nil? temp#)
         nil
         (let [~form temp#]
           ~(if (seq more)
              `(when-some* ~more ~@body)
              `(do ~@body)))))))

(defmacro pred->
  "When expr is passed by the predicate, threads it into the first form (via ->),
  and when that result is passed by the predicate, through the next etc,
  return the first failed expr"
  [pred expr & forms]
  (let [g (gensym)
        steps (map (fn [step] `(if (~pred ~g) (-> ~g ~step) ~g))
                   forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))

(defmacro pred->>
  "When expr is passed by the predicate, threads it into the first form (via ->>),
  and when that result is passed by the predicate, through the next etc,
  return the first failed expr"
  [pred expr & forms]
  (let [g (gensym)
        steps (map (fn [step] `(if (~pred ~g) (->> ~g ~step)  ~g))
                   forms)]
    `(let [~g ~expr
           ~@(interleave (repeat g) (butlast steps))]
       ~(if (empty? steps)
          g
          (last steps)))))
