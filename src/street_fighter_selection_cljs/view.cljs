(ns street-fighter-selection-cljs.view
  (:require [reagent.core :as r]
            [cljs.core.async :refer [chan <! put! close!]]
            [cljs.core.async :refer-macros [go go-loop]]
            [street-fighter-selection-cljs.algorithm :refer [get-in-mat is-empty? advance ncols nrows]]
            [street-fighter-selection-cljs.data :refer [assets-map]]
            [street-fighter-selection-cljs.sound :refer [play play-bg toggle-pause]]))

(. js/document addEventListener "keydown" #(. % preventDefault))
(defn key-up-chan []
  (let [c (chan)]
    (. js/document addEventListener "keyup" #(put! c %))
    c))

(defn thumbnail-url [fighter-name]
  (str "url('" (get-in assets-map [fighter-name :img]) "')"))

(defn character-banner [character image]
  [:div.character-banner 
   [:h2 "Street Fighter "]
   [:h3 character]
   [:p "Press [P] to play music"]
   [:img {:src image}]])

(defn cell-view [fighter selected?]
  [:td.char-cell {:class (when selected? "selected-cell")
                  :style {:background-image (thumbnail-url fighter)}} 
   [:span fighter]])

(defn grid-view [EV-CHANNEL fighters initial-pos]
  (let [cur-pos (r/atom initial-pos)
        next-char #(let [new-pos (advance fighters @cur-pos %)]
                     (if-not (= @cur-pos new-pos)
                       (do 
                         (play "sounds/switch.mp3")
                         (put! EV-CHANNEL [:hover-character (get-in-mat fighters new-pos)])
                         (reset! cur-pos new-pos))))
        k-chan (key-up-chan)
        _ (go-loop [ev (<! k-chan)] 
                   (when ev
                     (case (.-which ev)
                       80 (do (toggle-pause "sounds/guile-theme.mp3"))
                       37 (next-char :left)
                       38 (next-char :up)
                       39 (next-char :right)
                       40 (next-char :down)
                       nil)
                     (recur (<! k-chan))))]
    (r/create-class
      {:reagent-render (fn []
                        [:div 
                         [:table 
                          [:tbody
                           (doall (for [x (range (nrows fighters))]
                                    ^{:key x}
                                    [:tr.char-row
                                     (doall (for [y (range (ncols fighters))]
                                              (let [fighter (get-in-mat fighters [x y])]
                                                ^{:key [x y]}
                                                [cell-view fighter (= [x y] @cur-pos)])))]))]]])
       :component-will-unmount (fn [cmp] (close! k-chan))})))

(defn about []
  [:div {:style {:color "white"
                 :text-align "center"}}
   [:h4 "About"]
   [:p
    "This is a simple demo application inspired from the kata from code wars : "
    [:a {:href "https://www.codewars.com/kata/58583922c1d5b415b00000ff" :target "_blank"}
     "Street Fighter Selection 2" ]
    [:br]
    "Image assets was downloaded from : " 
    [:a {:href "https://streetfighter.fandom.com/wiki/Street_Fighter_Wiki" :target "_blank"}
     "Street fighter wiki"]
    [:br] [:br]
     "Here Awesome Video"]
    [:iframe
     {:width 560
      :height 315
      :src "https://www.youtube.com/embed/JzS96auqau0"
      :title "Awesome comeback"
      :frameBorder 0
      :allow "accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"}]])
