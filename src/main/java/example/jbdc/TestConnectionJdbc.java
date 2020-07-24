package example.jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TestConnectionJdbc {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // recupere le fichier properties
        ResourceBundle db = ResourceBundle.getBundle("database");
        Connection connection = null;
        try {

            // enregistre le pilote
            Class.forName(db.getString("db.driver"));
                        
            
            // creer la connection
            
            	connection = DriverManager.getConnection(
            		db.getString("db.url"),
            		db.getString("db.user"),
                    db.getString("db.pass"));

                // affiche la connexion
                boolean valid = connection.isValid(500);
                if (valid) {
                    System.out.println("La connection est ok");
                } else {
                    System.out.println("Il y a deconnection  !");
                }
        } 
            catch (SQLException | ClassNotFoundException e) {
            // Handle errors for JDBC
            System.err.println("Base deconnectee : " + e.getMessage());
        }
        finally {
        	try {
        		if(connection != null) { connection.close();
        	}            		
        		
        	} catch (Exception e) {
        		System.err.println("Erreur : " + e.getMessage());
        	}
        	System.out.println("Base deconnectee finally");
        }
        
    }
}
