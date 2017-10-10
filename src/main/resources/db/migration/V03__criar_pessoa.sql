CREATE TABLE pessoa
(
  codigo      BIGINT(20)   PRIMARY KEY AUTO_INCREMENT,
  nome        VARCHAR(35)  NOT NULL,
  logradouro  VARCHAR(255) NOT NULL,
  numero      VARCHAR(50)  NOT NULL,
  complemento VARCHAR(255),
  bairro      VARCHAR(255) NOT NULL,
  cep         VARCHAR(255) NOT NULL,
  cidade      VARCHAR(255) NOT NULL,
  estado      VARCHAR(2)   NOT NULL,
  ativo       BOOL         NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8;