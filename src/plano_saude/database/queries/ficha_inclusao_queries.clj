(ns plano-saude.database.queries.ficha-inclusao-queries)


(def obter-todos  "SELECT fi.id, fi.nome, fi.cpf, TO_CHAR(fi.dt_admissao, 'DD/MM/YYYY') as dt_admissao, fi.email,
                   fi.endereco, fi.peso_kg, fi.altura_cm, fi.hrs_meditadas_ultimos_dias,
                   fi.ativo, TO_CHAR(fi.dt_registro, 'DD/MM/YYYY') as dt_registro, fi.cd_plano,
                   p.nome as plano
                   FROM ficha_inclusao fi 
                   INNER JOIN plano p ON (p.id = fi.cd_plano) 
                   ORDER BY id")

(def obter-ativos "SELECT fi.id, fi.nome, fi.cpf, TO_CHAR(fi.dt_admissao, 'DD/MM/YYYY') as dt_admissao, fi.email,
                   fi.endereco, fi.peso_kg, fi.altura_cm, fi.hrs_meditadas_ultimos_dias,
                   fi.ativo, TO_CHAR(fi.dt_registro, 'DD/MM/YYYY') as dt_registro, fi.cd_plano,
                   p.nome as plano
                   FROM ficha_inclusao fi 
                   INNER JOIN plano p ON (p.id = fi.cd_plano) 
                   WHERE fi.ativo = true  
                   ORDER BY id")

(def obter-por-id "SELECT fi.id, fi.nome, fi.cpf, TO_CHAR(fi.dt_admissao, 'DD/MM/YYYY') as dt_admissao, fi.email,
                   fi.endereco, fi.peso_kg, fi.altura_cm, fi.hrs_meditadas_ultimos_dias,
                   fi.ativo, TO_CHAR(fi.dt_registro, 'DD/MM/YYYY') as dt_registro, fi.cd_plano,
                   p.nome as plano
                   FROM ficha_inclusao fi 
                   INNER JOIN plano p ON (p.id = fi.cd_plano) 
                   WHERE fi.id = ? 
                   ORDER BY id")