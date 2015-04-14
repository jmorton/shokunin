(ns shokunin.cmr
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :as string]))

(defn hits-count
  ""
  [url params]
  (let [response (client/head url params)
        cmr-hits (get-in response [:headers "CMR-hits"])]
    (or (Integer/parseInt cmr-hits) 0)))

(defn page-count
  ""
  [url params]
  (let [hits (hits-count url params)
        size (get-in params [:query-params :page-size])]
    (-> hits (/ size) Math/ceil int)))

(defn query
  ""
  [url params]
  (let [defaults {:accept :json }
        params+  (merge-with merge defaults params)
        resp     (client/get url params+)
        body     (client/json-decode (resp :body))]
    body))

(defn http-seq
  ""
  ([url params]
     (let [opts {:last-page (page-count url params)}]
       (http-seq url params opts)))
  ([url {{:keys [page-num page-size]} :query-params :as params}
        {:keys [last-page] :as opts}]
       (let [results     (get-in (query url params) ["feed" "entry"])
             next-params (update-in params [:query-params :page-num] inc)
             more-pages  (< page-num last-page)]
         (lazy-cat results (if more-pages (http-seq url next-params opts))))))

(comment "usage example"
  (let [params  { :query-params {:short-name "MOD09A1" :page-num 1 :page-size 25}}
        url     "https://cmr.earthdata.nasa.gov/search/granules"
        results (http-seq url params)]
    (map #(get % "title") (take 5 results)))
  results)

