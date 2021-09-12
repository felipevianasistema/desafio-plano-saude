(ns plano-saude.schema.schema-test
  (:require [clojure.test :refer :all]
            [plano-saude.validacoes.schemas :as schem]
            [plano-saude.validacoes.validacoes :as val]))


(deftest schemas-teste
  (testing "Teste - Plano"
    (is (nil? (val/validar-schema schem/plano-schema {:cnpj "08850477000178"
                                                      :nome "Norte Europa"
                                                      :descricao "Plano de saúde"}))))

  (testing "Teste - Todos os tipos de fichas"

    (is (nil? (val/validar-schema schem/norte-europa-schema {:nome "Maria Silva"
                                                             :cpf "46962563086"
                                                             :dt-admissao "01/12/2020"
                                                             :email "felipe@email.com"
                                                             :cd-plano 1})))

    (is (nil? (val/validar-schema schem/pampulha-intermedica-schema {:nome "Maria Silva"
                                                                     :cpf "46962563086"
                                                                     :dt-admissao "01/12/2020"
                                                                     :endereco "Av Paulista, 1552, São Paulo - SP"
                                                                     :cd-plano 2})))

    (is (nil? (val/validar-schema schem/dental-sorriso-schema {:nome "Maria Silva"
                                                               :cpf "46962563086"
                                                               :peso-kg 62.0
                                                               :altura-cm 1.60
                                                               :cd-plano 3})))

    (is (nil? (val/validar-schema schem/mente-sa-corpo-sao-schema {:cpf "46962563086"
                                                                   :hrs-meditadas-ultimos-dias 5.3
                                                                   :cd-plano 4})))))

