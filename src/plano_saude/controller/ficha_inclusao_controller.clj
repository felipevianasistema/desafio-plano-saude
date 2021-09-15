(ns plano-saude.controller.ficha-inclusao-controller
  (:require [clojure.string :as s]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [plano-saude.utils.util :as util]
            [plano-saude.utils.mensagens :as msg]
            [plano-saude.utils.response :as respo]
            [ring.util.http-status :as http-status]
            [plano-saude.validacoes.schemas :as schem]
            [plano-saude.validacoes.validacoes :as valid]
            [plano-saude.database.ficha-inclusao-db :as db]))

(defn cadastrar [{:keys [nome cpf dt-admissao email endereco peso-kg
                         altura-cm hrs-meditadas-ultimos-dias cd-plano]
                  :as mapa}]
  (try
    (let [retorno (cond
                    (= cd-plano 1) (valid/validar-schema schem/norte-europa-schema mapa)
                    (= cd-plano 2) (valid/validar-schema schem/pampulha-intermedica-schema mapa)
                    (= cd-plano 3) (valid/validar-schema schem/dental-sorriso-schema mapa)
                    (= cd-plano 4) (valid/validar-schema schem/mente-sa-corpo-sao-schema mapa)
                    :else (:plano-n-encontrado msg/mensagem))]
      (if (nil? retorno)
        (do (db/cadastrar nome cpf dt-admissao email endereco peso-kg altura-cm hrs-meditadas-ultimos-dias cd-plano)
            (respo/response http-status/created (json/write-str
                                                 {:mensagem (:sucesso msg/mensagem)})))
        (respo/response http-status/internal-server-error (json/write-str
                                                           {:mensagem retorno}))))
    (catch Exception e
      (log/error e mapa)
      (if (s/includes? (ex-message e) "unique_cpf_cd_plano")
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
                 {:keys [nome cpf dt-admissao email endereco
                         peso-kg altura-cm hrs-meditadas-ultimos-dias cd-plano] 
                  :as mapa}]
  (try
    (let [retorno (cond
                    (= cd-plano 1) (valid/validar-schema schem/norte-europa-schema mapa)
                    (= cd-plano 2) (valid/validar-schema schem/pampulha-intermedica-schema mapa)
                    (= cd-plano 3) (valid/validar-schema schem/dental-sorriso-schema mapa)
                    (= cd-plano 4) (valid/validar-schema schem/mente-sa-corpo-sao-schema mapa)
                    :else (:plano-n-encontrado msg/mensagem))
          linha (when (nil? retorno)
                  (db/atualizar (Integer/parseInt id) nome cpf dt-admissao email
                                endereco peso-kg altura-cm hrs-meditadas-ultimos-dias cd-plano))]

      (cond
        (= (first linha) 1) (respo/sucesso-response)
        (some? retorno) (respo/response http-status/internal-server-error (json/write-str {:mensagem retorno}))
        :else (respo/erro-registro-nao-encontrado-response)))
    (catch Exception e
      (log/error e mapa)
      (if (s/includes? (ex-message e) "unique_cpf_cd_plano")
        (respo/erro-registro-unico-response)
        (respo/erro-response)))))

