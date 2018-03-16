(defproject lockedon/if-let "0.3.0"
            :description "Implementation of `if-let`, `let-pred`, `when-let`, `if-some`, `when-some` macros to allow more than one binding. Also `pred->` and `pred->>`"
            :url "https://github.com/LockedOn/if-let"
            :license {:name "Eclipse Public License"
                      :url "http://www.eclipse.org/legal/epl-v10.html"}
            :profiles {:dev {:dependencies [[org.clojure/clojure "1.9.0"]]}}
            :deploy-repositories [["releases" :clojars]])
