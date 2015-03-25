(ns best-site-ever.main.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [best-site-ever.main.board :as board]
            [best-site-ever.main.keyboard :as keyboard]
            [best-site-ever.main.mouse :as mouse]
            [best-site-ever.main.state :as state]
            [cljs.core.async :refer [put! chan <!]]))

;; (enable-console-print!)

(do (state/change-game-state "to-begin")
  	(state/set-current-index-to-zero)  
    (board/init-boards)
    (mouse/init-events)
    (keyboard/init-keyboard))
