(ns breakpoint-app.animation.animation-events
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-event-db :animation/toggle-animation-state
  (fn [db]
    (if (:animation/enabled? db)
      (update db :animation/tilt-direction (fn [previous-direction]
                                             (case previous-direction
                                               :left :right
                                               :right :left)))
      db)))
