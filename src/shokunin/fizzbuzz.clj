(ns shokunin.fizzbuz)

(def fizzer (cycle [nil nil "fizz"]))

(def buzzer (cycle [nil nil nil nil "buzz"]))

(defn fizz-buzz [n]
  (let [fs (take n fizzer)
        bs (take n buzzer)]
    (map str fs bs)))

(fb 15)
