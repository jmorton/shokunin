(ns shokunin.core
  (:require [clojure.browser.repl :as repl]))

(repl/connect "http://localhost:9000/repl")

(defn ahoy [x]
  (str "Ahoy, " x " oy!"))
