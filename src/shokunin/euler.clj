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

;; prime number tools, useful for subsequent problems.

;;; a super naive and expensive check
(defn naive-prime? [n]
  (cond
   (< n 2) false
   (= n 2) true
   (even? n) false
   :else (not-any? #(= 0 (rem n %)) (range 3 (Math/sqrt n) 2))))

;; eratosthenes seive. does not work for large primes because the stack
;; gets way too deep.
(defn dirty-sieve
  ([] (sieve (iterate inc 2)))
  ([s]
  (cons (first s)
        (lazy-seq (sieve (filter #(not= 0 (rem % (first s)))
                                 (rest s)))))))
