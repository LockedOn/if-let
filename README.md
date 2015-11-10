# if-let and Friends

Are a set of macros to allow more than one binding for `if-let`, `when-let`, `if-some` and `when-some`.

These macros are convientenly named `if-let*`, `when-let*`, `if-some*` and `when-some*`. The true branch of these macros are evaluated when all bindings pass the `if`/`some` condition.

## Usage

Install with [![Clojars Project](http://clojars.org/lockedon/if-let/latest-version.svg)]

`[if-let.core :refer :all]`

Usage is similar to their `clojure.core` counterpart.

```clojure
(if-let* [a 1
          b 2]
    (+ a b)
    "false branch")
;; => 3
```

## License

Copyright © 2015 LockedOn

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
