 (ns plano-saude.database.plano-db
   (:require [clojure.java.jdbc :as jdbc]
             [plano-saude.database.config :as config]
             [plano-saude.database.queries.plano-queries :as query]))

(defn cadastrar [cnpj nome descricao]
  (jdbc/insert! @config/con-db-atm :plano {:cnpj cnpj
                                           :nome nome
                                           :descricao descricao
                                           :ativo true}))

(defn obter-todos []
  (jdbc/query @config/con-db-atm [query/obter-todos]))

(defn obter-ativos []
  (jdbc/query @config/con-db-atm [query/obter-ativos]))

(defn obter-por-id [id]
  (jdbc/query @config/con-db-atm [query/obter-por-id id]))

(defn atualizar-status [id status]
  (jdbc/update! @config/con-db-atm
                :plano
                {:ativo status}
                ["id = ?" id]))

(defn atualizar [id cnpj nome descricao]
  (jdbc/update! @config/con-db-atm
                :plano
                {:cnpj cnpj
                 :nome nome
                 :descricao descricao}
                ["id = ?" id]))


