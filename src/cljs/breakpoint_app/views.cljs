(ns breakpoint-app.views
  (:require [re-frame.core :as re-frame]))

(defn- results-item
  [result]
  [:img {:src (:image_url result)}])

(defn- results-box []
  (let [results (re-frame/subscribe [:images])]
    (fn []
      (into
        [:div.results-box]
        (map results-item @results)))))

(defn main-panel []
  (fn []
    [:div.main
     [:h1 "Breakpoint Giphy"]
     [:div
      [:input
       {:type "text"
        :placeholder "This does nothing for now"
        :value @(re-frame/subscribe [:search-input])
        :on-change #(re-frame/dispatch [:update-search-input (.-value (.-target %))])}]]
     [:div
      [:button
       {:on-click #(re-frame/dispatch [:load-random-giphy])}
       "Load random!"]]
     [results-box]]))
