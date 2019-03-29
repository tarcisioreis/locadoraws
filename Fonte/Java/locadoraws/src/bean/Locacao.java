/**
 *
 *		Classe DTO locacao representando a tabela fisica no banco de dados
 *
 */
package bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public class Locacao implements Serializable {

	private static final long serialVersionUID = 6391143371884676592L;

	private int id;
	private int idUsuario;
	private int idFilme;
	private Date dataRetirada;
	private Date dataEntrega;

	public Locacao() { }
	
	public Locacao(int id, int idUsuario, int idFilme, Date dataRetirada, Date dataEntrega) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idFilme = idFilme;
		this.dataRetirada = dataRetirada;
		this.dataEntrega = dataEntrega;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public int getIdFilme() {
		return idFilme;
	}
	public void setIdFilme(int idFilme) {
		this.idFilme = idFilme;
	}
	
	public Date getDataRetirada() {
		return dataRetirada;
	}
	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}
	
	public Date getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
}
