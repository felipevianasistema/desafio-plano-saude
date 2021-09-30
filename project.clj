(defproject plano-saude "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [metosin/malli "0.6.1"]
                 [io.pedestal/pedestal.service "0.5.9"]
                 [io.pedestal/pedestal.route "0.5.9"]
                 [io.pedestal/pedestal.jetty "0.5.9"]
                 [metosin/ring-http-response "0.9.3"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [clj-postgresql "0.7.0"]
                 [org.clojure/data.json "2.4.0"]
                 [com.h2database/h2 "1.3.175"]
                 [cheshire "5.10.0"]
                 [cadastro-de-pessoa "0.4.0"]
                 [org.clojure/tools.logging "1.1.0"]
                 [nubank/matcher-combinators "3.3.1"]
                 [medley "1.3.0"]]
  :main ^:skip-aot plano-saude.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :test     {:dependencies [[org.clojure/test.check "1.1.0"]
                                       [lambdaisland/kaocha "1.0.887"]
                                       [lambdaisland/kaocha-cloverage "1.0.72"]]}
             :kaocha {:dependencies [[lambdaisland/kaocha "1.0.887"]]}}
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]})


