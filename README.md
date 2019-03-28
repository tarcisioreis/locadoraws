# locadoraws
Modelo de serviços para locadora de filmes para aprendizagem de WebServices básicos

Especificação de uso dos webservices da locadora de filme:

Procedimento     Descrição                        Método   Parâmetros de Entrada                          Formato dos Parâmetros             Retorno - saída 

criarUsr         Fazer cadastro de usuários       PUT      nome, email, senha, telefone                   Alfanúmerico                       Mensagem de sucesso ou erro
logonUsr         Autenticacao no sistema          POST     email, senha                                   Alfanúmerico                       Mensagem de sucesso ou erro
logoffUsr        Saída do sistema                 POST     idUsuario                                      Números                            Mensagem de sucesso ou erro
listFilme        Listagem de Filmes disponíveis   GET      sem Parâmetros                                 sem Formato                        Titulo e diretor do filme
locaFilme        Locação de Filme                 PUT      idFilme, idUsuario, dataRetirada, dataEntrega  ids - Números, datas - YYYY-MM-DD  Mensagem de sucesso ou erro e ID da Locação
devoFilme        Devolução de Filme               PUT      idFilme, dataEntrega                           datas - YYYY-MM-DD                 Mensagem de sucesso ou erro e ID da Devolução
pesqFilmeTitulo  Pesquisa filmes por Título       GET      titulo                                         Alfanúmerico                       Título, diretor do filme e status para disponíveis ou locados                                    
