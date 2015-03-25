(defproject best-site-ever "0.0.1"
  :description "A memory game"
  :url "http://ggruiz.me/memoria"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3126"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [figwheel "0.2.5"]]
  
  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-figwheel "0.2.5"]]
  
  :figwheel {:http-server-root "public"
             :server-port 3449
             :repl true}
  
  :cljsbuild {:builds
              [{:id "dev"
               	:source-paths ["src/best_site_ever/main" "src/best_site_ever/figwheel"]
                :compiler {:output-to "resources/public/main.js"
                           :output-dir "resources/public/out"
                           :optimizations :none
                           :source-maps true
                           :pretty-print true}}]})
