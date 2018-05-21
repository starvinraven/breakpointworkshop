(ns breakpoint-app.routes
  (:require [clojure.java.io :as io]
            [compojure.route :refer [resources]]
            [compojure.api.sweet :as api]
            [ring.util.response :refer [response]]
            [ring.util.http-response :refer [ok]]
            [breakpoint-app.giphy :as giphy]
            [breakpoint-app.schema :as schema]))

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

    (resources "/")))
