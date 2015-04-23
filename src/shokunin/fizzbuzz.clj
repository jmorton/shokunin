(ns shokunin.fizzbuzz)

(def fizz-buzz-seq (let [number (iterate inc 1)
                     fizzer (cycle [nil nil "fizz"])
                     buzzer (cycle [nil nil nil nil "buzz"])]
                 (map #(or (if (or %2 %3) (str %2 %3)) %1)
                      number fizzer buzzer)))

(def fizz-buzz-fn
  (some-fn #(and (= (rem % 3) 0) (= (mod % 5) 0) "FizzBuzz")
           #(and (= (rem % 3) 0) "Fizz")
           #(and (= (rem % 5) 0) "Buzz")))


