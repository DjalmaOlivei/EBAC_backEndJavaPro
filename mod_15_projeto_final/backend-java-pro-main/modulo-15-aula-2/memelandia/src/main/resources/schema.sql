-- ============================================================================
-- Schema SQL para o banco MySQL (entidades CategoriaMeme e Meme)
-- A entidade Usuario fica no segundo banco de dados (MongoDB / banco separado),
-- portanto a tabela "usuario" e as Foreign Keys para ela foram removidas.
-- ============================================================================

CREATE TABLE IF NOT EXISTS categoria_meme (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  data_cadastro DATE NOT NULL,
  usuario_id BIGINT,
  CONSTRAINT pk_categoriameme PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS meme (
  id BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  descricao VARCHAR(255) NOT NULL,
  data_cadastro DATE NOT NULL,
  categoria_meme_id BIGINT,
  usuario_id BIGINT,
  CONSTRAINT pk_meme PRIMARY KEY (id),
  CONSTRAINT fk_meme_on_categoria_meme FOREIGN KEY (categoria_meme_id) REFERENCES categoria_meme (id)
);
