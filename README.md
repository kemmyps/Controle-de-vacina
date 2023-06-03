### Banco de Dados SQL com PostgreSQL ![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)

Neste projeto, utilizei o PostgreSQL como sistema de gerenciamento de banco de dados, mas você pode usar qualquer outro desde que o resultado seja um banco de dados relacional. Dependendo da sua escolha, pode ser que os comandos SQL tenham algumas mudanças. Além disso, eu resolvi usar o Postico para realizar as operações de criar e editar minhas tabelas. 

Em resumo, o PostgreSQL é o próprio sistema de gerenciamento de banco de dados, enquanto o Postico é uma ferramenta que permite aos usuários interagir com o PostgreSQL por meio de uma interface gráfica amigável.

```sql
CREATE TABLE Paciente 
( 
 telefone VARCHAR(11),  
 CPF VARCHAR(11),  
 nome VARCHAR(100),  
 regiaoMoradia VARCHAR(100),  
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

ALTER TABLE RegistroVacina ADD FOREIGN KEY(idVacina) REFERENCES Vacinas (idVacina);
ALTER TABLE RegistroVacina ADD FOREIGN KEY(idPaciente) REFERENCES Paciente (idPaciente);
