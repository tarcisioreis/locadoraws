# locadoraws
Modelo de serviços para locadora de filmes para aprendizagem de WebServices básicos

Especificação de uso dos webservices da locadora de filme:

curl -X PUT "http://localhost:8080/locadoraws/criarUsr?nome=XXXX9999&email=XXX9999&senha=YYYY999&telefone=99 999999999"

Procedimento - criarUsr         
Descrição    - Fazer cadastro de usuários

Método                 - PUT 
Parâmetros de Entrada  - nome, email, senha, telefone
Formato dos Parâmetros - Alfanúmerico

Retorno - sáida JSON - ID do usuário e Mensagem de sucesso ou erro

curl -X POST "http://localhost:8080/locadoraws/logonUsr?email=XXXX&senha=YYYY"

Procedimento - logonUsr
Descrição    - Autenticacao no sistema

Método                 - POST
Parâmetros de Entrada  - email, senha
Formato dos Parâmetros - Alfanúmerico

Retorno - sáida JSON - ID do usuário e Mensagem de sucesso ou erro

curl -X POST "http://localhost:8080/locadoraws/logoffUsr?idUsuario=9999"

Procedimento - logoffUsr
Descrição    - Saída no sistema

Método                 - POST
Parâmetros de Entrada  - idUsuario
Formato dos Parâmetros - Números

Retorno - sáida JSON - ID do usuário e Mensagem de sucesso ou erro

curl -X GET "http://localhost:8080/locadoraws/listFilme"

Procedimento - listFilme
Descrição    - Listagem de Filmes disponíveis

Método                 - GET
Parâmetros de Entrada  - SEM PARÂMETROS
Formato dos Parâmetros - SEM FORMATO

Retorno - sáida JSON - Título e diretor do filme

curl -X PUT "http://localhost:8080/locadoraws/locaFilme?idFilme=9999&idUsuario=9999&dataRetirada=9999-99-99&dataEntrega=9999-99-99"

Procedimento - locaFilme         
Descrição    - Locação de Filme

Método                 - PUT 
Parâmetros de Entrada  - idFilme, idUsuario, dataRetirada, dataEntrega
Formato dos Parâmetros - ids - Números e datas - YYYY-MM-DD

Retorno - sáida JSON - ID da locação e Mensagem de sucesso ou erro

curl -X PUT "http://localhost:8080/locadoraws/devoFilme?idFilme=9999&dataEntrega=9999-99-99"

Procedimento - devoFilme         
Descrição    - Devolução de Filme

Método                 - PUT 
Parâmetros de Entrada  - idFilme, dataEntrega
Formato dos Parâmetros - ids - Números e datas - YYYY-MM-DD

Retorno - sáida JSON - ID da devolução e Mensagem de sucesso ou erro

curl -X GET "http://localhost:8080/locadoraws/pesqFilmeTitulo?titulo=XXXX9999"

Procedimento - pesqFilmeTitulo
Descrição    - Pesquisa de Filmes por Títulos

Método                 - GET
Parâmetros de Entrada  - titulo
Formato dos Parâmetros - Alfanúmerico

Retorno - sáida JSON - Título, diretor do filme e status para disponíveis ou locados

