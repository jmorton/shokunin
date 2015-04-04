(ns shokunin.word
  (:require [clojure.string :refer [lower-case split-lines]]))


(defn term-frequency
  "Count occurrences of terms in a sequence of string."
  [lines stop-set]
  (let [tokenize (partial re-seq #"(?:\w[\w\-\']+)")
        non-stop (partial remove stop-set)
        parse    (comp frequencies non-stop tokenize lower-case)
        tally    (map parse lines)
        adder    (partial merge-with +)]
    (reduce adder tally)))


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

(example)
