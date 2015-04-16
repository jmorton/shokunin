(defproject shokunin "0.1.0-SNAPSHOT"
  :description "Practice Clojure Daily"
  :url "http://github.com/jmorton/shokunin"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.7.0-beta1"]
                 [clj-http "1.1.0"]
                 [org.clojure/clojurescript "0.0-3196"]]
  :cljsbuild {:builds [{:source-paths ["src/cljs"]
                        :compiler {:optimizations :none
                        :pretty-print true
                        :output-dir "out"
                        :output-to "out/main.js"}}]})
