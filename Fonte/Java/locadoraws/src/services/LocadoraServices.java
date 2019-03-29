/**
 *
 *		Classe Implementação dos services para Locadora
 *
 */
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import bean.Devolucao;
import bean.Estoque;
import bean.Filme;
import bean.Locacao;
import bean.Usuario;
import dao.DevolucaoDao;
import dao.EstoqueDao;
import dao.FilmeDao;
import dao.LocacaoDao;
import dao.UsuarioDao;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
@Path("/")
public class LocadoraServices {

	private Usuario usuario;
	private Filme filme;
	private Locacao locacao;
	private Devolucao devolucao;
	private Estoque estoque;
	private UsuarioDao usuarioDao;
	private FilmeDao filmeDao;
	private LocacaoDao locacaoDao;
	private DevolucaoDao devolucaoDao;
	private EstoqueDao estoqueDao;
	
    private int id;
	private String mensagem;
	private String pattern;

	/*
	 * 
	 * Procedimento para criar usuario 
	 * 
	 * Method: PUT
	 * URL: http://host:port/locadoraws/criarUsr?nome=XXXX9999&email=XXX999&senha=YYYY999&telefone=99 999999999
	 * 
	 * Pode ser testado usando SOAPUI, POSTMAN ou via curl 
	 * 
	 * $ curl -X PUT "http://localhost:8080/locadoraws/criarUsr?nome=XXXX9999&email=XXX9999&senha=YYYY999&telefone=99 999999999"
	 * 
	 */

	@PUT
	@Path("/criarUsr")
	@Produces("application/json")
	public String criarUsr(@QueryParam("nome")     String nome, 
			               @QueryParam("email")    String email, 
			               @QueryParam("senha")    String senha, 
			               @QueryParam("telefone") String telefone) {
		int status = 0;
		
		usuario = new Usuario(nome,email,senha,telefone,status);
		usuarioDao = new UsuarioDao();
		id = 0;
		
		if (usuarioDao.validar(usuario) == 0) {
			id = usuarioDao.insert(usuario);
			if (id > 0) {
				mensagem = "Usuário cadastrado com sucesso!";
			} else {
				mensagem = "Ocorreu um erro no cadastro do Usuário!";
			}
		}
	    
		pattern = "{ \"id\":\"%s\", \"mensagem\":\"%s\"\"}";
	    return String.format(pattern, id, mensagem);
	}

	/*
	 * 
	 * Procedimento para logon de usuario 
	 * 
	 * Method: POST
	 * URL: http://host:port/locadoraws/logonUsr?email=XXX9999&senha=YYYY9999
	 * 
	 * Pode ser testando usando SOAPUI, POSTMAN ou via curl 
	 * 
	 * $ curl -X POST "http://localhost:8080/locadoraws/logonUsr?email=XXX9999&senha=YYYY9999"
	 * 
	 */
	
	@POST
	@Path("/logonUsr")
	@Produces("application/json")
	public String logonUsr(@QueryParam("email") String email, 
			               @QueryParam("senha") String senha) {
		int retorno = 0;
		usuario    = new Usuario();
		
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		usuarioDao = new UsuarioDao();
		
		if (usuarioDao.validarLogin(usuario) == 0) {
			usuario.setStatus(1);
			usuario.setId(usuarioDao.searchByEmail(usuario));
			
			retorno = usuarioDao.update(usuario);
			if (retorno > 0) {
				mensagem = "Usuário logado com sucesso!";
			} else {
				mensagem = "Ocorreu um erro no logon do Usuário!";
			}
		}
	    
		pattern = "{ \"id\":\"%s\", \"mensagem\":\"%s\"\"}";
	    return String.format(pattern, usuario.getId(), mensagem);
	}

	/*
	 * 
	 * Procedimento para logoff de usuario 
	 * 
	 * Method: POST
	 * URL: http://host:port/locadoraws/logoffUsr?idUsuario=9999
	 * 
	 * Pode ser testado usando SOAPUI, POSTMAN ou via curl 
	 * 
	 * $ curl -X POST "http://localhost:8080/locadoraws/logoffUsr?idUsuario=9999"
	 * 
	 */

	@POST
	@Path("/logoffUsr")
	@Produces("application/json")
	public String logoffUsr(@QueryParam("idUsuario") int idUsuario) {
		int retorno = 0;
		usuario    = new Usuario();
		
		usuario.setId(idUsuario);
		usuario.setStatus(0);
		
		usuarioDao = new UsuarioDao();
		
		retorno = usuarioDao.update(usuario);
		if (retorno > 0) {
			mensagem = "Usuário deslogado com sucesso!";
		} else {
			mensagem = "Ocorreu um erro no logoff do Usuário!";
		}
	    
		pattern = "{ \"id\":\"%s\", \"mensagem\":\"%s\"\"}";
	    return String.format(pattern, usuario.getId(), mensagem);
	}

