(ns plano-saude.database.config
  (:require [clojure.java.jdbc :as jdbc]))

(def ^:private producao
  "Executa a base de dados local"
  {:dbtype "postgresql"
   :dbname "plano_saude"
   :host "localhost"
   :user "postgres"
   :password "password"})

(def ^:private teste
  "Executa a base de dados em memória para testes"
  {:classname   "org.h2.Driver"
   :subprotocol "h2:mem"
   :subname     "demo;DB_CLOSE_DELAY=-1"
   :user        "sa"
   :password    ""})


(def con-db-atm (atom nil))

(defn tipo-conexao-db
  "Deve ser informado o tipo da conexão 'prod' ou 'teste'.
   Para prod: executa na base de dados local.
   Para teste: executa na base de dados em memória."
  [tipo]
  (if (= "prod" tipo)
    (reset! con-db-atm producao)
    (reset! con-db-atm teste)))

(defn criar-banco-memoria []
  (jdbc/execute! @con-db-atm [(slurp "resources/sql/script_banco.sql")]))

