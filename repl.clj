(require 'cljs.core)
(require 'cljs.closure)
(require '[cljs.repl :as repl]
         '[cljs.repl.browser :as browser])

(repl/repl (browser/repl-env)
           :main       'shokunin.core
           :verbose    true
           :output-dir "scripts/out"
           :output-to  "scripts/main.js"
           :watch      "src")
