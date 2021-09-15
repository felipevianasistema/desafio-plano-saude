(ns plano-saude.service.plano-service-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]
            [cheshire.core :as json]
            [plano-saude.utils.util :as util]
            [plano-saude.service.compartilhado :as req]
            [plano-saude.database.config :as config-db]))

(deftest rotas-plano-saude-teste
  (config-db/tipo-conexao-db "teste")

  (let [plano "/plano/"
        dados-plano {:cnpj "11025035000100"
                     :nome "Plano 1"
                     :descricao "Desc. Plano 1"}]

    (testing "Cadastra"
      (let [result (req/cadastrar (str plano "cadastrar") dados-plano)]
        (is (= 201 (:status result)))
        (is (s/includes? (-> (json/decode (:body result) keyword)
                             :mensagem)
                         "Operação concluída com sucesso."))))

    (testing "Tenta cadastrar um já existente"
      (let [result (req/cadastrar (str plano "cadastrar") dados-plano)]
        (is (= 400 (:status result)))
        (is (s/includes? (-> (json/decode (:body result) keyword)
                             :mensagem)
                         "Não foi possível concluir a operação."))))

    (testing "Consulta por id"
      (let [result (req/consultar (str plano "obter/1"))]
        (is (= 200 (:status result)))
        (is (= 1 (count (json/decode (:body result) keyword))))))

    (testing "Consulta por id inexistente"
      (let [result (req/consultar (str plano "obter/9999"))]
        (is (= 404 (:status result)))
        (is (= 0 (count (json/decode (:body result) keyword))))))

    (testing "Consulta todos"
      (let [result (req/consultar (str plano "obter-todos"))]
        (is (= 200 (:status result)))
        (is (= 5 (count (json/decode (:body result) keyword))))))

    (testing "Atualiza para inativo, consulta se o status foi atualizado, obtem somente ativos e todos"
      (let [result-atualizado (req/atualizar (str plano "atualizar-status/2") (assoc dados-plano :ativo false))
            result-consulta (req/consultar (str plano "obter/2"))
            result-ativos (req/consultar (str plano "obter-ativos"))
            result-todos (req/consultar (str plano "obter-todos"))]

        (is (= 200 (:status result-atualizado)))
        (is (= false (-> (json/decode (:body result-consulta) keyword)
                         first
                         :ativo)))
        (is (= 4 (count (json/decode (:body result-ativos) keyword))))
        (is (= 5 (count (json/decode (:body result-todos) keyword))))))

    (testing "Atualiza os dados e consulta se realmente foi atualizado"
      (let [cnpj (util/gerar-cnpj)
            nome "Novo nome"
            descricao "Nova descrição"
            result-atualizado (req/atualizar (str plano "atualizar/4") (-> dados-plano
                                                                       (update :cnpj (constantly cnpj))
                                                                       (update :nome (constantly nome))
                                                                       (update :descricao (constantly descricao))))
            result-consulta (req/consultar (str plano "obter/4"))]

        (is (= 200 (:status result-atualizado)))
        (is (= 200 (:status result-consulta)))

        (let [result (first
                      (json/decode (:body result-consulta) keyword))]
          (is (= cnpj (:cnpj result)))
          (is (= nome (:nome result)))
          (is (= descricao (:descricao result))))))))
