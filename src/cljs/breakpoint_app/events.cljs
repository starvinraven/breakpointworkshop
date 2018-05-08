(ns breakpoint-app.events
  (:require [re-frame.core :as re-frame]
            [breakpoint-app.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
