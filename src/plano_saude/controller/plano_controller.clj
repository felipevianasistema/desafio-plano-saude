(ns plano-saude.controller.plano-controller
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [plano-saude.utils.util :as util]
            [plano-saude.database.plano-db :as db]
            [plano-saude.utils.response :as respo]
            [ring.util.http-status :as http-status]
            [plano-saude.validacoes.schemas :as schem]
            [plano-saude.validacoes.validacoes :as valid]))


(defn cadastrar [{:keys [cnpj nome descricao]
                  :as mapa}]
  (try
    (let [validacao (valid/validar-schema schem/plano-schema mapa)]
      (if (nil? validacao)
        (do (db/cadastrar cnpj nome descricao)
            (respo/sucesso-response http-status/created))
        (respo/erro-validacao-response validacao)))
    (catch Exception e
      (log/error e mapa)
      (if (str/includes? (ex-message e) "unique_cnpj")
        (respo/erro-registro-unico-response)
        (respo/erro-response)))))

(defn obter-todos []
  (try
    (let [lista (db/obter-todos)
          http (util/verifica-http-code lista)]
      (respo/response http (vec lista)))
    (catch Exception e
      (log/error e)
      (respo/erro-response))))

(defn obter-ativos []
  (try
    (let [lista (db/obter-ativos)
          http (util/verifica-http-code lista)]
      (respo/response http (vec lista)))
    (catch Exception e
      (log/error e)
      (respo/erro-response))))

(defn obter-por-id [{:keys [id]}]
  (try
    (let [lista (db/obter-por-id (Integer/parseInt id))
          http (util/verifica-http-code lista)]
      (respo/response http (vec lista)))
    (catch Exception e
      (log/error e)
      (respo/erro-response))))

(defn atualizar-status [{:keys [id]}
                        {:keys [ativo]}]
  (try
    (let [linha (db/atualizar-status (Integer/parseInt id) ativo)]
      (if (= (first linha) 1)
        (respo/sucesso-response http-status/ok)
        (respo/erro-registro-nao-encontrado-response)))
    (catch Exception e
      (log/error e)
      (respo/erro-response))))

(defn atualizar [{:keys [id]}
                 {:keys [cnpj nome descricao]
                  :as mapa}]
  (try
    (let [validacao (valid/validar-schema schem/plano-schema mapa)
          linha (when (nil? validacao)
                  (db/atualizar (Integer/parseInt id) cnpj nome descricao))]

      (cond
        (= (first linha) 1) (respo/sucesso-response http-status/ok)
        (some? validacao)   (respo/erro-validacao-response validacao)
        :else (respo/erro-registro-nao-encontrado-response)))
    (catch Exception e
      (log/error e mapa)
      (if (str/includes? (ex-message e) "unique_cnpj")
        (respo/erro-registro-unico-response)
        (respo/erro-response)))))