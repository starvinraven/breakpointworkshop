(ns breakpoint-app.views
  (:require [breakpoint-app.animation.animation-core :as a]
            [breakpoint-app.animation.animation-views :as aw]
            [re-frame.core :refer [subscribe dispatch]]))

(defn- results-item [image]
  [:div.results-item
   {:key (:id image)}
   [:div.results-item-header
    [:i.medium.material-icons.remove-button
     {:on-click #(println "remove")}
     "cancel"]
    [:i.medium.material-icons.add-to-list-button
     {:on-click #(println "save")}
     "add_circle"]]
   [:img {:src (:url image)}]])

(defn- results-box []
  (fn []
    [:div.results-box (map results-item @(subscribe [:images]))]))

(defn animation-toggle []
  [:button.animation-toggle
   {:on-click #(dispatch [:animation/toggle-animation])}
   (str "Toggle animation " (if @(subscribe [:animation/enabled?]) "off" "on"))])

(defn color-change []
  [:button.animation-toggle
   {:on-click #(dispatch [:color-event :black])}
   "Black!"])

(defn main-panel []
  (fn []
    [:div.main-wrapper
     {:class (when (= @(subscribe [:background-color]) :black) "black-background")}
     [aw/animation-header :header]
     [:div.main
      [:h1 "Breakpoint Giphy"]
      [:div.input-container
       [:input.search-input
        {:type        "text"
         :placeholder "This does nothing for now :("
         :value       ""
         :on-change   #(println "text input value" (.-value (.-target %)))}]]
      [:div.input-container
       [:button.random-button
        {:on-click #(dispatch [:load-random])}
        "Load random!"]]
      [results-box]]
     [aw/animation-header :footer]
     [:div.toggle-button-container
      [animation-toggle]
      [color-change]]]))
