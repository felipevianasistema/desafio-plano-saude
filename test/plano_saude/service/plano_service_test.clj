(ns plano-saude.service.plano-service-test
  (:require [clojure.test :refer :all]
            [io.pedestal.test :refer :all]
            [clojure.string :as s]
            [plano-saude.utils.util :as util]
            [io.pedestal.http :as service]
            [cheshire.core :as json]
            [clojure.data.json :as j]
            [plano-saude.service.plano-service :as plano-service]
            [plano-saude.database.config :as config-db]))


(def service
  (-> (service/default-interceptors {::service/routes plano-service/rotas})
      service/service-fn
      ::service/service-fn))

(defn consultar [url]
  (response-for service :get url))

(defn cadastrar [url body]
  (response-for service :post url
                :body (j/json-str body)))

(defn atualizar [url body]
  (response-for service :put url
                :body (j/json-str body)))

(deftest rotas-plano-saude-teste
  (config-db/tipo-conexao-db "teste")

  (let [plano "/plano/"
        dados-plano {:cnpj "11025035000100"
                     :nome "Plano 1"
                     :descricao "Desc. Plano 1"}]

    (testing "Cadastra"
      (let [result (cadastrar (str plano "cadastrar") dados-plano)]
        (is (= 201 (:status result)))
        (is (s/includes? (-> (json/decode (:body result) keyword)
                             :mensagem)
                         "sucesso"))))

    (testing "Tenta cadastrar o mesmo"
      (let [result (cadastrar (str plano "cadastrar") dados-plano)]
        (is (= 500 (:status result)))
        (is (s/includes? (-> (json/decode (:body result) keyword)
                             :mensagem)
                         "Não foi possível concluir a operação."))))

    (testing "Consulta por id"
      (let [result (consultar (str plano "obter/1"))]
        (is (= 1 (count (json/decode (:body result) keyword))))
        (is (= 200 (:status result)))))

    (testing "Consulta por id inexistente"
      (let [result (consultar (str plano "obter/9999"))]
        (is (= 0 (count (json/decode (:body result) keyword))))
        (is (= 404 (:status result)))))

    (testing "Consulta todos"
      (let [result (consultar (str plano "obter-todos"))]
        (is (= 5 (count (json/decode (:body result) keyword))))
        (is (= 200 (:status result)))))

    (testing "Atualiza para inativo, consulta se o status foi atualizado, obtem somente ativos e todos"
      (let [result-atualizado (atualizar (str plano "atualizar-status/2") (assoc dados-plano :ativo false))
            result-consulta (consultar (str plano "obter/2"))
            result-ativos (consultar (str plano "obter-ativos"))
            result-todos (consultar (str plano "obter-todos"))]

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
            result-atualizado (atualizar (str plano "atualizar/4") (-> dados-plano
                                                                       (update :cnpj (constantly cnpj))
                                                                       (update :nome (constantly nome))
                                                                       (update :descricao (constantly descricao))))
            result-consulta (consultar (str plano "obter/4"))]

        (is (= 200 (:status result-atualizado)))
        (is (= 200 (:status result-consulta)))

        (let [rs (first (json/decode (:body result-consulta) keyword))
              rs-cnpj (:cnpj rs)
              rs-nome (:nome rs)
              rs-descricao (:descricao rs)]
          (is (= cnpj rs-cnpj))
          (is (= nome rs-nome))
          (is (= descricao rs-descricao)))))))
