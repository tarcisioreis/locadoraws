/**
 *
 *		Classe DAO para implementação de procedimentos insert, update, delete, search 
 *      entre outros na tabela Estoque
 *
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bean.Estoque;
import connection.ConnectionBase;
import idao.iDao;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class EstoqueDao implements iDao<Estoque>{

	private ConnectionBase connBase;

	public EstoqueDao() {
		setConnBase(new ConnectionBase());
	}
	
	@Override
	public int validar(Estoque bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Estoque bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Estoque bean) {
		int retorno = 0;
		int valor   = 0;
		
		try {
			int indexID = 0, indexIdFilme = 0;
			
			StringBuilder sqlUpdate = new StringBuilder();
					
			sqlUpdate.append("UPDATE estoque SET ");

			valor = bean.getQuantidade();
			
			if (valor >= 0) {
				sqlUpdate.append("quantidade = quantidade + ? ");
			} else {
				sqlUpdate.append("quantidade = quantidade - ? ");
			}
			
			if (bean.getIdFilme() > 0) {
				indexIdFilme = 2;
			}
			
			if (bean.getId() > 0) {
				indexID = 2;
				
				if (bean.getIdFilme() > 0) {
					indexID++;
				}
			}

			sqlUpdate.append("WHERE ");
			
			if (indexIdFilme > 0 && indexID == 0) {
				sqlUpdate.append("idFilme = ?");
			} else if (indexIdFilme > 0 && indexID > 0) {
				sqlUpdate.append("idFilme = ? AND id = ?");
			} else if (indexID > 0){
				sqlUpdate.append("id = ?");
			} else {
	            throw new SQLException("Não foi possível atualizar estoque do filme");
			}
			
			PreparedStatement psUpd = getConnBase().getConnection().prepareStatement(sqlUpdate.toString());

			psUpd.setInt(1, Math.abs(bean.getQuantidade()));

			if (indexIdFilme > 0) {
				psUpd.setInt(indexIdFilme, bean.getIdFilme());
			} else if (indexID > 0) {
				psUpd.setInt(indexID, bean.getId());
			}
			
			retorno = psUpd.executeUpdate();
			
			if (retorno <= 0) {
	            throw new SQLException("Não foi possível atualizar estoque do filme");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = 0;
		}
		
		return retorno;
	}

	@Override
	public int delete(Estoque bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Estoque> search(Estoque bean) {
		List<Estoque> lista = new ArrayList<Estoque>();
		
		
		try {
			int indexID = 0, 
				indexIdFilme = 0;
			
			StringBuilder sql = new StringBuilder();
			
			sql.append("SELECT id, idFilme, quantidade ");
			sql.append("FROM estoque ");
			sql.append("WHERE ");
			
			if (bean.getId() > 0 && bean.getIdFilme() == 0) {
				sql.append("id = ?");
				indexID = 1;
			} else if (bean.getIdFilme() > 0 && bean.getId() == 0) {
				sql.append("idFilme = ?");
				indexIdFilme = 1;
			} else if (indexID > 0 && indexIdFilme > 0) {
				sql.append("id = ? AND idFilme = ?");
				indexID      = 1;
				indexIdFilme = 2;
			}
			
			PreparedStatement psSearch = getConnBase().getConnection().prepareStatement(sql.toString());
			
			if (indexID > 0) {
				psSearch.setInt(indexID, bean.getId());
			}
			
			if (indexIdFilme > 0) {
				psSearch.setInt(indexIdFilme, bean.getIdFilme());
			}	

			ResultSet rs = psSearch.executeQuery();
			while(rs.next()) {
				lista.add(new Estoque(rs.getInt("id"), 
	                                  rs.getInt("idFilme"), 
	                                  rs.getInt("quantidade")));
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
