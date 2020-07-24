package example.jdbc.entity;

public class Article {

	private Integer id;
	private String	ref;
	private String designation;
	private Double prix;
	private Integer idfou;
	
	public Article(Integer id, String ref, String designation, Double prix, Integer idfou) {
		super();
		this.id = id;
		this.ref = ref;
		this.designation = designation;
		this.prix = prix;
		this.idfou = idfou;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Double getPrix() {
		return prix;
	}

	public void setPrix(Double prix) {
		this.prix = prix;
	}

	public Integer getIdfou() {
		return idfou;
	}

	public void setIdfou(Integer idfou) {
		this.idfou = idfou;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", ref=" + ref + ", designation=" + designation + ", prix=" + prix + ", idfou="
				+ idfou + "]";
	}
	

}
