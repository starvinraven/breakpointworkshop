(ns breakpoint-app.giphy
  (:require [org.httpkit.client :as http]
            [breakpoint-app.config :refer [config]]
            [clojure.data.json :as json]))

(defn map->params
  [m]
  (reduce-kv (fn [acc k v]
               (str
                 acc
                 (if (clojure.string/blank? acc) "?" "&")
                 (name k) "=" v))
             ""
             m))

(defn query-url
  ([endpoint params-map]
   (println "querying" endpoint params-map)
   (str
     "https://api.giphy.com/v1/gifs/"
     endpoint
     (map->params (merge
                    params-map
                    {:apikey (:giphy-api-key (config))}))))
  ([endpoint]
   (query-url
     endpoint
     {})))

(defn- parse-data
  [response]
  (-> @response
      :body
      (json/read-str :key-fn keyword)
      :data))

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

