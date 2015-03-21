(ns best-site-ever.main.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [goog.dom :as dom]
            [goog.events :as events]
            [cljs.core.async :refer [put! chan <!]]
            [best-site-ever.main.board :as board])
  (:import [goog.net Jsonp]
           [goog Uri]))

(enable-console-print!)

(.log js/console "hello world")



(board/init-boards)

