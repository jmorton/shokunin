(require 'cljs.core)
(require 'cljs.closure)
(require 'cljs.repl.browser)

(cljs.closure/build "src"
  {:main 'shokunin.core
   :verbose    true
   :output-dir "scripts/out"
   :output-to  "scripts/main.js" })

(cljs.repl/repl (cljs.repl.browser/repl-env)
  :watch "src"
  :output-dir "scripts/out")

;; emacs support
(comment
  (require 'weasel.repl.websocket)
  (cemerick.piggieback/cljs-repl
    :repl-env (weasel.repl.websocket/repl-env :ip "127.0.0.1" :port 9001)))