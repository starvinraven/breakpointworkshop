(ns breakpoint-app.config
  (:require [environ.core :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.logger :refer [wrap-with-logger]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response]]))

(def giphy-api-key) ; you can set this in the REPL

(defn config []
  {:http-port  (Integer. (or (env :port) 10555))
   :middleware [[wrap-defaults api-defaults]
                wrap-with-logger
                wrap-gzip
                wrap-reload
                wrap-json-response]
   :giphy-api-key (or giphy-api-key (env :giphy-api-key))})
