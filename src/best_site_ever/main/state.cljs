(ns best-site-ever.main.state)

(def game-stage (atom nil)) ;; "to-begin", "in-progress", "lost"
(def whose-turn (atom nil)) ;; "player", "simon"
(def current-sequence (atom [])) ;; current sequence lol
(def next-index-to-press (atom nil)) ;; initialized to 0

;; all the actual state-changing funcs
(defn add-to-current-sequence [val]
  (swap! current-sequence #(conj % val)))

(defn increase-index []
  (swap! next-index-to-press inc))

(defn change-game-state [state]
  (swap! game-stage (fn [] state)))

;; funcs dependent on the state
(defn move-on? [val]
  (= val (nth @current-sequence next-index-to-press)))

(defn finished-round? []
  (>= next-index-to-press (count @current-sequence)))
