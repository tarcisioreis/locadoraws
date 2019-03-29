/**
 *
 *		Classe DTO usuario representando a tabela fisica no banco de dados
 *
 */
package bean;

import java.io.Serializable;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class Usuario implements Serializable {

	private static final long serialVersionUID = -1706271158091370919L;

	private int id;
	private String nome;
	private String email;
	private String senha;
	private String telefone;
	private int status;
	
	public Usuario() { }
	
	public Usuario(String nome,
			       String email,
			       String senha,
			       String telefone, 
			       int status) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.telefone = telefone;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
