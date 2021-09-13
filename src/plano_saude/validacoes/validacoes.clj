(ns plano-saude.validacoes.validacoes
  (:require [malli.core :as m]
            [malli.error :as me]
            [clojure.string :as s]
            [cadastro-de-pessoa.cpf  :as cpf]
            [cadastro-de-pessoa.cnpj :as cnpj])
  (:import java.time.format.DateTimeParseException
           java.time.format.DateTimeFormatter
           java.time.LocalDate))


(def msg-valor-incorreto "Informe o valor correto")

(defn str?
  "Valida se o valor é uma string e se está preenchida"
  [str]
  (not (empty? str)))

(defn validar-cpf?
  "Valida se o cpf é uma string e é válido"
  [cpf]
  (if (string? cpf)
    (cpf/valid? cpf)
    false))

(defn validar-cnpj?
  "Valida se o cnpj é uma string e é válido"
  [cnpj]
  (if (string? cnpj)
    (cnpj/valid? cnpj)
    false))

(defn validar-data?
  "Verifica se a data é string e se está no formato correto"
  [data]
  (try
    (let [formato (DateTimeFormatter/ofPattern "dd/MM/yyyy")]
      (LocalDate/parse data formato)
      true)
    (catch DateTimeParseException _
      false)))

(defn validar-email?
  "Valida se o e-mail é válido"
  [email]
  (try
    (not (nil? (re-matches #".+\@.+\..+" email)))
    (catch Exception _
      false)))

(defn validar-schema
  "Valida se o mapa está de acordo com o schema definido.
   Se retornar nil, o mapa está ok."
  [schema mapa]
  (-> schema
      (m/explain mapa)
      (me/humanize)
      (String/valueOf)
      (s/replace "missing required key" "Campo obrigatório")
      (s/replace "disallowed key" "Campo desnecessário")
      (s/replace "null" "nil")
      (read-string)))