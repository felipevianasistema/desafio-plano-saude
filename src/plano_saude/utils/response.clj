(ns plano-saude.utils.response
  (:require [clojure.data.json :as json]
            [plano-saude.utils.mensagens :as msg]
            [ring.util.http-status :as http-status]))


(defn response
  "Modelo de retorno padrão"
  [status body]
  {:status status
   :body (json/write-str body)
   :headers {"Content-Type" "application/json"}})

(defn sucesso-response
  "Retorno de sucesso"
  [http-code]
  (response http-code {:mensagem (:sucesso msg/mensagem)}))

(defn erro-response
  "Retorno de erro"
  []
  (response http-status/bad-request {:mensagem (:erro msg/mensagem)}))

(defn erro-validacao-response
  "Retorno de validações"
  [msg]
  (response http-status/bad-request {:mensagem msg}))

(defn erro-registro-unico-response
  "Retorno de erro para registro único"
  []
  (response http-status/bad-request {:mensagem (:registro-duplicado msg/mensagem)}))

(defn erro-registro-nao-encontrado-response
  "Retorno de erro para registro não encontrado"
  []
  (response http-status/not-found {:mensagem (:registro-nao-encontrado msg/mensagem)}))