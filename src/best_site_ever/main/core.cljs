(ns best-site-ever.main.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [goog.events :as events]
            [cljs.core.async :refer [put! chan <!]]
            [best-site-ever.main.board :as board]
            [best-site-ever.main.keyboard :as keyboard]
            [best-site-ever.main.mouse :as mouse]
            [best-site-ever.main.state :as state]))

(enable-console-print!)

(.log js/console "hello world")

(do  (board/init-boards)
     (mouse/init-events))


