(ns shokunin.cmr
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(def opts {:accept :json
           :query-params {:short-name "MOD09A1"}})
(def resp (client/get "https://cmr.earthdata.nasa.gov/search/granules" opts))

(-> resp :headers :cmr-hits)

(def body (client/json-decode (:body resp)))

(def results (get-in body ["feed" "entry"]))

(first (get (first results) "links"))
