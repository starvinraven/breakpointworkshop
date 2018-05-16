(ns breakpoint-app.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame :refer [reg-sub]]))

(reg-sub
  :background-color
  (fn [db]
    (:background-color db)))

;;
;; (re-frame/reg-sub
;;  :images
;;  (fn [db]
;;    [] ; this should return something instead
;;    ))
