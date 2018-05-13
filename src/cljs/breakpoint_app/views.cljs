(ns breakpoint-app.views
  (:require [re-frame.core :refer [subscribe dispatch]]))

(defn- results-item [image]
  [:div.results-item
   [:div.results-item-header
    [:i.medium.material-icons.remove-button
     {:on-click #(dispatch [:remove-from-list (:id image)])}
     "cancel"]
    (when-not @(subscribe [:image-saved? (:id image)])
      [:i.medium.material-icons.add-to-list-button
       {:on-click #(dispatch [:add-to-saved image])}
       "add_circle"])]
   [:img {:src (:url image)}]])


(defn- results-box []
  (let [results (subscribe [:images])]
    (fn []
      (into
        [:div.results-box]
        (map results-item @results)))))

(defn- saved-item [image]
  [:div.results-item
   [:img {:src (:url image)}]])

(defn- saved-box []
  (let [saved-images (subscribe [:saved-images])]
    (fn []
      (when (not-empty @saved-images)
        [:div.saved-images
         [:h2 "Your saved images:"]
         (into
           [:ul.results-box]
           (map saved-item @saved-images))]))))

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
       {:on-click #(dispatch [:clear-list])}
       "Clear!"]
      [:button.random-button
       {:on-click #(dispatch [:load-random-giphy])}
       "Load random!"]]
     [results-box]
     [saved-box]]))
