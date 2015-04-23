(require 'cljs.core)
(require 'cljs.closure)
(require 'cljs.repl.browser)

(cljs.closure/watch "src"
  {:main      'shokunin.core
   :verbose    true
   :output-dir "scripts/out"
   :output-to  "scripts/main.js" })