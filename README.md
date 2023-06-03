

### SQL Data Base

Aqui esta foi utilizado ![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)

````
CREATE TABLE Paciente 
( 
 telefone VARCHAR(11),  
 CPF VARCHAR(11),  
 nome VARCHAR(100),  
 regiaoMoradia VARCHAR(n100),  
 idPaciente INT PRIMARY KEY,  
 endereco VARCHAR(300),  
 dataNascimento DATE,  
 UNIQUE (CPF)
); 

CREATE TABLE Vacinas 
( 
 idVacina INT PRIMARY KEY,  
 descricao VARCHAR(300),  
 nome VARCHAR(100),  
 UNIQUE (nome)
); 

CREATE TABLE RegistroVacina 
( 
 dataVacinacao DATE,  
 idVacina INT PRIMARY KEY,  
 idPaciente INT PRIMARY KEY,  
); 

ALTER TABLE RegistroVacina ADD FOREIGN KEY(idVacina) REFERENCES Vacinas (idVacina)
ALTER TABLE RegistroVacina ADD FOREIGN KEY(idPaciente) REFERENCES Paciente (idPaciente)

````
