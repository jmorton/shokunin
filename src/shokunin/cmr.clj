;; ## Common Metadata Repository Clojure Client
;;
;; The Common Metadata Repository (CMR) is an earth science metadata repository
;; for the NASA Earth Observing System Data and Information System (EOSDIS)
;; holdings.
;;
;; This library aims to make working with the CMR API a joy.  Moreover, it aims
;; to make it easy to both find and obtain data.
;;
;; (find-granules :short-name "MOD09A1")
;;
(ns shokunin.cmr
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]))

(log/debug "Mhmm")

(defn query
  "Get a page of search results from CMR."
  [url params]
  (let [defaults {:accept :json }
        params+  (merge-with merge defaults params)
        resp     (client/get url params+)
        body     (client/json-decode (resp :body))]
    body))


(defn query-seq
  "Get a lazy seq of search results. Defaults to pages of 100 by default."
  ([url {{:keys [page-num page-size]
          :or   {page-num 1 page-size 100}} :query-params :as params}]
       (let [results       (seq (get-in (query url params) ["feed" "entry"]))
             next-params   (update-in params [:query-params :page-num] inc)]
         (lazy-cat results (if results (query-seq url next-params))))))


(defn find-granules
  "Search CMR granules, returning a lazy set of results."
  [& {:as params}]
  (let [granules "https://cmr.earthdata.nasa.gov/search/granules"
        defaults {:page-num 1 :page-size 100}
        params+  {:query-params (merge defaults params)}]
  (query-seq granules params+)))


(defn find-collections
  "Search CMR collections, returning a lazy set of results."
  [& {:keys [page-num page-size]
      :or   {page-num 1 page-size 100}
      :as   params}]
  (let [granules "https://cmr.earthdata.nasa.gov/search/collections"
        defaults {:page-num 1 :page-size 100}
        params+  {:query-params (merge defaults params)}]
  (query-seq granules params+)))

(def cs1 (find-collections :archive-center "LPDAAC"))
(-> (first cs1) (get "title"))

(log/warn "Warning")
(log/info "Informational logging.")
(log/debug "Dubgging logging")


