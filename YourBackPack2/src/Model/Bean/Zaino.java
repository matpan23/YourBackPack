package Model.Bean;

public class Zaino {

    private String codice;
    private String titolo;
    private int prezzo;
    private int quantitaDisp;

    private String descrizione;

    public Zaino() {

    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantitaDisp() {
        return quantitaDisp;
    }

    public void setQuantitaDisp(int quantitaDisp) {
        this.quantitaDisp = quantitaDisp;
    }

    @Override
    public String toString() {
        return "Zaino{" +
                "codice='" + codice + '\'' +
                ", titolo='" + titolo + '\'' +
                ", prezzo=" + prezzo +
                ", quantitaDisp=" + quantitaDisp +
                ", descrizione='" + descrizione + '\'' +
                '}';
    }
}