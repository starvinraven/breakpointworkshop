(ns breakpoint-app.animation.animation-views
  (:require [re-frame.core :as re-frame]))

(defn animation-header [type]
  (let [tilt-direction (re-frame/subscribe [:animation/tilt-direction])
        enabled?       (re-frame/subscribe [:animation/enabled?])]
    (fn [type]
      [:div
       {:class (if (= type :header)
                 "cutout-header-container"
                 "cutout-footer-container")}
       (->> (range 8)
            (map (fn [idx]
                   [:div.pixel-cutout
                    {:key   (str "pixel-cutout--" idx)
                     :class (when @enabled?
                              (str "pixel-cutout--" idx "-" (name @tilt-direction)))}]))
            (doall))])))

(defn animation-toggle []
  [:button.animation-toggle
   {:on-click #(re-frame/dispatch [:animation/toggle-animation])}
   "Toggle animation"])
