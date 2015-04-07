(ns shokunin.euler)

;; sum of multiples of 3 and 5 < 1000
;; https://projecteuler.net/problem=1

(defn multiple-seq [n]
  (iterate (partial + n) n))

(let [x3 (take-while #(< % 1000) (multiple-seq 3))
      x5 (take-while #(< % 1000) (multiple-seq 5))
      xs (clojure.set/union (set x3) (set x5))]
  (apply + xs))


;; sum of even fibonacci numbers below 4 million
;; https://projecteuler.net/problem=2

(defn fib-seq
  ([] (fib-seq 1 1))
  ([x y] (cons x (lazy-seq (fib-seq y (+ x y))))))

(def answer-2 (reduce + (filter even? (take-while (fn [x] (< x 4000000)) (fib-seq)))))
