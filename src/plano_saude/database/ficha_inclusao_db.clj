 (ns plano-saude.database.ficha-inclusao-db
   (:require [clojure.java.jdbc :as jdbc]
             [plano-saude.database.config :as config]
             [plano-saude.utils.util :as util]
             [plano-saude.database.queries.ficha-inclusao-queries :as query]))


(defn cadastrar [nome cpf dt-admissao email endereco
                 peso-kg altura-cm hrs-meditadas-ultimos-dias cd-plano]
  (jdbc/insert! @config/con-db-atm :ficha_inclusao {:nome nome
                                                    :cpf cpf
                                                    :dt_admissao (util/formatar-data-db dt-admissao)
                                                    :email email
                                                    :endereco endereco
                                                    :peso_kg peso-kg
                                                    :altura_cm altura-cm
                                                    :hrs_meditadas_ultimos_dias hrs-meditadas-ultimos-dias
                                                    :ativo true
                                                    :cd_plano cd-plano}))

(defn obter-todos []
  (jdbc/query @config/con-db-atm [query/obter-todos]))

(defn obter-ativos []
  (jdbc/query @config/con-db-atm [query/obter-ativos]))

(defn obter-por-id [id]
  (jdbc/query @config/con-db-atm [query/obter-por-id id]))

(defn atualizar-status [id status]
  (jdbc/update! @config/con-db-atm
                :ficha_inclusao
                {:ativo status}
                ["id = ?" id]))

(defn atualizar [id nome cpf dt-admissao email endereco peso-kg
                 altura-cm hrs-meditadas-ultimos-dias cd-plano]
  (jdbc/update! @config/con-db-atm
                :ficha_inclusao
                {:nome nome
                 :cpf cpf
                 :dt_admissao (util/formatar-data-db dt-admissao)
                 :email email
                 :endereco endereco
                 :peso_kg peso-kg
                 :altura_cm altura-cm
                 :hrs_meditadas_ultimos_dias hrs-meditadas-ultimos-dias
                 :cd_plano cd-plano}
                ["id = ?" id]))
