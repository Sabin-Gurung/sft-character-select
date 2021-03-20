(ns street-fighter-selection-cljs.events
  (:require [cljs.core.async :refer [chan <! put!]]
            [cljs.core.async :refer-macros [go]]
            [reagent.core :as r]
            ))

(defonce app-state (r/atom {:current-character "Ryu"}))

(def EVENTCHANNEL (chan))

(def event-handlers
  {:hover-character (fn [c-name] 
                      (do (swap! app-state assoc  :current-character c-name)
                          (println "you just hovered" c-name)))})

(go
  (while true
    (let [[id data] (<! EVENTCHANNEL)]
      ((get event-handlers id #(println "unhandled event" id data)) data))))
