(ns plano-saude.utils.util
  (:require [ring.util.http-status :as http-status]
            [cadastro-de-pessoa.cnpj :as cnpj]
            [cadastro-de-pessoa.cpf :as cpf]
            [clojure.string :as str]))

(defn formatar-data-db
  "Converte a data no formato do banco de dados"
  [data]
  (when (some? data)
    (-> (.parse (java.text.SimpleDateFormat. "dd/MM/yyyy") data)
        (.getTime)
        (java.sql.Date.))))

(defn verifica-http-code
  "Se houver dados, retorna 200.
   Se não houver, retorna 404."
  [lista]
  (if (empty? lista)
    http-status/not-found
    http-status/ok))

(defn remover-mascara-cpf-cnpj [valor]
  (-> valor
      (str/replace "." "")
      (str/replace "-" "")
      (str/replace "/" "")))

(defn gerar-cnpj
  "Gera cnpj sem máscara"
  []
  (-> (cnpj/gen)
      remover-mascara-cpf-cnpj))

(defn gerar-cpf
  "Gera cpf sem máscara"
  []
  (-> (cpf/gen)
      remover-mascara-cpf-cnpj))
