(require 'cljs.core)
(require 'cljs.closure)
(require 'cljs.repl.browser)

(cljs.closure/build "src"
  {:main 'shokunin.core
   :output-to "out/main.js"
   :verbose true})

(cljs.repl/repl (cljs.repl.browser/repl-env)
  :watch "src/cljs"
  :output-dir "out")

