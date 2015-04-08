(ns shokunin.word
  (:require [clojure.string :refer [lower-case split-lines]]))

(def tokenize (partial re-seq #"(?:\w[\w\-\']*)"))

(defn term-frequency
  "Count occurrences of terms in a sequence of string."
  ([lines]
   (term-frequency lines #{}))
  ([lines stop-set]
   (let [tokenize (partial re-seq #"(?:\w[\w\-\']+)")
         non-stop (partial remove stop-set)
         parse    (comp frequencies non-stop tokenize lower-case)
         tally    (map parse lines)
         adder    (partial merge-with +)]
     (reduce adder tally))))


(defn file-term-frequency
  "Count occurrences of terms in a file.  Does not load
   entire file into memory."
  [term-path stop-path]
  (with-open [file (clojure.java.io/reader term-path)]
    (let [stop-set (-> stop-path slurp split-lines set)]
    (term-frequency (line-seq file) stop-set))))


(defn example []
  (let [tf (file-term-frequency "resources/01/declaration.txt"
                                "resources/01/stop.txt")]
    (println (take 10 (sort-by last > tf)))
    (println (take 10 (sort-by first compare tf)))))


(defn n-gram
  "Um. Create n-gram from a word seq."
  [n word-seq]
  (partition-all n 1 word-seq))


(defn word-link
  "Turn a tri-gram into a word-chain entry."
  [[t1 t2 t3]]
  {[t1 t2] (if t3 #{t3} #{})})


(defn word-chain
  "Use everything but the last word in an n-gram as a key to the set of words that follow them."
  [n word-seq]
  (let [tri-grams (n-gram 3 word-seq)]
    (apply merge-with clojure.set/union (map word-link tri-grams))))

(word-chain 3 (tokenize (slurp "resources/01/declaration.txt")))

