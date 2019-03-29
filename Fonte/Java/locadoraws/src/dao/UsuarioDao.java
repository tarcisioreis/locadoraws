/**
 *
 *		Classe DAO para implementação de procedimentos insert, update, delete, search 
 *      entre outros na tabela Usuario
 *
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import bean.Usuario;
import connection.ConnectionBase;
import idao.iDao;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class UsuarioDao implements iDao<Usuario>{

	private ConnectionBase connBase;

	public UsuarioDao() { 
		setConnBase(new ConnectionBase());
	}

	@Override
	public int validar(Usuario bean) {
		int retorno = 0;

		try {
			if (bean.getNome().isEmpty()) {
				retorno = -1;
				return retorno;
			}

			if (bean.getEmail().isEmpty()) {
				retorno = -2;
				return retorno;
			} else if (this.searchByEmail(bean) > 0) {
				retorno = -5;
				return retorno;
			}

			if (bean.getSenha().isEmpty()) {
				retorno = -3;
				return retorno;
			}

			if (bean.getTelefone().isEmpty()) {
				retorno = -4;
				return retorno;
			}
		}catch(Exception e) {
			e.printStackTrace();
			retorno = -6;
		}
		
		return retorno;
	}
	
	public int validarLogin(Usuario bean) {
		int retorno = 0;
		
		try {
			if (bean.getEmail().isEmpty()) {
				retorno = -1;
				return retorno;
			} else if (this.searchByEmail(bean) == 0) {
				retorno = -3;
				return retorno;
			}

			if (bean.getSenha().isEmpty()) {
				retorno = -2;
				return retorno;
			}
		}catch(Exception e) {
			e.printStackTrace();
			retorno = -3;
		}
		
		return retorno;
	}
	
	@Override
	public int insert(Usuario bean) {
		int retorno = 0;
		
		try {
			String[] strId = { "ID" };
			String sqlIns  = "INSERT INTO usuario(nome, email, senha, telefone, status) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement psIns = getConnBase().getConnection().prepareStatement(sqlIns, strId);
		
			psIns.setString(1, bean.getNome());
			psIns.setString(2, bean.getEmail());
			psIns.setString(3, bean.getSenha());
			psIns.setString(4, bean.getTelefone());
			psIns.setInt(5,    bean.getStatus());
			
			retorno = psIns.executeUpdate();
			
			if (retorno > 0) {
				ResultSet generatedKeys = psIns.getGeneratedKeys();
				
				if (generatedKeys.next()) {
		            retorno = generatedKeys.getInt(1);
		        } else {
		            throw new SQLException("Não foi possível gerar ID do usuário");
		        }
			} else {
	            throw new SQLException("Não foi possível cadastrar usuário");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = 0;
		}
		
		return retorno;
	}

	@Override
	public int update(Usuario bean) {
		int retorno = 0;
		
		try {
			int indexNome = 0, 
				indexEmail = 0, 
				indexSenha = 0, 
				indexTelefone = 0, 
				indexStatus = 0, 
				indexID = 0;
			
			StringBuilder sqlUpdate = new StringBuilder();
					
			sqlUpdate.append("UPDATE usuario SET ");
			
			if (bean.getNome() != null) {
				if (!bean.getNome().isEmpty()) {
					sqlUpdate.append("nome = ?");
					indexNome = 1;
					indexID   = indexNome + 1;
				}
			}
			
			if (bean.getEmail() != null) {
				if (!bean.getEmail().isEmpty()) {
					if (indexNome > 0) {
						sqlUpdate.append(", ");
						indexEmail = 2;
					} else indexEmail = 1;

					indexID = indexEmail + 1;
				
					sqlUpdate.append("email = ?");
				}
			}
			
			if (bean.getSenha() != null) {
				if (!bean.getSenha().isEmpty()) {
					if (indexNome  > 0 || 
						indexEmail > 0) {
						sqlUpdate.append(", ");
						
						if (indexNome > 0) {
							indexSenha = 2;
						} else if (indexEmail > 0) {
							if (indexNome == 0) {
								indexSenha = 2;
							} else indexSenha = 3;
						} else indexSenha = 1;
					}	
					indexID = indexSenha + 1;
					
					sqlUpdate.append("senha = ?");
				}
			}

			if (bean.getTelefone() != null) {
				if (!bean.getTelefone().isEmpty()) {
					if (indexNome  > 0 || 
					    indexEmail > 0 ||
					    indexSenha > 0) {
						sqlUpdate.append(", ");
						
						if (indexNome > 0) {
							indexTelefone = 2;
						} else if (indexEmail > 0) {
							if (indexNome == 0) {
								indexTelefone = 2;
							} else indexTelefone = 3;
						} else if (indexSenha > 0) {
							if (indexNome == 0) {
								indexTelefone = 1;
							} else if (indexEmail == 0) {
								indexTelefone = 2;
							} else indexTelefone = 3;
						} else indexTelefone = 1;
					} else indexTelefone = 1;
					
					indexID = indexTelefone + 1;

					sqlUpdate.append("telefone = ?");
				}
			}

			if (bean.getStatus()  >= 0) {
				if (indexNome     > 0 || 
				    indexEmail    > 0 ||
				    indexSenha    > 0 ||
				    indexTelefone > 0) {
					sqlUpdate.append(", ");

					if (indexTelefone > 0) {
						indexStatus = indexTelefone + 1;
					} else if (indexSenha > 0) {
						indexStatus = indexSenha + 1;
					} else if (indexEmail > 0) {
						indexStatus = indexEmail + 1;
					} else if (indexNome > 0) {
						indexStatus = indexNome + 1;
					}
					
				} else indexStatus = 1;
				
				indexID = indexStatus + 1;

				sqlUpdate.append("status = ? ");
			}

			if (bean.getId() > 0) {
				sqlUpdate.append("WHERE id = ?");
			} else if (indexEmail > 0 && indexSenha > 0) {	// Procedimento de logonUsr
				sqlUpdate.append("WHERE email = ? AND senha = ?");
			}
			
			PreparedStatement psUpd = getConnBase().getConnection().prepareStatement(sqlUpdate.toString());
		
			if (indexNome > 0) {
				psUpd.setString(indexNome, bean.getNome());
			}
			
			if (indexEmail > 0) {
				psUpd.setString(indexEmail, bean.getEmail());
			}
			
			if (indexSenha > 0) {
				psUpd.setString(indexSenha, bean.getSenha());
			}
			
			if (indexTelefone > 0) {
				psUpd.setString(indexTelefone, bean.getTelefone());
			}
			
			if (indexStatus > 0) {
				psUpd.setInt(indexStatus, bean.getStatus());
			}
			
			if (bean.getId() > 0) {
				psUpd.setInt(indexID, bean.getId());
			} else if (indexEmail > 0 && indexSenha > 0) {	// Procedimento de logonUsr
				psUpd.setString(indexID,   bean.getEmail());
				psUpd.setString(indexID++, bean.getSenha());
			} 
			
			retorno = psUpd.executeUpdate();
			
			if (retorno > 0) {
		        retorno = bean.getId();
			} else {
	            throw new SQLException("Não foi possível atualizar dados do usuário");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = 0;
		}
		
		return retorno;
	}

	@Override
	public int delete(Usuario bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Usuario> search(Usuario bean) {
		// TODO Auto-generated method stub
		return null;
	}

	public int searchByEmail(Usuario bean) {
		int retorno = -1;

		try {
			String sql = "SELECT id FROM usuario WHERE email = ?";
			PreparedStatement psSearch = getConnBase().getConnection().prepareStatement(sql);
			
			psSearch.setString(1, bean.getEmail().trim().toLowerCase());
			
			ResultSet rs = psSearch.executeQuery();
			if (rs.next()) {
				retorno = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			retorno = -1;
		}
		
		return retorno;
	}
	
	private ConnectionBase getConnBase() {
		return connBase;
	}
	private void setConnBase(ConnectionBase connBase) {
		this.connBase = connBase;
	}

}
