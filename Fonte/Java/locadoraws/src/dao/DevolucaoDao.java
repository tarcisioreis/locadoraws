/**
 *
 *		Classe DAO para implementação de procedimentos insert, update, delete, search 
 *      entre outros na tabela Devolucao
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

import bean.Devolucao;
import bean.Estoque;
import connection.ConnectionBase;
import idao.iDao;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class DevolucaoDao implements iDao<Devolucao>{

	private ConnectionBase connBase;
	private java.sql.Date sqlDate;
	private Estoque estoque;
	private EstoqueDao estoqueDao;

	public DevolucaoDao() {
		setConnBase(new ConnectionBase());
	}
	
	@Override
	public int validar(Devolucao bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Devolucao bean) {
		int retorno = 0;
		
		try {
			String[] strId = { "ID" };
			String sqlIns  = "INSERT INTO devolucao(idFilme, dataEntrega) VALUES (?, ?)";
			PreparedStatement psIns = getConnBase().getConnection().prepareStatement(sqlIns, strId);
		
			psIns.setInt(1, bean.getIdFilme());

			sqlDate = new Date(bean.getDataEntrega().getTime());
			psIns.setDate(2, sqlDate);
			
			retorno = psIns.executeUpdate();
			
			if (retorno > 0) {
				ResultSet generatedKeys = psIns.getGeneratedKeys();
				
				if (generatedKeys.next()) {
		            retorno = generatedKeys.getInt(1);

		            estoque = new Estoque();
		            
		            estoque.setIdFilme(bean.getIdFilme());
		            estoque.setQuantidade(1);
		           
		            estoqueDao = new EstoqueDao();
		            
		            if (estoqueDao.update(estoque) <= 0) {
			            throw new SQLException("Não foi possível atualizar estoque do filme");
		            }
				} else {
		            throw new SQLException("Não foi possível gerar ID da devolução");
		        }
			} else {
	            throw new SQLException("Não foi possível cadastrar devolução");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = 0;
		}
		
		return retorno;
	}

	@Override
	public int update(Devolucao bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Devolucao bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Devolucao> search(Devolucao bean) {
		List<Devolucao> lista = new ArrayList<Devolucao>();
		
		try {
			StringBuilder sql = new StringBuilder();
					
			sql.append("SELECT id, idFilme, dataEntrega ");
			sql.append("FROM devolucao ");
			sql.append("WHERE idFilme = ? AND ");
			sql.append("dataEntrega  >= ?");
			
			PreparedStatement psSearch = getConnBase().getConnection().prepareStatement(sql.toString());
			
			psSearch.setInt(1,  bean.getIdFilme());

			sqlDate = new Date(bean.getDataEntrega().getTime());
			psSearch.setDate(2, sqlDate);
			
			ResultSet rs = psSearch.executeQuery();
			while(rs.next()) {
				lista.add(new Devolucao(rs.getInt("id"), 
                                        rs.getInt("idFilme"), 
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
