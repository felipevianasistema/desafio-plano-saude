(ns plano-saude.core
  (:gen-class)
  (:require [io.pedestal.http :as http]
            [plano-saude.database.config :as config]
            [plano-saude.service.plano-service :as plano]
            [plano-saude.service.ficha-inclusao-service :as ficha-inclusao]))

(defn criar-servidor []
  (http/create-server {::http/routes (concat plano/rotas ficha-inclusao/rotas)
                       ::http/type :jetty
                       ::http/port 3000}))

(defn -main
  [& args]
  (config/tipo-conexao-db "prod")
  (http/start (criar-servidor)))
