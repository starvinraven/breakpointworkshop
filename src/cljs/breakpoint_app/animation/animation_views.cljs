(ns breakpoint-app.animation.animation-views
  (:require [re-frame.core :as re-frame]))

(defn animation-header []
  (let [animation-state (re-frame/subscribe [:animation/animation-state])]
    (fn []
      [:div.cutout-container
       (->> (range 8)
            (map (fn [idx]
                   [:div.pixel-cutout
                    {:key   (str "pixel-cutout--" idx)
                     :class (str "pixel-cutout--" idx "-" @animation-state)}]))
            (doall))])))
