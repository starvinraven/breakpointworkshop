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

(reg-event-fx
  :search-giphy
  (fn [{:keys [db]} [_ query]]
    {:db (update db :requests-ongoing inc)
     :http-xhrio {:method :get
                  :uri (str "/api/search?q=" query)
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:replace-images]
                  :on-failure      [:handle-giphy-error]}}))

(reg-event-db
  :add-images
  (fn [db [_ response]]
    (-> db
        (update :requests-ongoing dec)
        (update :images into response))))

(reg-event-db
  :replace-images
  (fn [db [_ response]]
    (-> db
        (update :requests-ongoing dec)
        (assoc :images response))))

(reg-event-db
  :handle-giphy-error
  (fn [db _]
    (update db :requests-ongoing dec)))

(reg-event-fx
  :update-search-input
  (fn [{:keys [db]} [_ value]]
    {:db (assoc db :search-input value)
     :dispatch-debounced {:id :search
                          :timeout 500
                          :dispatch [:search-giphy value]}}))

(reg-event-db
  :remove-from-list
  (fn [db [_ id]]
    (update db :images (fn [images]
                         (remove #(= (:id %) id) images)))))

(defonce debounces (atom {}))

(re-frame/reg-fx
  :dispatch-debounced
  (fn [{:keys [id dispatch timeout]}]
    (js/clearTimeout (@debounces id))
    (swap! debounces assoc id (js/setTimeout
                                (fn []
                                  (re-frame/dispatch dispatch)
                                  (swap! debounces dissoc id))
                                timeout))))
