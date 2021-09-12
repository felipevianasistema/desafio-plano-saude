(ns plano-saude.validacoes.schemas
  (:require [plano-saude.validacoes.validacoes :as v]))


(def norte-europa-schema [:map {:closed true}
                          [:nome [:fn {:error/message v/msg-valor-incorreto} v/str?]]
                          [:cpf [:fn {:error/message v/msg-valor-incorreto} v/validar-cpf?]]
                          [:dt-admissao [:fn {:error/message v/msg-valor-incorreto} v/validar-data?]]
                          [:email [:fn {:error/message v/msg-valor-incorreto} v/validar-email?]]
                          [:cd-plano [:fn {:error/message v/msg-valor-incorreto} pos-int?]]])

(def pampulha-intermedica-schema [:map {:closed true}
                                  [:nome [:fn {:error/message v/msg-valor-incorreto} v/str?]]
                                  [:cpf [:fn {:error/message v/msg-valor-incorreto} v/validar-cpf?]]
                                  [:dt-admissao [:fn {:error/message v/msg-valor-incorreto} v/validar-data?]]
                                  [:endereco [:fn {:error/message v/msg-valor-incorreto} v/str?]]
                                  [:cd-plano [:fn {:error/message v/msg-valor-incorreto} pos-int?]]])

(def dental-sorriso-schema [:map {:closed true}
                            [:nome [:fn {:error/message v/msg-valor-incorreto} v/str?]]
                            [:cpf [:fn {:error/message v/msg-valor-incorreto} v/validar-cpf?]]
                            [:peso-kg [:fn {:error/message v/msg-valor-incorreto} double?]]
                            [:altura-cm [:fn {:error/message v/msg-valor-incorreto} double?]]
                            [:cd-plano [:fn {:error/message v/msg-valor-incorreto} pos-int?]]])

(def mente-sa-corpo-sao-schema [:map {:closed true}
                                [:cpf [:fn {:error/message v/msg-valor-incorreto} v/validar-cpf?]]
                                [:hrs-meditadas-ultimos-dias [:fn {:error/message v/msg-valor-incorreto} double?]]
                                [:cd-plano [:fn {:error/message v/msg-valor-incorreto} pos-int?]]])

(def plano-schema [:map {:closed true}
                   [:cnpj [:fn {:error/message v/msg-valor-incorreto} v/validar-cnpj?]]
                   [:nome [:fn {:error/message v/msg-valor-incorreto} v/str?]]
                   [:descricao [:fn {:error/message v/msg-valor-incorreto} v/str?]]])

