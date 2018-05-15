(ns breakpoint-app.animation.animation-events
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-db :animation/toggle-animation-state
  (fn [db]
    (update db :animation-state not)))
