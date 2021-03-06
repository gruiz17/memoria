(ns best-site-ever.main.board)
(def colors ["#33CCCC" "#0066FF" "#C12EE6" "#66FF66"])
  
(defn- squares-amount-valid? [n]
  (and
   (> n 3)
   (< n 5)
   (= 0 (mod n 2))))

(defn- generate-board! [n div-name] 
  {:pre [(squares-amount-valid? n)
         (string? div-name)]}
  (let [board (apply str 
                (->> (range 1 (inc n))
                  (map 
                    #(str "<div class='game-button " 
                          div-name  
                          "-button' id='" 
                          (str div-name "-" %) 
                          "' style='background-color:" 
                          (nth colors (- % 1)) 
                          "'></div>"))
                          (apply str)))
        div-to-change (.getElementById js/document div-name)]
    (do
      ;;(.log js/console board)
      (set! (.-innerHTML div-to-change) board))))

(defn init-boards []
  (do  
    (generate-board! 4 "sequence")
    (generate-board! 4 "player")))
;;  (.log js/console "loaded!")


(defn flash [el amount]
  (set! (->> el (.-style) (.-opacity)) (str amount)))

