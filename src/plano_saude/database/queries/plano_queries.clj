(ns plano-saude.database.queries.plano-queries)


(def obter-todos  "SELECT id, cnpj, nome, descricao, ativo, TO_CHAR(dt_registro, 'DD/MM/YYYY') as dt_registro FROM plano ORDER BY id")
(def obter-ativos "SELECT id, cnpj, nome, descricao, ativo, TO_CHAR(dt_registro, 'DD/MM/YYYY') as dt_registro FROM plano WHERE ativo = true  ORDER BY id")
(def obter-por-id "SELECT id, cnpj, nome, descricao, ativo, TO_CHAR(dt_registro, 'DD/MM/YYYY') as dt_registro FROM plano WHERE id = ? ORDER BY id")