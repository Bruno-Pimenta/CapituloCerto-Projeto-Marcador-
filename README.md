
# Marcador de Livro em JAVA

## Descrição do Projeto
Este é um programa simples desenvolvido em Java, no qual um usuário tem a capacidade de salvar o progresso da leitura de um livro. Esse marcador possibilita orientar de maneira eficiente a retomada da leitura, funcionando efetivamente como um marcador tradicional, porém com um mecanismo adicional que permite realizar uma breve recapitulação quando necessário.

## Funcionalidades
### Menu Inicial
1. Login
* O usuário pode realizar o login, caso já esteja devidamente cadastrado.

2. Cadastrar
* O usuário pode realizar o seu cadastro. Durante esse processo, é solicitado um nome de usuário único e uma senha, sendo que apenas o hash da senha é armazenado no banco de dados, garantindo a segurança das informações.

3. Sair
* Encerra a aplicação de forma adequada.

### Menu do Usuário
1. Adicionar marcador
* O usuário tem a opção de cadastrar um marcador. Para isso, é necessário preencher alguns dados essenciais, como o nome do livro, o número total de páginas do livro, a página atual de leitura e atribuir uma nota à obra, variando de 0 a 10, sendo possível atualizá-la conforme necessário.
* As anotações e os nomes dos autores são considerados dados opcionais, porém, sua inclusão pode ser benéfica para orientar na retomada da leitura e facilitar as buscas por marcadores, especialmente quando os nomes dos autores servirem como base para pesquisa.

2. Buscar marcador por id
* Cada marcador cadastrado tem seu identificador único. Esse tipo de busca é feito por esse identificador para o usuário logado. Caso o identicador seja válido, será retornado o marcador somente se ele pertencer ao usuário logado.

3. Buscar todos os marcadores
* Busca todos os marcadores do usuário logado.

4. Buscar marcadores por categoria
* As categorias devem ser cadastradas diretamente na base de dados. No processo de cadastro do marcador pelo usuário, caso a categoria presente no registro do marcador exista na base de dados, o marcador será associado a essa(s) categoria(s). 

5. Buscar marcadores por autor
* Busca todos os marcadores do usuário logado referente ao autor buscado.

6. Atualizar página marcada
* Essa é a funcionalidade que caracteriza a aplicação como um marcador. Constantemente o usuário deve atualizar a página marcada conforme a progressão da leitura. 
* O campo destinado à página atual também desempenha a função de determinar o estado da leitura, ou seja, se foi iniciada, está em andamento ou foi concluída.

7. Atualizar avaliação da obra
* Serve para avaliar a obra. Pode ser constantemente alterada conforme a progressão da leitura. 

8. Atualizar anotação da obra
* Este recurso serve como uma ferramenta de orientação, recapitulação e observação sobre a obra.
* Pode ser constantemente atualizado, proporcionando uma recapitulação eficaz que auxilia na retomada da leitura, contextualizando e resumindo o que foi lido até o momento.

9. Excluir marcador
* Auto explicativo. 

10. Logout
* Sai da conta do usuário, voltando para o menu inicial.

## Tecnologias Utilizadas
* Java
* JBCrypt
* MySQL

## Como executar o Projeto
### Pré-requisitos:
* Java 17
* JBCrypt 0.4
* MySQL 8.2.0

### Instruções de Configuração:

* Crie o Banco de Dados:
CREATE DATABASE capitulo_certo;

* Crie a Tabela Usuario:
```sql
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    senha TEXT NOT NULL
);

CREATE INDEX idx_usuario_id ON Usuario(id);
```

* Crie a Tabela Marcador_Livros:
```sql
CREATE TABLE Marcador_Livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    numero_de_paginas INT NOT NULL,
    pagina_atual INT NOT NULL,
    status_atual VARCHAR(50) NOT NULL,
    nota DECIMAL(3, 2) CHECK (nota >= 0 AND nota <= 10),
    anotacoes TEXT,
    autores TEXT,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES Usuario(id) ON DELETE CASCADE
);

CREATE INDEX idx_marcador_livros_id ON Marcador_Livros(id);
```

* Crie a Tabela Palavras_Chave:
```sql
CREATE TABLE Palavras_Chave (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

CREATE INDEX idx_palavras_chave_id ON Palavras_Chave(id);
```

* Crie a Tabela Marcador_LivrosPalavras_Chave:
```sql
CREATE TABLE Marcador_LivrosPalavras_Chave (
    marcador_livro_id INT,
    palavra_chave_id INT,
    FOREIGN KEY (marcador_livro_id) REFERENCES Marcador_Livros(id) ON DELETE CASCADE,
    FOREIGN KEY (palavra_chave_id) REFERENCES Palavras_Chave(id) ON DELETE CASCADE
);

CREATE INDEX idxfk_marcador_livro_id ON Marcador_LivrosPalavras_Chave(marcador_livro_id);
CREATE INDEX idxfk_palavra_chave_id ON Marcador_LivrosPalavras_Chave(palavra_chave_id);
```

# Autor
Bruno Henrique Pimenta
