(ns plano-saude.core
  (:gen-class)
  (:require [io.pedestal.http :as http]
            [plano-saude.database.config :as config]
            [plano-saude.service.plano-service :as plano]
            [plano-saude.service.ficha-inclusao-service :as ficha-inclusao]))

(defn- iniciar-servidor
  "Inicializa o servidor da API"
  []
  (let [servidor (http/create-server {::http/routes (concat ficha-inclusao/rotas plano/rotas)
                                      ::http/type :jetty
                                      ::http/port 3000})]
    (config/tipo-conexao-db "prod")
    (http/start servidor)))


(defn -main
  [& args]
  (iniciar-servidor))
