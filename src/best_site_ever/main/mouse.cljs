(ns best-site-ever.main.mouse
  (:require [best-site-ever.main.state :as state]
            [best-site-ever.main.board :as board]
            [best-site-ever.main.audio :as audio]
            [best-site-ever.main.logic :as logic]))

(defn- get-button-number [el]
  (->> el
      (.-id)
      (re-seq #"[0-9]")
      (first)
      (int)))

(defn init-events []
  (doseq [el (array-seq (.getElementsByClassName js/document "player-button"))] 
    (.addEventListener el "mousedown" (logic/mousedown-logic el))
    (.addEventListener el "mouseup" (logic/mouseup-logic el))))
