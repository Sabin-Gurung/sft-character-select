(ns ^:figwheel-hooks street-fighter-selection-cljs.core
  (:require [goog.dom :as gdom]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [street-fighter-selection-cljs.sound :as sft-sound]
            [street-fighter-selection-cljs.events :as sft-ev]
            [street-fighter-selection-cljs.data :refer [assets-map]]
            [street-fighter-selection-cljs.view :refer [grid-view character-banner about]]))

(defn get-app-element [] (gdom/getElement "app"))

(def g-fighters [[       ""    "Ryu"  "E.Honda"  "Blanka"   "Guile" ""       ]
                 [ "Balrog"    "Ken"  "Chun Li" "Zangief" "Dhalsim" "Sagat"  ]
                 [   "Vega" "T.Hawk" "Fei Long"  "Deejay"   "Cammy" "M.Bison"]])

(defn app []
  [:div 
   (let [fighter (:current-character @sft-ev/app-state)]
     [character-banner fighter (get-in assets-map [fighter :img])])
   [grid-view sft-ev/EVENTCHANNEL g-fighters [0 1]]
   [about]])

(defn mount [el] (rdom/render [app] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

(mount-app-element)

(defn ^:after-load on-reload []
  (sft-sound/close-all-audio!)
  (mount-app-element))
