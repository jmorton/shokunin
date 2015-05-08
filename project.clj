(defproject shokunin "0.1.0-SNAPSHOT"

  :description "Practice Clojure Daily"

  :url "http://github.com/jmorton/shokunin"

  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.7.0-beta1"]
                 [org.clojure/clojurescript "0.0-3211"]]

  :profiles {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.10"]]}}

  :source-paths ["src"]

  :aliases {"cljs-watch" ["run" "-m" "clojure.main" "watch.clj"]
            "cljs-repl"  ["run" "-m" "clojure.main" "repl.clj"]})
