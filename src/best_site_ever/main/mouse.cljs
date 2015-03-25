(ns best-site-ever.main.mouse
  (:require [best-site-ever.main.state :as state]
            [best-site-ever.main.board :as board]
            [best-site-ever.main.audio :as audio]))

(defn- get-button-number [el]
  (->> el
      (.-id)
      (re-seq #"[0-9]")
      (first)
      (int)))

(defn init-events []
  (doseq [el (array-seq (.getElementsByClassName js/document "player-button"))] 
    (.addEventListener el "mousedown" 
      #(if (= (state/get-game-stage) "player-turn")
        (do 
        (.log js/console "mouse down, " (get-button-number el))
        (let [audiostr (audio/get-audio-from-num (get-button-number el))]
          (.log js/console audiostr)
          (audio/stop-audio-element audiostr))
        (board/flash el 1)
        (.log js/console (state/get-game-stage)))))
    (.addEventListener el "mouseup" 
      #(if (= (state/get-game-stage) "player-turn")
        (do 
          (.log js/console "mouse up, " (get-button-number el))
          (let [audiostr (audio/get-audio-from-num (get-button-number el))]
            (.log js/console audiostr)
            (audio/play-audio audiostr))
          (board/flash el 0.5))))))
