(ns shokunin.fizzbuz)

(def fizz-buzz (let [number (iterate inc 1)
                     fizzer (cycle [nil nil "fizz"])
                     buzzer (cycle [nil nil nil nil "buzz"])]
                 (map #(or (if (or %2 %3) (str %2 %3)) %1)
                      number fizzer buzzer)))

(take 15 fizz-buzz)
