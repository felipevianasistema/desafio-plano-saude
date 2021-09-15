(ns plano-saude.controller.ficha-inclusao-controller
  (:require [clojure.string :as str]
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
    (let [validacao (cond
                      (= cd-plano 1) (valid/validar-schema schem/norte-europa-schema mapa)
                      (= cd-plano 2) (valid/validar-schema schem/pampulha-intermedica-schema mapa)
                      (= cd-plano 3) (valid/validar-schema schem/dental-sorriso-schema mapa)
                      (= cd-plano 4) (valid/validar-schema schem/mente-sa-corpo-sao-schema mapa)
                      :else (:plano-n-encontrado msg/mensagem))]
      (if (nil? validacao)
        (do (db/cadastrar nome cpf dt-admissao email endereco peso-kg altura-cm hrs-meditadas-ultimos-dias cd-plano)
            (respo/sucesso-response http-status/created))
        (respo/erro-validacao-response validacao)))
    (catch Exception e
      (log/error e mapa)
      (if (str/includes? (ex-message e) "unique_cpf_cd_plano")
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
                 {:keys [nome cpf dt-admissao email endereco
                         peso-kg altura-cm hrs-meditadas-ultimos-dias cd-plano]
                  :as mapa}]
  (try
    (let [validacao (cond
                      (= cd-plano 1) (valid/validar-schema schem/norte-europa-schema mapa)
                      (= cd-plano 2) (valid/validar-schema schem/pampulha-intermedica-schema mapa)
                      (= cd-plano 3) (valid/validar-schema schem/dental-sorriso-schema mapa)
                      (= cd-plano 4) (valid/validar-schema schem/mente-sa-corpo-sao-schema mapa)
                      :else (:plano-n-encontrado msg/mensagem))
          linha (when (nil? validacao)
                  (db/atualizar (Integer/parseInt id) nome cpf dt-admissao email
                                endereco peso-kg altura-cm hrs-meditadas-ultimos-dias cd-plano))]

      (cond
        (= (first linha) 1) (respo/sucesso-response http-status/ok)
        (some? validacao)   (respo/erro-validacao-response validacao)
        :else (respo/erro-registro-nao-encontrado-response)))
    (catch Exception e
      (log/error e mapa)
      (if (str/includes? (ex-message e) "unique_cpf_cd_plano")
        (respo/erro-registro-unico-response)
        (respo/erro-response)))))

