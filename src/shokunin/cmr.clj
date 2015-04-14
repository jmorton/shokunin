(ns shokunin.cmr
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [clojure.string :as string]))

(defn search [url params]
  (let [defaults {:accept :json }
        params+  (merge-with merge defaults params)
        resp     (client/get url params+)
        body     (client/json-decode (resp :body))]
    body))

(defn hits
  ""
  [url params]
  (let [response (client/head url params)
        cmr-hits (get-in response [:headers "CMR-hits"])]
    (or (Integer/parseInt cmr-hits) 0)))

(defn search
  ""
  [url params]
  (let [defaults {:accept :json }
        params+  (merge-with merge defaults params)
        resp     (client/get url params+)
        body     (client/json-decode (resp :body))]
    body))

(defn http-seq
  ""
  ;; this doesn't *set* defaults in the map... careful.
  ([url {{:keys [page-num page-size]} :query-params
          :or {page-num 1 page-size 10}
          :as params}]
     (let [last-page (int (Math/ceil (/ (hits url params) page-size)))
           opts {:last-page last-page}]
       (http-seq url params opts)))
  ([url {{:keys [page-num page-size]} :query-params :as params}
        {:keys [last-page] :as opts}]
       (let [entries     (get-in (search url params) ["feed" "entries"])
             next-params (update-in params [:query-params :page-num] inc)]
         (<= page-num last-page)
         (concat entries (lazy-seq (http-seq url next-params opts))))))

; an example
(def params { :query-params {:short-name "MOD09A1" :page-num 1 :page-size 10}})
(def opts {:last-page 10})
(def url "https://cmr.earthdata.nasa.gov/search/granules")
(def result (http-seq url params opts))
(map #(get % "title") (take 2 result))

