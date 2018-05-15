(ns breakpoint-app.animation.animation-core
  (:require [cljs.core.async :as async]
            [re-frame.core :as re-frame])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defn start-animation-loop []
  (go-loop []
    (async/<! (async/timeout 3000))
    (re-frame/dispatch [:animation/toggle-animation-state])
    (recur)))
