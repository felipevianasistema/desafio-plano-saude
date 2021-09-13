(ns plano-saude.utils.util
  (:require [ring.util.http-status :as http-status]
            [cadastro-de-pessoa.cnpj :as cnpj]
            [cadastro-de-pessoa.cpf :as cpf]
            [clojure.string :as s]))

(defn formatar-data-db
  "Converte a data para inserir no banco de dados.
   Retorn nil se o parâmetro for vazio"
  [data]
  (if (nil? data)
    data
    (-> (.parse (java.text.SimpleDateFormat. "dd/MM/yyyy") data)
        (.getTime)
        (java.sql.Date.))))

(defn verifica-http-code
  "Se houver dados, retorna ok.
   Se não houver dados, retorna 404."
  [lista]
  (if (empty? lista)
    http-status/not-found
    http-status/ok))


(defn gerar-cnpj
  "Gera cnpj aleatório e sem máscara"
  []
  (-> (cnpj/gen)
      (s/replace "." "")
      (s/replace "-" "")
      (s/replace "/" "")))

(defn gerar-cpf
  "Gera cpf aleatório e sem máscara"
  []
  (-> (cpf/gen)
      (s/replace "." "")
      (s/replace "-" "")
      (s/replace "/" "")))

