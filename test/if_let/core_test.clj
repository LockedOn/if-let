(ns if-let.core-test
  (:require [clojure.test :refer :all]
            [if-let.core :refer :all]))

(defn exception
  "Use this function to ensure that execution of a program doesn't
  reach certain point."
  []
  (throw (new Exception "Exception which should never occur")))

(deftest test-when-let
  "Modified from clojure.core"
  (are [x y] (= x y)
       1 (when-let* [a 1]
           a)
       2 (when-let* [[a b] '(1 2)]
           b)
       nil (when-let* [a false]
             (exception))
       ))

(deftest test-if-let
  "Modified from clojure.core"
  (are [x y] (= x y)
       1 (if-let* [a 1]
           a)
       2 (if-let* [[a b] '(1 2)]
           b)
       nil (if-let* [a false]
             (exception))
       1 (if-let* [a false]
           a 1)
       1 (if-let* [[a b] nil]
             b 1)
       1 (if-let* [a false]
           (exception)
           1)
       ))

(deftest test-if-some
  "Modified from clojure.core"
  (are [x y] (= x y)
       1 (if-some* [a 1] a)
       false (if-some* [a false] a)
       nil (if-some* [a nil] (exception))
       3 (if-some* [[a b] [1 2]] (+ a b))
       1 (if-some* [[a b] nil] b 1)
       1 (if-some* [a nil] (exception) 1)))

(deftest test-when-some
  "Modified from clojure.core"
  (are [x y] (= x y)
       1 (when-some* [a 1] a)
       2 (when-some* [[a b] [1 2]] b)
       false (when-some* [a false] a)
       nil (when-some* [a nil] (exception))))