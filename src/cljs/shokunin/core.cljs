(ns shokunin.core
  (:require [clojure.browser.repl :as repl]))

(defonce conn
  (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(defn ahoy [x]
  (str "Ahoy, " x))
