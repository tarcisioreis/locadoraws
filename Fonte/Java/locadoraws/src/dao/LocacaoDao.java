/**
 *
 *		Classe DAO para implementação de procedimentos insert, update, delete, search 
 *      entre outros na tabela Locacao
 *
 */
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bean.Estoque;
import bean.Locacao;
import connection.ConnectionBase;
import idao.iDao;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class LocacaoDao implements iDao<Locacao>{

	private ConnectionBase connBase;
	private java.sql.Date sqlDate;
	private Estoque estoque;
	private EstoqueDao estoqueDao;

	public LocacaoDao() {
		setConnBase(new ConnectionBase());
	}
	
	@Override
	public int validar(Locacao bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Locacao bean) {
		int retorno = 0;
	
		try {
			String[] strId = { "ID" };
			String sqlIns  = "INSERT INTO locacao(idUsuario, idFilme, dataRetirada, dataEntrega) VALUES (?, ?, ?, ?)";
			PreparedStatement psIns = getConnBase().getConnection().prepareStatement(sqlIns, strId);
		
			psIns.setInt(1, bean.getIdUsuario());
			psIns.setInt(2, bean.getIdFilme());

			sqlDate = new Date(bean.getDataRetirada().getTime());
			psIns.setDate(3, sqlDate);

			sqlDate = new Date(bean.getDataEntrega().getTime());
			psIns.setDate(4, sqlDate);
	
			retorno = psIns.executeUpdate();
			
			if (retorno > 0) {
				ResultSet generatedKeys = psIns.getGeneratedKeys();
				
				if (generatedKeys.next()) {
		            retorno = generatedKeys.getInt(1);
		            
		            estoque = new Estoque();
		            
		            estoque.setIdFilme(bean.getIdFilme());
		            estoque.setQuantidade(-1);
		     
		            estoqueDao = new EstoqueDao();
		            
		            if (estoqueDao.update(estoque) <= 0) {
			            throw new SQLException("Não foi possível atualizar estoque do filme");
		            }
		            		
		        } else {
		            throw new SQLException("Não foi possível gerar ID da locação");
		        }
			} else {
	            throw new SQLException("Não foi possível cadastrar locação");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = 0;
		}
		
		return retorno;
	}

	@Override
	public int update(Locacao bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Locacao bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Locacao> search(Locacao bean) {
		List<Locacao> lista = new ArrayList<Locacao>();
		
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("SELECT id, idUsuario, idFilme, dataRetirada, dataEntrega ");
			sql.append("FROM locacao ");
			sql.append("WHERE idFilme = ? AND ");
			sql.append("dataRetirada >= ?");
			
			PreparedStatement psSearch = getConnBase().getConnection().prepareStatement(sql.toString());
			
			psSearch.setInt(1,  bean.getIdFilme());

			sqlDate = new Date(bean.getDataRetirada().getTime());
			psSearch.setDate(2, sqlDate);
			
			ResultSet rs = psSearch.executeQuery();
			while(rs.next()) {
				lista.add(new Locacao(rs.getInt("id"), 
	                                  rs.getInt("idUsuario"), 
	                                  rs.getInt("idFilme"), 
	                                  rs.getDate("dataRetirada"), 
	                                  rs.getDate("dataEntrega")));
			}
		} catch (Exception e) {
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
