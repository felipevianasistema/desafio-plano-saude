(ns plano-saude.service.plano-service
  (:require [io.pedestal.http.body-params :as bp]
            [io.pedestal.http.route.definition.table :as table]
            [plano-saude.controller.plano-controller :as controller]))

(defn- cadastrar
  "Cadastro da ficha de inclusão"
  [request]
  (let [body-params (:json-params (bp/json-parser request))]
    (controller/cadastrar body-params)))

(defn- obter-todos
  "Retorna todos independente do status true/false"
  [_]
  (controller/obter-todos))

(defn- obter-ativos
  "Retorna todos com status true"
  [_]
  (controller/obter-ativos))

(defn- obter-por-id
  "Retorna de acordo com o id informado"
  [request]
  (let [path-params (:path-params request)]
    (controller/obter-por-id path-params)))

(defn- atualizar-status
  "Atualiza somente o status true/false"
  [request]
  (let [path-params (:path-params request)
        body-params (:json-params (bp/json-parser request))]
    (controller/atualizar-status path-params body-params)))

(defn- atualizar
  "Atualiza as informações"
  [request]
  (let [path-params (:path-params request)
        body-params (:json-params (bp/json-parser request))]
    (controller/atualizar path-params body-params)))

(def rotas
  (let [path "/plano/"]
    (table/table-routes
     [[(str path "cadastrar") :post cadastrar :route-name :cadastrar]
      [(str path "obter-todos") :get obter-todos :route-name :obter-todos]
      [(str path "obter-ativos") :get obter-ativos :route-name :obter-ativos]
      [(str path "obter/:id") :get obter-por-id :route-name :obter-por-id]
      [(str path "atualizar-status/:id") :put atualizar-status :route-name :atualizar-status]
      [(str path "atualizar/:id") :put atualizar :route-name :atualizar]])))