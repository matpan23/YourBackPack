package Model.Bean;

public class Ordine { //Testata dell'ordine, informazioni dell'utente e dell'acquisto
    private int id;
    private String email;
    private String dataOrdine;
    private int numZaino;
    private float totale;

    public Ordine(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(String dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public int getNumZaino() {
        return numZaino;
    }

    public void setNumZaino(int numZaino) {
        this.numZaino = numZaino;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
