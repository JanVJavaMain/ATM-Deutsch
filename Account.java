import java.util.ArrayList;

public class Account {
    private String name;
    private String uuid;
    private Nutzer besitzer;
    private ArrayList<Transaktion> transaktionen;

    public Account(String name, Nutzer besitzer, Bank dieBank) {
        this.name = name;
        this.besitzer = besitzer;
        this.uuid = dieBank.getNeueAccountUUID();
        this.transaktionen = new ArrayList<Transaktion>();
    }

    public String getUUID() {
        return this.uuid;
    } 

    public String getZusammenfassungsLinie() {
        double bilanz = this.getBilanz();
        if (bilanz >= 0) {
            return String.format("%s: $%.02f : %s", this.uuid, bilanz, this.name); // zwei Nachkommastellen
        } else {
            return String.format("%s: $(%.02f) : %s", this.uuid, bilanz, this.name);
        }
    }

    public double getBilanz() {
        double bilanz = 0;
        for (Transaktion t : this.transaktionen) {
            bilanz += t.getMenge();
        }
        return bilanz;
    }

    public void druckeTransaktionenVerlauf() {
        System.out.printf("\nTransaktionsverlauf fuer den Account %s\n", this.uuid);
        for (int t = this.transaktionen.size()-1; t >= 0; t--) {
            System.out.println(this.transaktionen.get(t).getZusammenfassendeLinie());
        }
        System.out.println();
    }

    public void transaktionAusfuehren(double menge, String memo) {
        Transaktion neueTrans = new Transaktion(menge, memo, this);
        this.transaktionen.add(neueTrans);
    }
}
