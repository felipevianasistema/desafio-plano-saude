(ns plano-saude.validacoes.schemas
  (:require [plano-saude.validacoes.validacoes :as val]))


(def norte-europa-schema [:map {:closed true}
                          [:nome [:fn {:error/message val/msg-valor-invalido} val/str?]]
                          [:cpf [:fn {:error/message val/msg-cpf-invalido} val/cpf-valido?]]
                          [:dt-admissao [:fn {:error/message val/msg-data-invalida} val/data-valida?]]
                          [:email [:fn {:error/message val/msg-email-invalido} val/email-valido?]]
                          [:cd-plano [:fn {:error/message val/msg-valor-invalido} pos-int?]]])

(def pampulha-intermedica-schema [:map {:closed true}
                                  [:nome [:fn {:error/message val/msg-valor-invalido} val/str?]]
                                  [:cpf [:fn {:error/message val/msg-cpf-invalido} val/cpf-valido?]]
                                  [:dt-admissao [:fn {:error/message val/msg-data-invalida} val/data-valida?]]
                                  [:endereco [:fn {:error/message val/msg-valor-invalido} val/str?]]
                                  [:cd-plano [:fn {:error/message val/msg-valor-invalido} pos-int?]]])

(def dental-sorriso-schema [:map {:closed true}
                            [:nome [:fn {:error/message val/msg-valor-invalido} val/str?]]
                            [:cpf [:fn {:error/message val/msg-cpf-invalido} val/cpf-valido?]]
                            [:peso-kg [:fn {:error/message val/msg-valor-invalido} double?]]
                            [:altura-cm [:fn {:error/message val/msg-valor-invalido} double?]]
                            [:cd-plano [:fn {:error/message val/msg-valor-invalido} pos-int?]]])

(def mente-sa-corpo-sao-schema [:map {:closed true}
                                [:cpf [:fn {:error/message val/msg-cpf-invalido} val/cpf-valido?]]
                                [:hrs-meditadas-ultimos-dias [:fn {:error/message val/msg-valor-invalido} double?]]
                                [:cd-plano [:fn {:error/message val/msg-valor-invalido} pos-int?]]])

(def plano-schema [:map {:closed true}
                   [:cnpj [:fn {:error/message val/msg-cnpj-invalido} val/cnpj-valido?]]
                   [:nome [:fn {:error/message val/msg-valor-invalido} val/str?]]
                   [:descricao [:fn {:error/message val/msg-valor-invalido} val/str?]]])

