(ns breakpoint-app.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [breakpoint-app.events]
            [breakpoint-app.subs]
            [breakpoint-app.views :as views]
            [breakpoint-app.config :as config]
            [breakpoint-app.animation.animation-events]
            [breakpoint-app.animation.animation-subs]
            [breakpoint-app.animation.animation-core :as a]))

(enable-console-print!)

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn render []
  (re-frame/dispatch-sync [:initialize-db])
  ;(re-frame/dispatch [:load-random-giphy])
  (dev-setup)
  (mount-root)
  (a/start-animation-loop))
