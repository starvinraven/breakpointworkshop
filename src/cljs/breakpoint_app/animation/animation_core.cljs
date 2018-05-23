(ns breakpoint-app.animation.animation-core
  (:require [cljs.core.async :as async]
            [re-frame.core :as re-frame])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defonce animation-loop-running? (atom false))

(defn start-animation-loop []
  (when-not @animation-loop-running?
    (reset! animation-loop-running? true)
    (go-loop []
      (async/<! (async/timeout 3000))
      (re-frame/dispatch [:animation/toggle-animation-state])
      (recur))))
