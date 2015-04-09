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
  "Turn an (arbitrary) n-gram into a markov chain entry."
  [[& ts]] {(butlast ts) (list (last (rest ts)))})


(defn word-chain
  "Build a word chain out of word tokens."
  [word-seq]
  (let [word-seq* (concat [:start] word-seq [:end])
        tri-grams (n-gram 3 word-seq*)]
    (apply merge-with concat (map word-link tri-grams))))


(defn sentence-tokens
  "Turn a seq of words into sentences."
  [word-seq]
  (re-seq #"(?:[^\.\?\!]+[\.\?\!]\s*)" word-seq))


(defn word-tokens
  "Turn a sentence into a markov chain ready seq of words."
  [sentence]
  (re-seq #"(?:\w[\w\'\-]*|[\,\.\?\!\:\;])" sentence))

;; still need to count probability of transition somewhere
(-> "a b 1 a b x a b x a b x" word-tokens word-chain)

(defn parse-file
  "Build a word chain from the file contents."
  [path tokenizer parser]
  (with-open [file (clojure.java.io/reader path)]
     (doall (parser (mapcat tokenizer (line-seq file))))))


(defn naive-bayes [& ps]
  (let [prob (apply * ps)
        not-p (apply * (map #(- 1 %) ps))]
    (/ prob (+ prob not-p))))
