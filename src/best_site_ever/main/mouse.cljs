(ns best-site-ever.main.mouse
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [put! chan <!]]
            [goog.events :as events]))

(defn listen [el type]
  (let [out (chan)]
    (events/listen el type 
                   #(put! out %)) out))

(let [clicks (listen (.getElementById js/document "player") "click")]
  (go (while true
        (.log js/console (<! clicks)))))
   