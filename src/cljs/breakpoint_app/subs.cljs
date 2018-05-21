(ns breakpoint-app.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame :refer [reg-sub]]))

(reg-sub
  :background-color
  (fn [db]
    (:background-color db)))

(reg-sub
 :images
 (fn [db]
   (:images db)))

(reg-sub
  :search-input
  (fn [db]
    (:search-input db)))
