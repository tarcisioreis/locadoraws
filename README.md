# locadoraws
Modelo de serviços para locadora de filmes para aprendizagem de WebServices básicos

Especificação de uso dos webservices da locadora de filme:

Procedimento - criarUsr         
Descrição    - Fazer cadastro de usuários

Método                 - PUT 
Parâmetros de Entrada  - nome, email, senha, telefone
Formato dos Parâmetros - Alfanúmerico

Retorno - sáida JSON - ID do usuário e Mensagem de sucesso ou erro


Procedimento - logonUsr
Descrição    - Autenticacao no sistema

Método                 - POST
Parâmetros de Entrada  - email, senha
Formato dos Parâmetros - Alfanúmerico

Retorno - sáida JSON - ID do usuário e Mensagem de sucesso ou erro

