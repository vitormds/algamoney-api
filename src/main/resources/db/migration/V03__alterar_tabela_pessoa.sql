ALTER TABLE pessoa CHANGE logadouro logradouro varchar(50); 
ALTER TABLE pessoa ADD COLUMN ativo boolean not null after nome; 