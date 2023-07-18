package Model.Bean;

import java.util.Date;

public class OrdineC { //Ordine effettivo (entry dell'ordine)
    private int id;
    private String ordine;
    private String Zaino;
    private int numZaini;
    private int prezzo;
    public OrdineC(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrdine() {
        return ordine;
    }

    public void setOrdine(String ordine) {
        this.ordine = ordine;
    }

    public String getZaino() {
        return Zaino;
    }

    public void setZaino(String Zaino) {
        this.Zaino = Zaino;
    }

    public int getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	public int getNumZaini() {
        return numZaini;
    }
    public void setNumZaini(int numZaini) {this.numZaini = numZaini;}
}
