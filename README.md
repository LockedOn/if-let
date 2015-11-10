# if-let and Friends

Are a set of macros to allow more than one binding for `if-let`, `when-let`, `if-some` and `when-some`.

These macros are convientenly named `if-let*`, `when-let*`, `if-some*` and `when-some*`. The true branch of these macros are evaluated when all bindings pass the `if`/`some` condition.

## Usage

Install in project.clj

![Clojars Project](http://clojars.org/lockedon/if-let/latest-version.svg)

Usage is similar to their `clojure.core` counterpart.

Require: `[if-let.core :refer :all]` in your namespace.

```clojure
(ns user
    (:require [if-let.core :refer :all]))

(if-let* [a 1
          b 2]
    (+ a b)
    "false branch")
;; => 3

(if-let* [a 1
          b nil]
    (+ a b)
    "false branch")
;; => "false branch"


(when-let* [a 1
            b 2]
    (+ a b))
;; => 3

(when-let* [a nil
            b 2]
    (+ a b))
;; => nil


(if-some* [a 1
           b 2]
    (+ a b)
    "false branch")
;; => 3

(if-some* [a 1
           b nil]
    (+ a b)
    "false branch")
;; => "false branch"


(when-some* [a 1
             b 2]
    (+ a b))
;; => 3

(when-some* [a nil
             b 2]
    (+ a b))
;; => nil
```

## License

Copyright © 2015 LockedOn

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
