(ns best-site-ever.main.state
  (:require [clojure.string :as string]))

(def game-stage (atom "to-begin")) ;; "to-begin", "simon-turn", "player-turn", "lost"
(def current-sequence (atom [(inc (rand-int 4))])) ;; init to a random num in #{1,2,3,4}
(def next-index-to-press (atom 0)) ;; initialized to 0

;; state getters
(defn get-game-stage [] (deref game-stage))
(defn get-current-sequence [] (deref current-sequence))
(defn get-next-index-to-press [] (deref next-index-to-press))

;; state changers
(defn add-to-current-sequence [val]
  (swap! current-sequence #(conj % val)))

(defn increase-index []
  (swap! next-index-to-press inc))

(defn set-current-index-to-zero []
  (swap! next-index-to-press #(- % %)))

(defn change-game-state [state]
  (swap! game-stage #(string/replace % #"[a-z\-]+" state)))
