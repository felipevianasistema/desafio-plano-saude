(ns plano-saude.controller.plano-controller
  (:require [clojure.string :as s]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [plano-saude.utils.util :as util]
            [plano-saude.utils.mensagens :as msg]
            [plano-saude.database.plano-db :as db]
            [plano-saude.utils.response :as respo]
            [ring.util.http-status :as http-status]
            [plano-saude.validacoes.schemas :as schem]
            [plano-saude.validacoes.validacoes :as valid]))


(defn cadastrar [{:keys [cnpj nome descricao] :as mapa}]
  (try
    (let [retorno (valid/validar-schema schem/plano-schema mapa)]
      (if (nil? retorno)
        (do (db/cadastrar cnpj nome descricao)
            (respo/response http-status/created (json/write-str
                                                 {:mensagem (:sucesso msg/mensagem)})))
        (respo/response http-status/internal-server-error (json/write-str
                                                           {:mensagem retorno}))))
    (catch Exception e
      (log/error e mapa)
      (if (s/includes? (ex-message e) "unique_cnpj")
        (respo/erro-registro-unico-response)
        (respo/erro-response)))))

(defn obter-todos []
  (try
    (let [lista (db/obter-todos)
          http (util/verifica-http-code lista)]
      (respo/response http (json/write-str (vec lista))))
    (catch Exception e
      (log/error e)
      (respo/erro-response))))

(defn obter-ativos []
  (try
    (let [lista (db/obter-ativos)
          http (util/verifica-http-code lista)]
      (respo/response http (json/write-str (vec lista))))
    (catch Exception e
      (log/error e)
      (respo/erro-response))))

(defn obter-por-id [{:keys [id]}]
  (try
    (let [lista (db/obter-por-id (Integer/parseInt id))
          http (util/verifica-http-code lista)]
      (respo/response http (json/write-str (vec lista))))
    (catch Exception e
      (log/error e)
      (respo/erro-response))))

(defn atualizar-status [{:keys [id]}
                        {:keys [ativo]}]
  (try
    (let [linha (db/atualizar-status (Integer/parseInt id) ativo)]
      (if (= (first linha) 1)
        (respo/sucesso-response)
        (respo/erro-registro-nao-encontrado-response)))
    (catch Exception e
      (log/error e)
      (respo/erro-response))))

(defn atualizar [{:keys [id]}
                 {:keys [cnpj nome descricao] :as mapa}]
  (try
    (let [retorno (valid/validar-schema schem/plano-schema mapa)
          linha (when (nil? retorno)
                  (db/atualizar (Integer/parseInt id) cnpj nome descricao))]

      (cond
        (= (first linha) 1) (respo/sucesso-response)
        (some? retorno) (respo/response http-status/internal-server-error (json/write-str {:mensagem retorno}))
        :else (respo/erro-registro-nao-encontrado-response)))
    (catch Exception e
      (log/error e mapa)
      (if (s/includes? (ex-message e) "unique_cnpj")
        (respo/erro-registro-unico-response)
        (respo/erro-response)))))