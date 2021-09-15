(ns plano-saude.service.ficha-inclusao-service-test
  (:require [clojure.test :refer :all]
            [clojure.string :as s]
            [cheshire.core :as json]
            [plano-saude.utils.util :as util]
            [plano-saude.service.compartilhado :as req]
            [plano-saude.database.config :as config-db]))

(deftest rotas-ficha-inclusao-teste
  (config-db/tipo-conexao-db "teste")

  (let [ficha "/ficha-inclusao/"
        dados-ficha {:nome "Maria Silva"
                     :cpf "46962563086"
                     :dt-admissao "01/12/2020"
                     :email "felipe@email.com"
                     :cd-plano 1}]

    (testing "Cadastra"
      (let [result (req/cadastrar (str ficha "cadastrar") dados-ficha)]
        (is (= 201 (:status result)))
        (is (s/includes? (-> (json/decode (:body result) keyword)
                             :mensagem)
                         "Operação concluída com sucesso."))))

    (testing "Tenta cadastrar um já existente"
      (let [result (req/cadastrar (str ficha "cadastrar") dados-ficha)]
        (is (= 400 (:status result)))
        (is (s/includes? (-> (json/decode (:body result) keyword)
                             :mensagem)
                         "Não foi possível concluir a operação."))))

    (testing "Tenta cadastrar para um plano inexistente"
      (let [result (req/cadastrar (str ficha "cadastrar") (assoc dados-ficha :cd-plano 9999))]
        (is (= 400 (:status result)))
        (is (s/includes? (-> (json/decode (:body result) keyword)
                             :mensagem)
                         "Plano não encontrado."))))

    (testing "Tenta atualizar para um plano inexistente"
      (let [result (req/atualizar (str ficha "atualizar/1") (assoc dados-ficha :cd-plano 9999))]
        (is (= 400 (:status result)))
        (is (s/includes? (-> (json/decode (:body result) keyword)
                             :mensagem)
                         "Plano não encontrado."))))

    (testing "Consulta por id"
      (let [result (req/consultar (str ficha "obter/1"))]
        (is (= 200 (:status result)))
        (is (= 1 (count (json/decode (:body result) keyword))))))

    (testing "Consulta por id inexistente"
      (let [result (req/consultar (str ficha "obter/9999"))]
        (is (= 404 (:status result)))
        (is (= 0 (count (json/decode (:body result) keyword))))))

    (testing "Consulta todos"
      (let [result (req/consultar (str ficha "obter-todos"))]
        (is (= 200 (:status result)))
        (is (= 1 (count (json/decode (:body result) keyword))))))

    (testing "Atualiza para inativo, consulta se o status foi atualizado, obtem somente ativos e todos"
      (let [result-atualizado (req/atualizar (str ficha "atualizar-status/1") (assoc dados-ficha :ativo false))
            result-consulta (req/consultar (str ficha "obter/1"))
            result-ativos (req/consultar (str ficha "obter-ativos"))
            result-todos (req/consultar (str ficha "obter-todos"))]

        (is (= 200 (:status result-atualizado)))
        (is (= false (-> (json/decode (:body result-consulta) keyword)
                         first
                         :ativo)))
        (is (= 0 (count (json/decode (:body result-ativos) keyword))))
        (is (= 1 (count (json/decode (:body result-todos) keyword))))))

    (testing "Atualiza os dados e consulta se realmente foi atualizado"
      (let [cpf (util/gerar-cpf)
            nome "Novo nome"
            email "felipe_novo@email.com"
            result-atualizado (req/atualizar (str ficha "atualizar/1") (-> dados-ficha
                                                                       (update :cpf (constantly cpf))
                                                                       (update :nome (constantly nome))
                                                                       (update :email (constantly email))))
            result-consulta (req/consultar (str ficha "obter/1"))]
        (is (= 200 (:status result-atualizado)))
        (is (= 200 (:status result-consulta)))

        (let [result (first
                      (json/decode (:body result-consulta) keyword))]
          (is (= cpf (:cpf result)))
          (is (= nome (:nome result)))
          (is (= email (:email result))))))))
