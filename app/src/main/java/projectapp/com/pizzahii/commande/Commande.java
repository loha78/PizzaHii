package projectapp.com.pizzahii.commande;

import java.util.List;

/**
 * Created by NAJI on 08/11/2015.
 */
public class Commande {

    public int numero;
    public List<String> listePlatsCommandes;

    public Commande() {
    }

    public Commande(int numero) {
        this.numero = numero;
    }

    public Commande(int numero, List<String> listePlatsCommandes) {
        this.numero = numero;
        this.listePlatsCommandes = listePlatsCommandes;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setListePlatsCommandes(List<String> listePlatsCommandes) {
        this.listePlatsCommandes = listePlatsCommandes;
    }

    public int getNumero() {

        return numero;
    }

    public List<String> getListePlatsCommandes() {
        return listePlatsCommandes;
    }
}
