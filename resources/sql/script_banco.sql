--###########################################
--# Remove as tabelas
--###########################################

DROP TABLE IF EXISTS ficha_inclusao;
DROP TABLE IF EXISTS plano;

--###########################################
--# Criação das tabelas
--###########################################
create table plano(
	id        		serial         	not null,
	cnpj           	varchar(18)    	not null,
	nome       		varchar(70)    	not null,
	descricao       varchar(100)   	not null,
	ativo           bool 	   		not null,
	dt_registro     date 			DEFAULT CURRENT_DATE,
primary key(id),
CONSTRAINT unique_cnpj UNIQUE (cnpj)
);

create table ficha_inclusao(
	id            				serial      not null,
	nome              			varchar(70) null,
	cpf               			varchar(14) not null,
	dt_admissao             	date     null,
	email                		varchar(60)  null,
	endereco                	varchar(150)  null,
	peso_kg                		decimal  null,
	altura_cm               	decimal  null,
	hrs_meditadas_ultimos_dias  decimal  null,
	ativo                		bool 	 not null,
	dt_registro     			date 	 DEFAULT CURRENT_DATE,
	cd_plano                	integer  not null,
primary key (id),
constraint fk_cd_plano foreign key (cd_plano) references plano (id),
CONSTRAINT unique_cpf_cd_plano UNIQUE (cpf, cd_plano)
);

--###########################################
--# Cadastro dos Planos
--###########################################

INSERT INTO public.plano(cnpj, nome, descricao, ativo, dt_registro)	VALUES ('99066063000108', 'Norte Europa', 'Plano de saúde', true, now());
INSERT INTO public.plano(cnpj, nome, descricao, ativo, dt_registro)	VALUES ('38740774000121', 'Pampulha Intermédica', 'Plano de saúde', true, now());
INSERT INTO public.plano(cnpj, nome, descricao, ativo, dt_registro)	VALUES ('26488262000130', 'Dental Sorriso', 'Plano odontológico', true, now());
INSERT INTO public.plano(cnpj, nome, descricao, ativo, dt_registro)	VALUES ('88784378000174', 'Mente Sã, Corpo São', 'Plano de saúde mental', true, now());

