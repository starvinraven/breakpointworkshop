(ns breakpoint-app.giphy
  (:require [org.httpkit.client :as http]
            [breakpoint-app.config :refer [config]]
            [clojure.data.json :as json]
            [ring.util.codec :as codec]))

(defn map->params
  [m]
  (reduce-kv (fn [acc k v]
               (str
                 acc
                 (if (clojure.string/blank? acc) "?" "&")
                 (name k) "=" (codec/url-encode v)))
             ""
             m))

(defn query-url
  ([endpoint params-map]
   (str
     "https://api.giphy.com/v1/gifs/"
     endpoint
     (map->params (merge
                    params-map
                    {:limit 5
                     :apikey (:giphy-api-key (config))}))))
  ([endpoint]
   (query-url
     endpoint
     {})))

(defn- parse-image
  [datum]
  {:title (:title datum)
   :id (:id datum)
   :url (-> datum :images :fixed_width :url)})

(defn- parse-data
  [response]
  (let [json (-> @response
                 :body
                 (json/read-str :key-fn keyword)
                 :data)]
    (map
      parse-image
      (if (sequential? json)
        json
        [json]))))

(defn search
  [query-text]
  (-> (query-url "search" {:q query-text})
      (http/get)
      (parse-data)))

(defn random
  []
  (-> (query-url "random")
      (http/get)
      (parse-data)))

