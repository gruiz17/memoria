(ns best-site-ever.main.state
  (:require [clojure.string :as string]))

(def game-stage (atom "to-begin")) ;; "to-begin", "in-progress", "lost"
(def whose-turn (atom "simon")) ;; "player", "simon"
(def current-sequence (atom [])) ;; current sequence lol
(def next-index-to-press (atom 0)) ;; initialized to 0

;; state getters
(defn get-game-stage [] (deref game-stage))
(defn get-whose-turn [] (deref whose-turn))
(defn get-current-sequence [] (deref current-sequence))
(defn get-next-index-to-press [] (deref next-index-to-press))

;; state changers
(defn add-to-current-sequence [val]
  (swap! current-sequence #(conj % val)))

(defn increase-index []
  (swap! next-index-to-press inc))

(defn change-game-state [state]
  (swap! game-stage #(string/replace % #"[a-z\-]+" state)))

;; funcs dependent on the state
(defn move-on? [val]
  (= val (nth @current-sequence next-index-to-press)))

(defn finished-round? []
  (>= next-index-to-press (count @current-sequence)))
