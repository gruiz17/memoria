(ns best-site-ever.main.keyboard
  (:require [best-site-ever.main.state :as state]))


(defn respond-to-space [k]
  ;(.log js/console (.-keyCode k))
  (if 
    (and 
       (= (.-keyCode k) 32)
       (= (or (= (state/get-game-stage) "to-begin")
              (= (state/get-game-stage) "lost"))))
    (do (state/change-game-state "in-progress")
        (.log js/console "begin!"))))

(defn init-keyboard [] (.addEventListener js/document "keydown" respond-to-space))
