/**
 *
 *		Classe DAO para implementação de procedimentos insert, update, delete, search 
 *      entre outros na tabela Filme
 *
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bean.Filme;
import connection.ConnectionBase;
import idao.iDao;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class FilmeDao implements iDao<Filme>{

	private ConnectionBase connBase;

	public FilmeDao() { 
		setConnBase(new ConnectionBase());
	}

	@Override
	public int validar(Filme bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Filme bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Filme bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Filme bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Filme> search(Filme bean) {
		List<Filme> lista = new ArrayList<Filme>();
		
		try {
			if (bean.getTitulo() == null) {
				throw new SQLException("Informar titulo para busca. Verfique.");
			}
			
			StringBuilder sql = new StringBuilder();
					
			sql.append("SELECT f.id, f.titulo, f.diretor, COALESCE(l.idFilme, 0) locado ");
			sql.append("FROM filme f ");
			sql.append("LEFT JOIN locacao l ON l.idFilme = f.id ");
			sql.append("WHERE LOWER(f.titulo) LIKE ?");
			
			PreparedStatement psSearch = getConnBase().getConnection().prepareStatement(sql.toString());
			
			psSearch.setString(1, "%" + bean.getTitulo().trim().toLowerCase() + "%");
			
			ResultSet rs = psSearch.executeQuery();
			while(rs.next()) {
				Filme filme = new Filme();
				
				filme.setId(rs.getInt("id"));
				filme.setTitulo(rs.getString("titulo"));
				filme.setDiretor(rs.getString("diretor"));
				filme.setLocado(rs.getInt("locado"));
				
				lista.add(filme);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			lista = Collections.emptyList();
		}
		
		return lista;
	}

	public List<Filme> listFilme() {
		List<Filme> lista = new ArrayList<Filme>();
		
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT f.id, f.titulo, f.diretor ");
			sql.append("FROM filme f ");
			sql.append("WHERE f.id not in (SELECT l.idFilme ");
			sql.append("                   FROM locacao l ");
			sql.append("                   WHERE l.idFilme = f.id)");
			
			PreparedStatement psList = getConnBase().getConnection().prepareStatement(sql.toString());
			
			ResultSet rs = psList.executeQuery();
			while(rs.next()) {
				Filme filme = new Filme();
				
				filme.setId(rs.getInt("id"));
				filme.setTitulo(rs.getString("titulo"));
				filme.setDiretor(rs.getString("diretor"));
				filme.setLocado(0);
				
				lista.add(filme);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			lista = Collections.emptyList();
		}
		
		return lista;
	}
	
	private ConnectionBase getConnBase() {
		return connBase;
	}
	private void setConnBase(ConnectionBase connBase) {
		this.connBase = connBase;
	}

}
