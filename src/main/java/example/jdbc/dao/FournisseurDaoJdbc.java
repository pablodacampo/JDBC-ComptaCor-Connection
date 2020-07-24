package example.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import example.jdbc.entity.Fournisseur;

public class FournisseurDaoJdbc implements FournisseurDao {

	public static void main(String[] args) {

		FournisseurDaoJdbc ofo = new FournisseurDaoJdbc();
		List<Fournisseur> listeFour = ofo.extraire();
		for (Fournisseur fo : listeFour) {
			System.out.println(fo);
		}

		ofo.insert(new Fournisseur(6, "The Riders"));
		listeFour = ofo.extraire();
		for (Fournisseur fo : listeFour) {
			System.out.println(fo);
		}

		ofo.update("The Farmers", "The Barmy Farmers");
		listeFour = ofo.extraire();
		for (Fournisseur fo : listeFour) {
			System.out.println(fo);
		}

		if (ofo.delete(new Fournisseur(6, "The Riders")))
			System.out.println("Fournisseur Deleted !");
		listeFour = ofo.extraire();
		for (Fournisseur fo : listeFour) {
			System.out.println(fo);
		}
	}

	public List<Fournisseur> extraire() {

		Connection connection = null;
		List<Fournisseur> listeFour = new ArrayList<Fournisseur>();
		try {
			connection = getConnection();
			/**
			 * Recuperate a statement = access to data in the connection object Recuperate
			 * the result of the request Add line by line in the list of Fournisseurs
			 */
			Statement monCanal = connection.createStatement();
			ResultSet monResult = monCanal.executeQuery("SELECT * FROM fournisseur;");

			while (monResult.next()) {
				listeFour.add(new Fournisseur(monResult.getInt("id"), monResult.getString("nom")));
			}
			monResult.close();
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
		return listeFour;
	}

	/** fait un insert dans la base de compta sur la table fournisseur */
	public void insert(Fournisseur fournisseur) {

		Connection connection = null;
		try {
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			int nb = monCanal.executeUpdate("INSERT INTO fournisseur (id, nom) VALUES "
					+ "(" + fournisseur.getId() + ",'"
					+ fournisseur.getNom() + "');");

			if (nb == 1) {
				System.out.println("Fournisseur Added !");
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
	 * fait un update dans la table fournisseur en changeant le nom ancienNom par
	 * nouveauNom
	 */
	public int update(String ancienNom, String nouveauNom) {

		Connection connection = null;
		int nb = 0;
		try {
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			nb = monCanal
					.executeUpdate("UPDATE fournisseur SET nom='" + nouveauNom + "' WHERE nom='" + ancienNom + "';");

			if (nb == 1) {
				System.out.println("Fournisseur Updated !");
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
	 * supprime le fournisseur specifie dans la table fournisseur
	 */
	public boolean delete(Fournisseur fournisseur) {

		Connection connection = null;
		boolean nb = false;
		try {
			connection = getConnection();
			Statement monCanal = connection.createStatement();
			if (monCanal.executeUpdate("DELETE FROM fournisseur WHERE id=" + fournisseur.getId() + ";") == 1)
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
			System.err.println("Fournisseur Class Loaded");
			return DriverManager.getConnection(db.getString("db.url"), db.getString("db.user"),
					db.getString("db.pass"));

		} catch (ClassNotFoundException | SQLException e) {
			System.err.println(e.getMessage());
			throw new RuntimeException();
		}
	}

}