	/*
	 * 
	 * Procedimento de pesquisa de filmes por titulos
	 * 
	 * Method: GET
	 * URL: http://host:port/locadoraws/pesqFilmeTitulo?titulo=XXXX9999
	 * 
	 * Pode ser testado usando SOAPUI, POSTMAN ou via curl 
	 * 
	 * $ curl -X GET "http://localhost:8080/locadoraws/pesqFilmeTitulo?titulo=XXXX9999"
	 * 
	 */
	
	@GET
	@Path("/pesqFilmeTitulo")
	@Produces("application/json")
	public String pesqFilmeTitulo(@QueryParam("titulo") String titulo) {
		StringBuilder strPattern = new StringBuilder();
		StringBuilder strDados   = new StringBuilder();
		
		List<Filme> listaFilmes  = new ArrayList<Filme>();
	
		filmeDao = new FilmeDao();
		filme = new Filme();
		
		filme.setTitulo(titulo);
		
		listaFilmes = filmeDao.search(filme);
			
		if (listaFilmes.isEmpty()) {
			mensagem = "Nenhum filme com titulo " + titulo + " foi encontrado.";
			pattern = "{ \"mensagem\":\"%s\" }";
		    return String.format(pattern, mensagem);
		} else {
			strPattern.append("{ ");
			for(int i = 0; i < listaFilmes.size(); i++) {
				if (listaFilmes.size() > 1 && i > 0) {
					strDados.append(" ");
				}

				strDados.append("\"filme" + i + "\": { ");
					
				strDados.append("\"id\":\""      + listaFilmes.get(i).getId() + "\", ");
				strDados.append("\"titulo\":\""  + listaFilmes.get(i).getTitulo() + "\", ");
				strDados.append("\"diretor\":\"" + listaFilmes.get(i).getDiretor() + "\", ");
				strDados.append("\"status\":\""  + (listaFilmes.get(i).getLocado() > 0?"Locado":"Disponível") + "\"");
					
				strDados.append("},");
			}

			if (strDados.substring(strDados.toString().trim().length()-1).equals(",")) {
				strDados.deleteCharAt(strDados.toString().trim().length()-1);
			}
				
			strPattern.append(strDados.toString());
				
			strPattern.append("} ");
		}
		
		return strPattern.toString();
	}

	/*
	 * 
	 * Procedimento de listagem de filmes disponíveis
	 * 
	 * Method: GET
	 * URL: http://host:port/locadoraws/listFilme
	 * 
	 * Pode ser testado usando SOAPUI, POSTMAN ou via curl 
	 * 
	 * $ curl -X GET "http://localhost:8080/locadoraws/listFilme"
	 * 
	 */
	
	@GET
	@Path("/listFilme")
	@Produces("application/json")
	public String listFilme() {
		StringBuilder strPattern = new StringBuilder();
		StringBuilder strDados   = new StringBuilder();

		List<Filme> listaFilmes  = new ArrayList<Filme>();
	
		filmeDao = new FilmeDao();
		listaFilmes = filmeDao.listFilme();
			
		if (listaFilmes.isEmpty()) {
			mensagem = "Nenhum filme disponível no momento.";
			pattern = "{ \"mensagem\":\"%s\" }";
		    return String.format(pattern, mensagem);
		} else {
			strPattern.append("{ ");
			for(int i = 0; i <= listaFilmes.size(); i++) {
				if (listaFilmes.size() > 1 && i > 0) {
					strDados.append(" ");
				}

				strDados.append("\"filme" + i + "\": { ");
					
				strDados.append("\"id\":\""      + listaFilmes.get(i).getId() + "\", ");
				strDados.append("\"titulo\":\""  + listaFilmes.get(i).getTitulo() + "\", ");
				strDados.append("\"diretor\":\"" + listaFilmes.get(i).getDiretor() + "\" ");
					
				strDados.append("},");
			}

			if (strDados.substring(strDados.toString().trim().length()-1).equals(",")) {
				strDados.deleteCharAt(strDados.toString().trim().length()-1);
			}
				
			strPattern.append(strDados.toString());
				
			strPattern.append("} ");
		}
		
		return strPattern.toString();
	}

