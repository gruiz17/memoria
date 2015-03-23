(ns best-site-ever.main.mouse
  (:require [best-site-ever.main.state :as state]))

(defn- get-button-number [el]
  (->> el
       (.-id)
       (re-seq #"[0-9]")
       (first)
       (int)))

(defn- aesthetics [el amount]
  (do
    (set! (-> el (.-style) (.-opacity)) (str amount))))

(defn init-events []
  (doseq [el (array-seq (.getElementsByClassName js/document "player-button"))] 
    (.addEventListener el "mousedown" #(do 
                                           (.log js/console "mouse down, " (get-button-number el))
                                           (aesthetics el 1)))
    (.addEventListener el "mouseup" #(do 
                                       (.log js/console "mouse up, " (get-button-number el))
                                       (aesthetics el 0.5)))))
