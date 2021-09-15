(ns plano-saude.service.compartilhado
  (:require [io.pedestal.test :refer :all]
            [clojure.test :refer :all]
            [io.pedestal.http :as service]
            [clojure.data.json :as json]
            [plano-saude.service.plano-service :as plano-service]
            [plano-saude.service.ficha-inclusao-service :as ficha-service]))


(def service
  (-> (service/default-interceptors {::service/routes (concat ficha-service/rotas plano-service/rotas)})
      service/service-fn
      ::service/service-fn))

(defn consultar [url]
  (response-for service :get url))

(defn cadastrar [url body]
  (response-for service :post url
                :body (json/json-str body)))

(defn atualizar [url body]
  (response-for service :put url
                :body (json/json-str body)))