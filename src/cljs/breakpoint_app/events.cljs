(ns breakpoint-app.events
  (:require [re-frame.core :as re-frame :refer [reg-event-db reg-event-fx]]
            [ajax.core :as ajax]
            [day8.re-frame.http-fx] ; required to initialize http fx handler
            [breakpoint-app.db :as db]))

(reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(reg-event-db
  :color-event
  (fn [db [_ color]]
    (assoc db :background-color color)))

;; ;Used in ex. 2:
(reg-event-fx
  :load-random
  (fn [{:keys [db]} _]
    {:http-xhrio {:method          :get
                  :uri             "/api/random"
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [:add-images]}
     :db         db}))

(reg-event-db
  :add-images
  (fn [db [_ images]]
    (update db :images into images)))

;; ;Used in ex. 3:
;; (defonce debounces (atom {}))
;; (re-frame/reg-fx
;;   :dispatch-debounced
;;   (fn [{:keys [id dispatch timeout]}]
;;     (js/clearTimeout (@debounces id))
;;     (swap! debounces assoc id (js/setTimeout
;;                                 (fn []
;;                                   (re-frame/dispatch dispatch)
;;                                   (swap! debounces dissoc id))
;;                                 timeout))))
