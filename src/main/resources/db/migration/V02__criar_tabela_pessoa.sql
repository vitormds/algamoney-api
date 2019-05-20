CREATE TABLE IF NOT EXISTS pessoa (
codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
nome VARCHAR(50) NOT NULL,
logadouro VARCHAR(50),
numero VARCHAR(8),
complemento VARCHAR(50),
bairro VARCHAR(50),
cep VARCHAR(15),
cidade VARCHAR(40),
estado VARCHAR(40)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Silvia','Rua aurora','25','Em frente casa','Federação','40209364','Salvador','BA');
 INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Maria','Rua aurora1','10','Em frente rua','Vasco','40090364','Salvador','BA');
 INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Marcia','Rua aurora2','22','Em frente lachonente','Tranquedo','48920364','Salvador','BA');
 INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Silvana','Rua aurora3','43','Em frente curso','Rio vermelho','409970364','Salvador','BA');
 INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Matilde','Rua aurora4','50','Em frente mata','ondina','40220300','Salvador','BA');
 INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('carla','Rua aurora5','55','Em frente rua de baix','pernabues','40229964','Salvador','BA');
 INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('vitor','Rua aurora6','66','Em frente policia','barra','40220764','Salvador','BA');
 INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Ramos','Rua aurora7','75','Em frente hospital','Federação','40220654','Salvador','BA');
 INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Soltinho','Rua aurora8','67','Em frente medeiros','Sao Cristovao','40220312','Salvador','BA');
 INSERT INTO pessoa (nome, logadouro, numero , complemento, bairro , cep, cidade, estado )
 VALUES ('Katia','Rua aurora9','27','Em frente puteiro','Imbuí','40220894','Salvador','BA');
