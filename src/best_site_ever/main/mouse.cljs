(ns best-site-ever.main.mouse
  (:require [best-site-ever.main.state :as state]
            [best-site-ever.main.board :as board]))

(defn- get-button-number [el]
  (->> el
       (.-id)
       (re-seq #"[0-9]")
       (first)
       (int)))

(defn init-events []
  (doseq [el (array-seq (.getElementsByClassName js/document "player-button"))] 
    (.addEventListener el "mousedown" #(do 
                                           (.log js/console "mouse down, " (get-button-number el))
                                           (board/flash el 1)
                                           (.log js/console (state/get-game-stage))))
    (.addEventListener el "mouseup" #(do 
                                       (.log js/console "mouse up, " (get-button-number el))
                                       (board/flash el 0.5)))))
