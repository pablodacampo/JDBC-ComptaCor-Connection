package example.jdbc.entity;

/**
 * @author antoinethebault
 *Fournisseur : represente un fournisseur avec son id unique et son nom
 */
public class Fournisseur {
	/**id : int*/
	private Integer id;

	/**nom : String*/
	private String nom;


	/**Constructor
	 * @param id
	 * @param nom
	 */
	public Fournisseur(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**Getter
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}


	/**Setter
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
	public String toString() {
		return this.id+" "+this.nom;
	}
}
