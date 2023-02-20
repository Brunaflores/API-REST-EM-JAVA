CREATE DATABASE labeach;


create table bairro (
id BIGSERIAL primary key,
nome varchar (100) unique not null,
descricao varchar (255),
populacao integer
);


create table praia(
id bigserial primary key,
bairro_id bigserial references bairro on delete restrict,
nome varchar (100) unique not null,
acessibilidade varchar not null,
status varchar (20) not null
);

INSERT INTO public.bairro (nome,descricao,populacao) VALUES
	 ('Agronômica','Bairro nobre com ampla área verde e vista para o mar',7500),
	 ('Balneário','Bairro litorâneo com praias e áreas de lazer',11000),
	 ('Cacupé','Bairro residencial com casas de alto padrão',2500),
	 ('Campeche','Bairro com praia e lagoa, comércio e áreas verdes',30000),
	 ('Canasvieiras','Bairro litorâneo com grande movimento turístico',12000),
	 ('Capoeiras','Bairro com ampla oferta de serviços e comércio',25000),
	 ('Coqueiros','Bairro com praia, parques e restaurantes',17000),
	 ('Estreito','Bairro residencial com acesso fácil à Ilha de Santa Catarina',35000),
	 ('Ingleses','Bairro litorâneo com comércio e grande oferta de turismo',25000),
	 ('Itacorubi','Bairro com ampla oferta de serviços e instituições de ensino',15000);
INSERT INTO public.bairro (nome,descricao,populacao) VALUES
	 ('Jardim Atlântico','Bairro com praias e parques com vista para o mar',6500),
	 ('Jurerê Internacional','Bairro residencial com casas de alto padrão e turismo de luxo',5000),
	 ('Lagoa da Conceição','Bairro com lagoa e praias, áreas de lazer e comércio',15000),
	 ('Pantanal','Bairro residencial próximo a universidades e serviços públicos',10000),
	 ('Ribeirão da Ilha','Bairro histórico com casario açoriano e produção de ostras',2000),
	 ('Saco dos Limões','Bairro residencial próximo a universidades e comércio',8000),
	 ('Santo Antônio de Lisboa','Bairro histórico com casario açoriano e restaurantes',3000),
	 ('Trindade','Bairro com universidades, comércio e áreas de lazer',20000),
	 ('Vargem Pequena','Bairro rural com produção de hortifrutigranjeiros e áreas de lazer',5000),
	 ('Canto','Bairro residencial com vista para o mar e a lagoa',7000);
	 
	INSERT INTO public.praia (nome,acessibilidade,status) VALUES
	 ('Praia do Balneário','não','imprópria'),
	 ('Praia de Cacupé','sim','imprópria'),
	 ('Praia do Campeche','sim','própria'),
	 ('Praia de Canasvieiras','não','própria'),
	 ('Praia do Ribeirão','Sim','própria'),
	 ('Praia da Agronômica','não','própria');