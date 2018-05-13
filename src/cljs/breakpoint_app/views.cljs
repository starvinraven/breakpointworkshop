(ns breakpoint-app.views
  (:require [re-frame.core :as re-frame]))

(defn- results-item
  [result]
  (println "render" (:url result))
  [:div.results-item
   [:img {:src (:url result)}]])

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
     [:div.input-container
      [:input.search-input
       {:type "text"
        :placeholder "Continuous search!"
        :value @(re-frame/subscribe [:search-input])
        :on-change #(re-frame/dispatch [:update-search-input (.-value (.-target %))])}]]
     [:div.input-container
      [:button.random-button
       {:on-click #(re-frame/dispatch [:load-random-giphy])}
       "Load random!"]]
     [results-box]]))
