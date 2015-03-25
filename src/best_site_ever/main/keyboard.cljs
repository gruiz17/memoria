(ns best-site-ever.main.keyboard
  (:require [best-site-ever.main.state :as state]
            [best-site-ever.main.logic :as logic]))

(defn respond-to-space [k]
  ;(.log js/console (.-keyCode k))
  (if 
    (= (.-keyCode k) 32)
    (logic/keyboard-space-logic)))

(defn init-keyboard [] (.addEventListener js/document "keydown" respond-to-space))
