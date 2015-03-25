(ns best-site-ever.main.audio)

(def audio-map
  {
   :1 "D4" 
   :2 "G4"
   :3 "B4"
   :4 "D5"
   })

(defn get-audio-from-num [n]
  (->> n
       (str)
       (keyword)
       (audio-map)
       (.getElementById js/document)))

(defn stop-audio-element [a]
	(do
   		(.pause a)
     	(set! (.-currentTime a) 0)))

(defn play-audio [a]
	(.play a))