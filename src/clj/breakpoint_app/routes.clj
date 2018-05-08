(ns breakpoint-app.routes
  (:require [clojure.java.io :as io]
            [compojure.route :refer [resources]]
            [compojure.api.sweet :as api]
            [ring.util.response :refer [response]]
            [ring.util.http-response :refer [ok]]
            [breakpoint-app.giphy :as giphy]))

(defn home-routes [endpoint]
  (api/api
    (api/GET "/" []
             (-> "public/index.html"
                 io/resource
                 io/input-stream
                 response
                 (assoc :headers {"Content-Type" "text/html; charset=utf-8"})))
    (api/GET "/api/random" []
             (ok (giphy/random)))
    (api/GET "/api/search" []
             :query-params [q :- String]
             (ok (giphy/search q)))

    (resources "/")))
