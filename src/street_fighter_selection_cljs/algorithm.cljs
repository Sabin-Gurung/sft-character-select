(ns street-fighter-selection-cljs.algorithm)

(defn nrows [mat] (count mat))
(defn ncols [mat] (count (first mat)))
(defn get-in-mat [arr [x y]] ((arr x) y))
(defn is-empty? [arr pos] (= "" (get-in-mat arr pos)))
(defn is-hor-move? [move] (some? (#{:left :right} move)))

(defn advance-hor [fighters [x y] move-y]
  (let [adv-fn #(mod (+ %1 move-y) (ncols fighters))]
    (loop [new-y (adv-fn y)]
      (if-not (is-empty? fighters [x new-y])
        [x new-y]
        (recur (adv-fn new-y))))))

(defn advance-ver [fighters [x y] move-x]
  (let [new-x (+ x move-x)]
    (if (and (< -1 new-x (nrows fighters)) (not (is-empty? fighters [new-x y])))
      [new-x y]
      [x y])))

(def move-cmd-map
  {:left (fn [fighters pos] (advance-hor fighters pos -1))
   :right (fn [fighters pos] (advance-hor fighters pos 1))
   :up (fn [fighters pos] (advance-ver fighters pos -1))
   :down (fn [fighters pos] (advance-ver fighters pos 1))})

(defn advance [fighters pos move]
  (if-let [move-fn (get move-cmd-map move)]
    (move-fn fighters pos)
    pos))

; (defn simulate-moves-1 [fighters pos moves]
;   (->> [fighters pos moves]
;        (iterate (fn [[f p [m_ & _m]]] (if m_ [f (advance f p m_) _m])))
;        (take-while some?)
;        (map #(get-in-mat fighters (second %)))))

; (-> [[       ""    "Ryu"  "E.Honda"  "Blanka"   "Guile" ""       ]
;      [ "Balrog"    "Ken"  "Chun Li" "Zangief" "Dhalsim" "Sagat"  ]
;      [   "Vega" "T.Hawk" "Fei Long"  "Deejay"   "Cammy" "M.Bison"]]
;     (simulate-moves-1 [1 1] [:left :left :left :down :down :up :up :left :down])
;     (println))
