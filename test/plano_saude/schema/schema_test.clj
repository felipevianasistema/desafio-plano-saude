(ns plano-saude.schema.schema-test
  (:require [clojure.test :refer :all]
            [matcher-combinators.test]
            [plano-saude.validacoes.schemas :as schem]
            [plano-saude.validacoes.validacoes :as val]))


(deftest schemas-teste
  (testing "Teste - Plano"

    (is (nil? (val/validar-schema schem/plano-schema {:cnpj "08850477000178"
                                                      :nome "Norte Europa"
                                                      :descricao "Plano de saúde"}))))

  (testing "Teste - Fichas"

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
                                                                   :cd-plano 4}))))

  (testing "Teste - Campos Plano"

    (is (match? {:cnpj ["Cnpj inválido"]
                 :nome ["Informe o valor correto"]
                 :descricao ["Informe o valor correto"]}
                (val/validar-schema schem/plano-schema {:cnpj ""
                                                        :nome ""
                                                        :descricao ""}))))


  (testing "Teste - Campos Fichas"

    (is (match? {:nome ["Informe o valor correto"]
                 :cpf ["Cpf inválido"]
                 :dt-admissao ["Data inválida"]
                 :email ["E-mail inválido"]
                 :cd-plano ["Informe o valor correto"]}
                (val/validar-schema schem/norte-europa-schema {:nome ""
                                                               :cpf ""
                                                               :dt-admissao ""
                                                               :email ""
                                                               :cd-plano ""})))

    (is (match? {:nome ["Informe o valor correto"]
                 :cpf ["Cpf inválido"]
                 :dt-admissao ["Data inválida"]
                 :endereco ["Informe o valor correto"]
                 :cd-plano ["Informe o valor correto"]}
                (val/validar-schema schem/pampulha-intermedica-schema {:nome ""
                                                                       :cpf ""
                                                                       :dt-admissao ""
                                                                       :endereco ""
                                                                       :cd-plano ""})))

    (is (match? {:nome ["Informe o valor correto"]
                 :cpf ["Cpf inválido"]
                 :peso-kg ["Informe o valor correto"]
                 :altura-cm ["Informe o valor correto"]
                 :cd-plano ["Informe o valor correto"]}
                (val/validar-schema schem/dental-sorriso-schema {:nome ""
                                                                 :cpf ""
                                                                 :peso-kg ""
                                                                 :altura-cm ""
                                                                 :cd-plano ""})))

    (is (match? {:cpf ["Cpf inválido"]
                 :hrs-meditadas-ultimos-dias ["Informe o valor correto"]
                 :cd-plano ["Informe o valor correto"]}
                (val/validar-schema schem/mente-sa-corpo-sao-schema {:cpf ""
                                                                     :hrs-meditadas-ultimos-dias ""
                                                                     :cd-plano ""})))))

