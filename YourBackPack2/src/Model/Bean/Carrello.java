package Model.Bean;

public class Carrello {
    private String utente;
    private int nZaini = 0;
    private float totale = 0;

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public int getnZaini() {
        return nZaini;
    }

    public void setnZaini(int nZaini) {
        this.nZaini = nZaini;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    @Override
    public String toString() {
        return "Carrello{" +
                "utente='" + utente + '\'' +
                ", nLibri=" + nZaini +
                ", totale=" + totale +
                '}';
    }
}