	/*
	 * 
	 * Procedimento para locação de filmes 
	 * 
	 * Method: PUT
	 * URL: http://host:port/locadoraws/locaFilme?idFilme=9999&idUsuario=9999&dataRetirada=9999-99-99&dataEntrega=9999-99-99
	 * 
	 * Pode ser testado usando SOAPUI, POSTMAN ou via curl 
	 * 
	 * $ curl -X PUT "http://localhost:8080/locadoraws/locaFilme?idFilme=9999&idUsuario=9999&dataRetirada=9999-99-99&dataEntrega=9999-99-99"
	 * 
	 */

	@PUT
	@Path("/locaFilme")
	@Produces("application/json")
	public String locaFilme(@QueryParam("idFilme")      int idFilme,
			                @QueryParam("idUsuario")    int idUsuario,
			                @QueryParam("dataRetirada") String dataRetirada, 
			                @QueryParam("dataEntrega")  String dataEntrega) {
		estoque = new Estoque();
		locacao = new Locacao();

		locacaoDao = new LocacaoDao();
		estoqueDao = new EstoqueDao();

		try {
			pattern = "yyyy-MM-dd";
			
			SimpleDateFormat sdfData = new SimpleDateFormat(pattern);

			locacao.setIdFilme(idFilme);
			locacao.setIdUsuario(idUsuario);
			locacao.setDataRetirada(sdfData.parse(dataRetirada));
			locacao.setDataEntrega(sdfData.parse(dataEntrega));
			
			estoque.setIdFilme(idFilme);
		} catch (ParseException e) {
			pattern = "{ \"error\":\"%s\", \"mensagem\":\"%s\"\"}";
			mensagem = "Ocorreu um erro no formato de alguma das datas de Retirada ou Entrega. Verifique.";
		    return String.format(pattern, e.getMessage(), mensagem);
		}
		
		id = 0;
		
		if (estoqueDao.search(estoque).get(0).getQuantidade() > 0) {
			id = locacaoDao.insert(locacao);
			if (id > 0) {
				mensagem = "Locação efetuada com sucesso!";
			} else {
				mensagem = "Ocorreu um erro na locação do filme!";
			}
		} else {
			pattern = "{ \"mensagem\":\"%s\"\"}";
			mensagem = "Filme encontra-se indisponível para locação.";
		    return String.format(pattern, mensagem);
		}
	    
		pattern = "{ \"id\":\"%s\", \"mensagem\":\"%s\"\"}";
	    return String.format(pattern, id, mensagem);
	}

	/*
	 * 
	 * Procedimento para devolução de filmes 
	 * 
	 * Method: PUT
	 * URL: http://host:port/locadoraws/devoFilme?idFilme=9999&dataEntrega=9999-99-99
	 * 
	 * Pode ser testado usando SOAPUI, POSTMAN ou via curl 
	 * 
	 * $ curl -X PUT "http://localhost:8080/locadoraws/devoFilme?idFilme=9999&dataEntrega=9999-99-99"
	 * 
	 */

	@PUT
	@Path("/devoFilme")
	@Produces("application/json")
	public String devoFilme(@QueryParam("idFilme")     int idFilme,
			                @QueryParam("dataEntrega") String dataEntrega) {
		devolucao = new Devolucao();
		devolucaoDao = new DevolucaoDao();

		try {
			pattern = "yyyy-MM-dd";
			
			SimpleDateFormat sdfData = new SimpleDateFormat(pattern);

			devolucao.setIdFilme(idFilme);
			devolucao.setDataEntrega(sdfData.parse(dataEntrega));
		} catch (ParseException e) {
			pattern = "{ \"error\":\"%s\", \"mensagem\":\"%s\"\"}";
			mensagem = "Ocorreu um erro no formato da data de Entrega. Verifique.";
		    return String.format(pattern, e.getMessage(), mensagem);
		}
		
		id = 0;
		
		if (devolucaoDao.search(devolucao).isEmpty()) {
			id = devolucaoDao.insert(devolucao);
			if (id > 0) {
				mensagem = "Devolução efetuada com sucesso!";
			} else {
				mensagem = "Ocorreu um erro na devolução do filme!";
			}
		} else {
			pattern  = "{ \"mensagem\":\"%s\"\"}";
			mensagem = "Filme encontra-se indisponível para locação ou já foi devolvido. Verifique.";
		    return String.format(pattern, mensagem);
		}
	    
		pattern = "{ \"id\":\"%s\", \"mensagem\":\"%s\"\"}";
	    return String.format(pattern, id, mensagem);
	}
	
}
