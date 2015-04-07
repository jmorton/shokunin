(ns shokunin.fizzbuz)

(def fizz-buzz (let [fizzer (cycle [nil nil "fizz"])
                     buzzer (cycle [nil nil nil nil "buzz"])]
                 (map str fizzer buzzer)))
