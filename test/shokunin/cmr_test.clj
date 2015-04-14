(ns shokunin.cmr-test
  (:require [clojure.test :refer :all]
            [shokunin.cmr :refer :all]
            [clj-http.fake :as fake]
            :reload))

(deftest test-cmr-client
  (let [url    "https://cmr.earthdata.nasa.gov/search/granules"
        params {:query-params {:page-size 5}}]
    ;; fake routes needs to match the query parameters exactly (if the are given)
    (fake/with-fake-routes {{:address "https://cmr.earthdata.nasa.gov/search/gran1ules"
                             :query-params {:page-size 5}}
                            {:head (fn [req] {:status 200
                                                  :body ""
                                                  :headers { "CMR-hits" "10"}})}}
      (testing "the count"
        (print url)
        (is (= 10 (hits-count url params)))
        (is (= 2 (page-count url params)))))))
