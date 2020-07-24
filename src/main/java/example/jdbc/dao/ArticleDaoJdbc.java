package example.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import example.jdbc.entity.Article;
import example.jdbc.entity.Fournisseur;

public class ArticleDaoJdbc implements ArticleDao {
	
	public static void main(String[] args) {
		
		ArticleDaoJdbc art = new ArticleDaoJdbc();
		List<Article> listeArt = art.extraire();
		for(Article ar : listeArt) {
			System.out.println(ar);
		}
		
		art.insert(new Article(12, "M01", "10lt Milk", 9.99, 5 ));
		listeArt = art.extraire();
		for (Article ar : listeArt) {
			System.out.println(ar);
		}
		
		art.update("1lt Milk", "2lts Milk");
		listeArt = art.extraire();
		for (Article ar : listeArt) {
			System.out.println(ar);
		}
		
		if (art.delete(new Article(11, "M01", "2lts Milk", 1.75, 5)))
			System.out.println("Article Deleted !");
		listeArt = art.extraire();
		for (Article ar : listeArt) {
			System.out.println(ar);
		}
	}
	
	public List<Article> extraire() {
		
		Connection connection = null;
		List<Article> listeArt = new ArrayList<Article>();
		try {
			connection = getConnection();
			/**
			 * Recuperate a statement = access to data in the connection object
			 * Recuperate the result of the request
			 * Add line by line in the list of Articles
			 */
			Statement monCanal = connection.createStatement();
			ResultSet monResult = monCanal.executeQuery("SELECT * FROM article WHERE ref LIKE 'M%';");
			
			while(monResult.next()) {
				listeArt.add(new Article(monResult.getInt("id"),
										monResult.getString("ref"), 
										monResult.getString("designation"), 
										monResult.getDouble("prix"), 
										monResult.getInt("idfou")));
			}
			monResult.close();
			monCanal.close();
			
		}
		catch(Exception e) {
			System.err.println("Erreur d'execution : " + e.getMessage());
		}
		finally {
			try {
				if(connection != null) connection.close();
			}
			catch(SQLException e)
			{
				System.err.println("Problem de connection close : " + e.getMessage());
			}
			
		}
		return listeArt;
	}

	/**fait un insert dans la base de compta sur la table Article*/
	public void insert(Article article) {
		
		Connection connection = null;
		try {
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			int nb = monCanal.executeUpdate("INSERT INTO article (id, ref, designation, prix, idfou) "+" VALUES ("
					+ article.getId() + ",'"
					+ article.getRef() + "','"
					+ article.getDesignation() + "',"
					+ article.getPrix() + ","
					+ article.getIdfou() +")");

			if (nb == 1) {
				System.out.println("Article Added !");
			}
			monCanal.close();
		} catch (Exception e) {
			System.err.println("Erreur d'execution : " + e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println("Problem de connection close : " + e.getMessage());
			}

		}
	}

	/**
	 * fait un update dans la table Article en changeant le nom ancienNom par nouveauNom
	 */
	public int update(String ancienDesignation, String nouveauDesignation) {

		Connection connection = null;
		int nb = 0;
		try {
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			nb = monCanal
					.executeUpdate("UPDATE article SET designation='" + nouveauDesignation + "' WHERE designation='" + ancienDesignation + "';");

			if (nb == 1) {
				System.out.println("Article Updated !");
			}
			monCanal.close();
		} catch (Exception e) {
			System.err.println("Erreur d'execution : " + e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println("Problem de connection close : " + e.getMessage());
			}

		}

		return nb;
	}
	

	/**
	 *supprime le Article specifie dans la table Article
	 */
	public boolean delete(Article article) {

		Connection connection = null;
		boolean nb = false;
		try {
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			if (monCanal.executeUpdate("DELETE FROM article WHERE id=" + article.getId() + ";") == 1)
				nb = true;

			monCanal.close();
		} catch (Exception e) {
			System.err.println("Erreur d'execution : " + e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println("Problem de connection close : " + e.getMessage());
			}

		}

		return nb;

	}

	public Connection getConnection() {
		
		// recupere le fichier properties
		ResourceBundle db = ResourceBundle.getBundle("database");
		
		try {
			// enregistre le pilote
			Class.forName(db.getString("db.driver"));
			System.err.println("Article Class Loaded");
			return DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"), db.getString("db.pass"));
			
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException();
		}
	}
	
}
