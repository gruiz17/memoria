(ns best-site-ever.main.logic
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [best-site-ever.main.state :as state]
            [best-site-ever.main.board :as board]
            [best-site-ever.main.audio :as audio]
            [cljs.core.async :refer [put! chan <! timeout]]))

(def state-mappings
  {:to-begin "press space to start."
   :simon-turn "watch carefully..."
   :player-turn "go!"
   :lost "you lose. press space to play again."})

(defn change-message []
  (set! (.-innerHTML (.getElementById js/document "top-message")) 
        (state-mappings (keyword (state/get-game-stage)))))

(defn get-element-from-num [id-num who]
  (.getElementById js/document (str who "-" id-num)))

(defn simon-start []
  (let [elem-seq (state/get-current-sequence)]
    (go-loop [curr 0]
      (.log js/console "haha" curr)
      (let [audiostr (audio/get-audio-from-num (nth elem-seq curr))]
        (.log js/console audiostr)
        (audio/stop-audio-element audiostr))
      (let [audiostr (audio/get-audio-from-num (nth elem-seq curr))]
        (.log js/console audiostr)
        (audio/play-audio audiostr))
      (board/flash (get-element-from-num (nth elem-seq curr) "sequence") 1)
      (<! (timeout 500))
      (board/flash (get-element-from-num (nth elem-seq curr) "sequence") 0.5)
      (<! (timeout 200))
      (if (< (inc curr) (count elem-seq))
        (recur (inc curr)))
        (do 
          (state/change-whose-turn "player-turn")
          (state/change-game-state "player-turn")
          (.log js/console "player go lol" (state/get-game-stage))
          (change-message)))))

(defn keyboard-space-logic []
  (if 
      (or (= (state/get-game-stage) "to-begin")
          (= (state/get-game-stage) "lost"))
      (do (state/change-game-state "simon-turn")
          (.log js/console "begin!" (state/get-game-stage))
          (change-message)
          (simon-start))
      (.log js/console "already begun!")))

