(ns breakpoint-app.views
  (:require [re-frame.core :refer [subscribe dispatch]]))

(defn- results-item [result]
  [:div.results-item
   [:div.results-item-header
    [:i.medium.material-icons.remove-button
     {:on-click #(dispatch [:remove-from-list (:id result)])}
     "cancel"]
    [:i.medium.material-icons.add-to-list-button
     {:on-click #(dispatch [:add-to-saved (:id result)])}
     "add_circle"]]
   [:img {:src (:url result)}]])

(defn- results-box []
  (let [results (subscribe [:images])]
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
        :value @(subscribe [:search-input])
        :on-change #(dispatch [:update-search-input (.-value (.-target %))])}]]
     [:div.input-container
      [:button.random-button
       {:on-click #(dispatch [:load-random-giphy])}
       "Load random!"]]
     [results-box]]))
