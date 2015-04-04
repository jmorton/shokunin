(ns shokunin.word-test
  (:require [clojure.test :refer :all]
            [shokunin.word :refer :all]
            :reload))


(deftest frequency-test

  (testing "one line, no stop words"
    (let [tf (term-frequency ["foo" "bar" "baz"])]
      (is (= tf {"foo" 1 "bar" 1 "baz" 1}))))

  (testing "multipe lines, no stop words"
    (let [tf (term-frequency ["foo bar baz" "Foo Bar" "FOO"])]
      (is (= tf {"foo" 3 "bar" 2 "baz" 1}))))

  (testing "ignore stop words"
    (let [tf (term-frequency ["there can be only one"]
                             #{"there" "can" "be"})]
      (is (= #{"only" "one"} (set (keys tf)))))))

(deftest file-term-frequency-test
  (let [tf (file-term-frequency "resources/01/declaration.txt"
                                "resources/01/stop.txt")
        tf-most (sort-by last > tf)]
    (is (= (first tf-most) ["us" 11]))))
