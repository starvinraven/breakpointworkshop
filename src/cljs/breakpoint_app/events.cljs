(ns breakpoint-app.events
  (:require [re-frame.core :as re-frame :refer [reg-event-db reg-event-fx]]
            [ajax.core :as ajax]
            [day8.re-frame.http-fx] ; required to initialize http fx handler
            [breakpoint-app.db :as db]))

(reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(reg-event-fx
  :load-random-giphy
  (fn [{:keys [db]} _]
    {:db   (update db :requests-ongoing inc)
     :http-xhrio {:method          :get
                  :uri             "/api/random"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:add-images]
                  :on-failure      [:handle-giphy-error]}}))

(reg-event-db
  :add-images
  (fn [db [_ response]]
    (-> db
        (update :requests-ongoing dec)
        (update :images into response))))

(reg-event-db
  :handle-giphy-error
  (fn [db _]
    (update db :requests-ongoing dec)))

(reg-event-db
  :update-search-input
  (fn [db [_ value]]
    (assoc db :search-input value)))
