(ns breakpoint-app.animation.animation-events
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-db :animation/toggle-animation-state
  (fn [db]
    (update db :animation/tilt-direction (fn [previous-direction]
                                           (case previous-direction
                                             :left :right
                                             :right :left)))))

(re-frame/reg-event-db :animation/toggle-animation
  (fn [db]
    (update db :animation/enabled? not)))
