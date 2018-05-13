(ns breakpoint-app.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :images
 (fn [db]
   (:images db)))

(re-frame/reg-sub
  :search-input
  (fn [db]
    (:search-input db)))

(re-frame/reg-sub
  :saved-images
  :saved-images)

(re-frame/reg-sub
  :image-saved?
  (fn [db [_ id]]
    (->> db
        :saved-images
        (filter #(= (:id %) id))
        (not-empty)
        (boolean))))
