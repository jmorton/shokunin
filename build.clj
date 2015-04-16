(require 'cljs.closure)

(cljs.closure/build "src"
  {:main      'shokunin.core
   :output-to "out/main.js" })
