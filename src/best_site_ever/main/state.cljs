(ns best-site-ever.main.state
  (:require [clojure.string :as string]))

(def game-stage (atom "to-begin")) ;; "to-begin", "simon-turn", "player-turn" "lost"
(def whose-turn (atom "simon")) ;; "player", "simon"
(def current-sequence (atom [(inc (rand-int 4)) 2 4 3 1 3 2 1 4 3])) ;; init to a random num in #{1,2,3,4}
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

(defn change-whose-turn [who]
  (swap! whose-turn #(string/replace % #"[a-z\-]+" who)))

;; funcs dependent on the state
;; probably need to put this in a logic.cljs file
(defn move-on? [val]
  (= val (nth @current-sequence next-index-to-press)))

(defn finished-round? []
  (>= next-index-to-press (count @current-sequence)))
