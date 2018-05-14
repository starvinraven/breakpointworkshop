(ns breakpoint-app.schema
  (:require [schema.core :as s]))

(def image {:id s/Str
            :url s/Str
            :title s/Str})
