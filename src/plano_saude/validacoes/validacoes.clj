(ns plano-saude.validacoes.validacoes
  (:require [malli.core :as m]
            [malli.error :as me]
            [clojure.string :as s]
            [cadastro-de-pessoa.cpf  :as cpf]
            [cadastro-de-pessoa.cnpj :as cnpj])
  (:import java.time.format.DateTimeParseException
           java.time.format.DateTimeFormatter
           java.time.LocalDate))


(def msg-valor-invalido "Informe o valor correto")
(def msg-data-invalida "Data inválida")
(def msg-cpf-invalido "Cpf inválido")
(def msg-cnpj-invalido "Cnpj inválido")
(def msg-email-invalido "E-mail inválido")

(defn str?
  "Valida se o valor é uma string e se está preenchida"
  [str]
  (not (empty? str)))

(defn cpf-valido?
  "Valida se o cpf é uma string e é válido"
  [cpf]
  (and (string? cpf) (cpf/valid? cpf)))

(defn cnpj-valido?
  "Valida se o cnpj é uma string e é válido"
  [cnpj]
  (and (string? cnpj) (cnpj/valid? cnpj)))

(defn data-valida?
  "Verifica se a data é verdadeira e está no formato correto"
  [data]
  (try
    (let [formato (DateTimeFormatter/ofPattern "dd/MM/yyyy")]
      (LocalDate/parse data formato)
      true)
    (catch DateTimeParseException _
      false)))

(defn email-valido?
  "Valida se o e-mail é válido"
  [email]
  (try
    (not (nil? (re-matches #".+\@.+\..+" email)))
    (catch Exception _
      false)))

(defn validar-schema
  "Valida se o mapa está de acordo com o schema definido.
   Se retornar nil indica que o mapa está ok."
  [schema mapa]
  (-> schema
      (m/explain mapa)
      (me/humanize)
      (String/valueOf)
      (s/replace "missing required key" "Campo obrigatório")
      (s/replace "disallowed key" "Este campo não deve ser informado")
      (s/replace "null" "nil")
      (read-string)))