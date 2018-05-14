(ns breakpoint-app.routes
  (:require [clojure.java.io :as io]
            [compojure.route :refer [resources]]
            [compojure.api.sweet :as api]
            [ring.util.response :refer [response]]
            [ring.util.http-response :refer [ok]]
            [breakpoint-app.giphy :as giphy]
            [breakpoint-app.schema :as schema]))

(defonce fake-db (atom {:saved-images []}))

(defn home-routes [endpoint]
  (api/api
    (api/GET "/" []
             (-> "public/index.html"
                 io/resource
                 io/input-stream
                 response
                 (assoc :headers {"Content-Type" "text/html; charset=utf-8"})))
    (api/GET "/api/random" []
             :return [schema/image]
             (ok (giphy/random)))
    (api/GET "/api/search" []
             :query-params [q :- String]
             :return [schema/image]
             (ok (giphy/search q)))
    (api/GET "/api/stored" []
             :return [schema/image]
             (do
               (println "load stored" @fake-db)
               (ok (:saved-images @fake-db))))
    (api/PUT "/api/stored" []
             :body [images [schema/image]]
             (swap! fake-db assoc :saved-images images))
    (resources "/")))
