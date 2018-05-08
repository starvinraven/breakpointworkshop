(ns cljs.user
  (:require [breakpoint-app.core]
            [breakpoint-app.system :as system]))

(def go system/go)
(def reset system/reset)
(def stop system/stop)
(def start system/start)
