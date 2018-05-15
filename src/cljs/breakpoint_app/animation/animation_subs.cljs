(ns breakpoint-app.animation.animation-subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub :animation/animation-state
  (fn [db]
    (:animation-state db)))
