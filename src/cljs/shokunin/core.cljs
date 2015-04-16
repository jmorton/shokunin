(ns shokunin.core
  (:require [clojure.browser.repl :as repl]))

(enable-console-print!)
(print "Ahoy!")

(defonce conn
  (repl/connect "http://localhost:9000/repl"))

(defn ahoy [x]
  (str "Ahoy, " x))
