package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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

public class LocadoraServicesTest {

	//@Test
	public void testCriarUsrOK() {
		String nome, email, senha, telefone, mensagem;
		int status;
		
		nome = "Teste";
		email = "teste@teste.com.br";
		senha = "123";
		telefone = "51 984904355";
		status = 0;
		
		Usuario usuario = new Usuario(nome,email,senha,telefone,status);
		UsuarioDao usuarioDao = new UsuarioDao();
		int id = 0;
		
		if (usuarioDao.validar(usuario) == 0) {
			id = usuarioDao.insert(usuario);
			if (id > 0) {
				mensagem = "Usuário cadastrado com sucesso!";
			} else {
				mensagem = "Ocorreu um erro no cadastro do Usuário!";
			}

			System.out.println(mensagem);
		}
	}

	//@Test
	public void testCriarUsrError() {
		String nome, email, senha, telefone, mensagem;
		int status;
		
		nome = null;
		email = null;
		senha = null;
		telefone = null;
		status = -1;
		
		Usuario usuario = new Usuario(nome,email,senha,telefone,status);
		UsuarioDao usuarioDao = new UsuarioDao();
		int id = 0;
		
		if (usuarioDao.validar(usuario) == 0) {
			id = usuarioDao.insert(usuario);
			if (id > 0) {
				mensagem = "Usuário cadastrado com sucesso!";
			} else {
				mensagem = "Ocorreu um erro no cadastro do Usuário!";
			}

			System.out.println(mensagem);
		}
	}
	
	@SuppressWarnings("unused")
	//@Test
	public void testLogonUsrOK() {
		int retorno = 0;
		String email, senha, mensagem;

		email = "teste@teste.com.br";
		senha = "123";
		
		Usuario usuario = new Usuario();
		
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		UsuarioDao usuarioDao = new UsuarioDao();
		
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
	}

	@SuppressWarnings("unused")
//	@Test
	public void testLogonUsrError() {
		int retorno = 0;
		String email, senha, mensagem;

		email = null;
		senha = null;
		
		Usuario usuario = new Usuario();
		
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		UsuarioDao usuarioDao = new UsuarioDao();
		
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
	}
	
	@SuppressWarnings("unused")
	//@Test
	public void testLogoffUsrOK() {
		int retorno = 0, idUsuario = 1;
		String mensagem = "";
		Usuario usuario = new Usuario();
		
		usuario.setId(idUsuario);
		usuario.setStatus(0);
		
		UsuarioDao usuarioDao = new UsuarioDao();
		
		retorno = usuarioDao.update(usuario);
		if (retorno > 0) {
			mensagem = "Usuário deslogado com sucesso!";
		} else {
			mensagem = "Ocorreu um erro no logoff do Usuário!";
		}
	}

	@SuppressWarnings("unused")
	//@Test
	public void testLogoffUsrOError() {
		int retorno = 0, idUsuario = 0;
		String mensagem = "";
		Usuario usuario = new Usuario();
		
		usuario.setId(idUsuario);
		usuario.setStatus(0);
		
		UsuarioDao usuarioDao = new UsuarioDao();
		
		retorno = usuarioDao.update(usuario);
		if (retorno > 0) {
			mensagem = "Usuário deslogado com sucesso!";
		} else {
			mensagem = "Ocorreu um erro no logoff do Usuário!";
		}
	}
	
	//@Test
	public void testPesqFilmeTituloOK() {
		StringBuilder strPattern = new StringBuilder();
		StringBuilder strDados   = new StringBuilder();

		String titulo = "Marvel", mensagem = "", pattern = "";
		
		List<Filme> listaFilmes  = new ArrayList<Filme>();
	
		FilmeDao filmeDao = new FilmeDao();
		Filme filme = new Filme();
		
		filme.setTitulo(titulo);
		
		listaFilmes = filmeDao.search(filme);
			
		if (listaFilmes.isEmpty()) {
			mensagem = "Nenhum filme com titulo " + titulo + " foi encontrado.";
			pattern = "{ \"mensagem\":\"%s\"\"}";
		    System.out.println(String.format(pattern, mensagem));
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

			if (strDados.substring(strDados.toString().trim().length()-1).contentEquals(",")) {
				strDados.deleteCharAt(strDados.toString().trim().length()-1);
			}
				
			strPattern.append(strDados.toString());
				
			strPattern.append(" } ");
		}
		
		System.out.println(strPattern.toString());
	}

