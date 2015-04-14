(ns shokunin.cmr
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

(defn search [url opts]
  (let [defaults {:accept :json }
        opts+    (merge-with merge defaults opts)
        resp     (client/get url opts+)
        body     (client/json-decode (resp :body))]
    body))

(-> (search url opts) (get-in ["feed" "entry"] first))

(defn http-seq [url opts]
  (let [entries (-> (search url opts) (get-in ["feed" "entry"]))]
    ; the last item in the seq should somehow invoke
    ; a subsequent call to get more items if more
    ; results exist. however, the search function only
    ; returns a body... there is no information about
    ; where in the result list we actually are. hmm.
    entries))

(comment
  ; an example
  (def opts { :query-params {:short-name "MOD09A1" :page-num 1 :page-size 5}})
  (def url "https://cmr.earthdata.nasa.gov/search/granules")
  (def s (http-seq url opts))
  (count s))
