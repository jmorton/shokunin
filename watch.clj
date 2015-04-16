(require 'cljs.closure)

(cljs.closure/watch "src"
  {:main 'shokunin.core
   :output-to "out"})