	//@Test
	public void testPesqFilmeTituloError() {
		StringBuilder strPattern = new StringBuilder();
		StringBuilder strDados   = new StringBuilder();

		String titulo = null, mensagem = "", pattern = "";
		
		List<Filme> listaFilmes  = new ArrayList<Filme>();
	
		FilmeDao filmeDao = new FilmeDao();
		Filme filme = new Filme();
		
		filme.setTitulo(titulo);
		
		listaFilmes = filmeDao.search(filme);
			
		if (listaFilmes.isEmpty()) {
			mensagem = "Nenhum filme com titulo " + titulo + " foi encontrado.";
			pattern = "{ \"mensagem\":\"%s\" }";
		    System.out.println(String.format(pattern, mensagem));
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

			if (strDados.substring(strDados.toString().trim().length()-1).contentEquals(",")) {
				strDados.deleteCharAt(strDados.toString().trim().length()-1);
			}
				
			strPattern.append(strDados.toString());
				
			strPattern.append(" } ");
		}
		
		System.out.println(strPattern.toString());
	}
	
	//@Test
	public void testListFilmeOK() {
		StringBuilder strPattern = new StringBuilder();
		StringBuilder strDados   = new StringBuilder();

		String mensagem = "", pattern = "";
		
		List<Filme> listaFilmes  = new ArrayList<Filme>();
	
		FilmeDao filmeDao = new FilmeDao();
		listaFilmes = filmeDao.listFilme();
			
		if (listaFilmes.isEmpty()) {
			mensagem = "Nenhum filme disponível no momento.";
			pattern = "{ \"mensagem\":\"%s\" }";
		    System.out.println(String.format(pattern, mensagem));
		} else {
			strPattern.append("{ ");
			for(int i = 0; i < listaFilmes.size(); i++) {
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
		
		System.out.println(strPattern.toString());
	}

	//@Test
	public void testLocaFilme() {
		String pattern = "yyyy-MM-dd", 
			   mensagem = "", 
			   dataRetirada = "2019-03-01", 
			   dataEntrega = "2019-03-08";
		
		int idFilme = 1, idUsuario = 1;

		SimpleDateFormat sdfData = new SimpleDateFormat(pattern);

		Estoque estoque = new Estoque();
		EstoqueDao estoqueDao = new EstoqueDao();

		Locacao locacao = new Locacao();
		LocacaoDao locacaoDao = new LocacaoDao();
		
		try {
			locacao.setIdFilme(idFilme);
			locacao.setIdUsuario(idUsuario);
			locacao.setDataRetirada(sdfData.parse(dataRetirada));
			locacao.setDataEntrega(sdfData.parse(dataEntrega));
			
			estoque.setIdFilme(idFilme);
		} catch (ParseException e) {
			pattern = "{ \"error\":\"%s\", \"mensagem\":\"%s\"\"}";
			mensagem = "Ocorreu um erro no formato de alguma das datas de Retirada ou Entrega. Verifique.";
		    System.out.println(String.format(pattern, e.getMessage(), mensagem));
		}
		
		int id = 0;
		
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
			System.out.println(String.format(pattern, mensagem));
		}
	    
		pattern = "{ \"id\":\"%s\", \"mensagem\":\"%s\"\"}";
		System.out.println(String.format(pattern, id, mensagem));
	}

	@Test
	public void testDevoFilme() {
		Devolucao devolucao = new Devolucao();
		DevolucaoDao devolucaoDao = new DevolucaoDao();

		String pattern = "", 
			   mensagem = "",
			   dataEntrega = "2019-03-08";
		
		int idFilme = 1;
		
		try {
			pattern = "yyyy-MM-dd";
			
			SimpleDateFormat sdfData = new SimpleDateFormat(pattern);

			devolucao.setIdFilme(idFilme);
			devolucao.setDataEntrega(sdfData.parse(dataEntrega));
		} catch (ParseException e) {
			pattern = "{ \"error\":\"%s\", \"mensagem\":\"%s\"\"}";
			mensagem = "Ocorreu um erro no formato da data de Entrega. Verifique.";
		    System.out.println(String.format(pattern, e.getMessage(), mensagem));
		}
		
		int id = 0;
		
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
		    System.out.println(String.format(pattern, mensagem));
		}
	    
		pattern = "{ \"id\":\"%s\", \"mensagem\":\"%s\"\"}";
		System.out.println(String.format(pattern, id, mensagem));
	}
	
}
