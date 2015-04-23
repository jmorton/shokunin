(require 'cljs.core)
(require 'cljs.closure)
(require '[cljs.repl :as repl]
         '[cljs.repl.browser :as browser])

(repl/repl (browser/repl-env)
           :main 'shokunin.core
           :verbose    true
           :output-dir "scripts/out"
           :output-to  "scripts/main.js"
           :watch "src")

;; emacs support
(comment
  (require 'weasel.repl.websocket)
  (cemerick.piggieback/cljs-repl
    :repl-env (weasel.repl.websocket/repl-env :ip "127.0.0.1" :port 9001)))
