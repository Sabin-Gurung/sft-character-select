(ns street-fighter-selection-cljs.sound)

(defonce sound-resources (atom {}))

(defn- stop-audio [audio-tag]
    (. audio-tag pause)
    (set! (.. audio-tag -currentTime) 0))

(defn close-all-audio! []
  (doseq [[k v] @sound-resources]
    (println "Audio: clearing " k ":" v)
    (stop-audio v)))

(defn- get-audio-tag! [resource-id]
  (if-let [audio-tag (@sound-resources resource-id)]
    audio-tag
    (-> (swap! sound-resources assoc resource-id (new js/Audio resource-id))
        (get resource-id))))

(defn play [resource-id]
  (let [audio-tag (get-audio-tag! resource-id)]
    (stop-audio audio-tag)
    (. audio-tag play)))

(defn toggle-pause [resource-id]
  (let [audio-tag (get-audio-tag! resource-id)]
    (if (. audio-tag -paused)
      (. audio-tag play)
      (. audio-tag pause))))

(defn play-bg [resource-id]
  (let [audio-tag (get-audio-tag! resource-id)]
    (set! (.. audio-tag -loop) true)
    (set! (.. audio-tag -volume) 0.4)
    (when (. audio-tag -paused)
      (. audio-tag play))))
