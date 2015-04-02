(ns shokunin.words
  (:require [clojure.string :refer [lower-case split-lines]]))


(defn term-frequency
  "Count occurrences of terms in a file."
  [file stop-set]
  (with-open [rdr (clojure.java.io/reader file)]
    (let [tokenize (partial re-seq #"(?:\w[\w\-\']+)")
          non-stop (partial remove stop-set)
          parse    (comp frequencies non-stop tokenize lower-case)
          tally    (map parse (line-seq rdr))
          adder    (partial merge-with +)]
      (reduce adder tally))))


(def stop-set (-> "resources/01/stop.txt" slurp split-lines set))
(def freq-map (term-frequency "resources/01/declaration.txt" stop-set))


(take 10 (sort-by last > freq-map))
(take 10 (sort-by first compare freq-map))
