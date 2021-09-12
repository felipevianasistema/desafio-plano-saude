(ns plano-saude.utils.response
  (:require [clojure.data.json :as json]
            [plano-saude.utils.mensagens :as msg]
            [ring.util.http-status :as http-status]))


(defn response
  "Modelo de retorno padrão"
  [status body]
  {:status status
   :body body
   :headers {"Content-Type" "application/json"}})

(defn sucesso-response
  "Retorno de sucesso"
  []
  (response http-status/ok (json/write-str
                            {:mensagem (:sucesso msg/mensagem)})))

(defn erro-response
  "Retorno de erro"
  []
  (response http-status/internal-server-error (json/write-str
                                               {:mensagem (:erro msg/mensagem)})))

(defn erro-registro-unico-response
  "Retorno de erro para registro único"
  []
  (response http-status/internal-server-error (json/write-str
                                               {:mensagem (:registro-duplicado msg/mensagem)})))

(defn erro-registro-nao-encontrado-response
  "Retorno de erro para registro não encontrado"
  []
  (response http-status/not-found (json/write-str
                                   {:mensagem (:registro-nao-encontrado msg/mensagem)})))