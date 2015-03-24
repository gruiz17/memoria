(ns best-site-ever.main.keyboard
  (:require [best-site-ever.main.state :as state]))


(defn respond-to-space [k]
  ;(.log js/console (.-keyCode k))
  (if 
    (= (.-keyCode k) 32)
    (if 
        (or (= (state/get-game-stage) "to-begin")
            (= (state/get-game-stage) "lost"))
        (do (state/change-game-state "in-progress")
            (.log js/console "begin!"))
        (.log js/console "already begun!"))))

(defn init-keyboard [] (.addEventListener js/document "keydown" respond-to-space))
