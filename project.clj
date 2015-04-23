(defproject shokunin "0.1.0-SNAPSHOT"

  :description "Practice Clojure Daily"

  :url "http://github.com/jmorton/shokunin"

  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.7.0-beta1"]
                 ;[clj-http "1.1.0"]
                 [org.clojure/clojurescript "0.0-3196"]]

  :profiles {:dev {:dependencies [[com.cemerick/austin     "0.1.6"]
                                  [com.cemerick/piggieback "0.2.0"]
                                  [org.clojure/tools.nrepl "0.2.10"]
                                  [weasel "0.6.0-SNAPSHOT"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}

  :source-paths ["src"]

  :aliases {"cljs-watch" ["run" "-m" "clojure.main" "watch.clj"]
            "cljs-repl"  ["run" "-m" "clojure.main" "repl.clj"]})
