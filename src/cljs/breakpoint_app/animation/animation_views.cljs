(ns breakpoint-app.animation.animation-views
  (:require [re-frame.core :as re-frame :refer [subscribe]]))

(defn animation-header [type]
  (let [tilt-direction (subscribe [:animation/tilt-direction])]
    (fn [type]
      [:div
       {:class (if (= type :header)
                 "cutout-header-container"
                 "cutout-footer-container")}
       (->> (range 8)
            (map (fn [idx]
                   [:div.pixel-cutout
                    {:key   (str "pixel-cutout--" idx)
                     :class (when true
                              (str "pixel-cutout--" idx "-" (name @tilt-direction)))}]))
            (doall))])))
