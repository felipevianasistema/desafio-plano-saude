(ns plano-saude.service.ficha-inclusao-service
  (:require [io.pedestal.http.body-params :as bp]
            [io.pedestal.http.route.definition.table :as table]
            [plano-saude.controller.ficha-inclusao-controller :as controller]))

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
  (let [ficha "/ficha-inclusao/"]
    (table/table-routes
     [[(str ficha "cadastrar") :post cadastrar :route-name :cadastrar]
      [(str ficha "obter-todos") :get obter-todos :route-name :obter-todos]
      [(str ficha "obter-ativos") :get obter-ativos :route-name :obter-ativos]
      [(str ficha "obter/:id") :get obter-por-id :route-name :obter-por-id]
      [(str ficha "atualizar-status/:id") :put atualizar-status :route-name :atualizar-status]
      [(str ficha "atualizar/:id") :put atualizar :route-name :atualizar]])))