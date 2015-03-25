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

(defn- get-num-from-element [el]
  (->> el
      (.-id)
      (re-seq #"[0-9]")
      (first)
      (int)))

(defn simon-start []
  (let [elem-seq (state/get-current-sequence)]
    (go-loop [curr 0]
      ;; (.log js/console "haha" curr)
      (let [audio-el (audio/get-audio-from-num (nth elem-seq curr))]
        (.log js/console audio-el)
        (audio/stop-audio-element audio-el))
      (let [audio-el (audio/get-audio-from-num (nth elem-seq curr))]
        ;; (.log js/console audio-el)
        (audio/play-audio audio-el))
      (board/flash (get-element-from-num (nth elem-seq curr) "sequence") 1)
      (<! (timeout 700))
      (board/flash (get-element-from-num (nth elem-seq curr) "sequence") 0.5)
      (<! (timeout 200))
      (if (< (inc curr) (count elem-seq))
        (recur (inc curr)))
        (do
          (state/change-game-state "player-turn")
          ;; (.log js/console "player go lol" (state/get-game-stage))
          (change-message)))))

(defn move-on? [val]
  (= val (nth (state/get-current-sequence) (state/get-next-index-to-press))))

(defn finished-round? []
  (= (state/get-next-index-to-press) (count (state/get-current-sequence))))

(defn check-if-move-on [val]
  (if (not (move-on? val))
    (do (state/change-game-state "lost")
        (change-message)
        (set! (.-innerHTML (.getElementById js/document "score")) 
              (str 0)))
    (state/increase-index)))

(defn check-if-finished-round []
  (if (finished-round?)
    (do (state/set-current-index-to-zero)
        (state/change-game-state "simon-turn")
        (set! (.-innerHTML (.getElementById js/document "score")) 
              (str (count (state/get-current-sequence))))
        (state/add-to-current-sequence (inc (rand-int 4)))
        (change-message)
        (go
          (<! (timeout 2000))
          (simon-start)))))

(defn keyboard-space-logic []
  (if 
      (or (= (state/get-game-stage) "to-begin")
          (= (state/get-game-stage) "lost"))
      (do (state/change-game-state "simon-turn")
         ;; (.log js/console "begin!" (state/get-game-stage))
          (change-message)
          (simon-start))))
      ;;(.log js/console "already begun!")))

(defn mousedown-logic [el]
  #(if (= (state/get-game-stage) "player-turn")
    (do 
      ;; (.log js/console "mouse down, " (get-num-from-element el))
      (let [audio-el (audio/get-audio-from-num (get-num-from-element el))]
        ;; (.log js/console audio-el)
        (audio/stop-audio-element audio-el))
      (board/flash el 1))))
      ;; (.log js/console (state/get-game-stage)))))

(defn mouseup-logic [el]
  #(if (= (state/get-game-stage) "player-turn")
    (do
      (check-if-move-on (get-num-from-element el))
      (check-if-finished-round) 
      ;; (.log js/console "mouse up, " (get-num-from-element el))
      (let [audio-el (audio/get-audio-from-num (get-num-from-element el))]
        ;; (.log js/console audio-el)
        (audio/play-audio audio-el))
      (board/flash el 0.5))))
