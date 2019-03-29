/**
 *
 *		Classe Interface Generica para implementação em classes DAO
 *
 */
package idao;

import java.util.List;

/**
 * @author Tarcisio Machado dos Reis
 *
 */
public interface iDao<T> {
	public int validar(T bean);
	public int insert(T bean);
	public int update(T bean);
	public int delete(T bean);
	public List<T> search(T bean);
}
