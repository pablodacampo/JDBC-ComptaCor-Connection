package example.jdbc.dao;

import java.util.List;

import example.jdbc.entity.Fournisseur;

public interface FournisseurDao {
    List<Fournisseur> extraire();
    void insert(Fournisseur fournisseur);
    int update(String ancienNom, String nouveauNom);
    boolean delete(Fournisseur fournisseur);
}
