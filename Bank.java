import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<Nutzer> nutzer;
    private ArrayList<Account> accounts;

    public Bank(String name){
        this.name = name;
        this.nutzer = new ArrayList<Nutzer>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNeueNutzerUUID() {
        String uuid;
        Random rng = new Random();
        int laenge = 6;
        boolean nichtEinzigartig;

        do {
            uuid = "";
            for (int i = 0; i < laenge; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString(i);
            }

            nichtEinzigartig = false;
            for (Nutzer u : this.nutzer) {
                if (uuid.compareTo(u.getUUID()) == 0) {
                    nichtEinzigartig = true;
                    break;
                }
            }
        } while (nichtEinzigartig = true);

        return uuid;
    }

    public String getNeueAccountUUID() {
        String uuid;
        Random rng = new Random();
        int laenge = 10;
        boolean nichtEinzigartig;

        do {
            uuid = "";
            for (int i = 0; i < laenge; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString(i);
            }

            nichtEinzigartig = false;
            for (Account a: this.accounts) {
                if (uuid.compareTo(a.getUUID()) == 0) {
                    nichtEinzigartig = true;
                    break;
                }
            }
        } while (nichtEinzigartig = true);

        return uuid;
    }

    public void accountHinzufuegen(Account einAcc) {
        this.accounts.add(einAcc);
    }

    public Nutzer nutzerHinzufuegen(String vorname, String nachname, String pin) {
        Nutzer neuerNutzer = new Nutzer(vorname, nachname, pin, this);
        this.nutzer.add(neuerNutzer);

        Account neuerAccount = new Account("Ersparnisse", neuerNutzer, this);
        neuerNutzer.accountHinzufuegen(neuerAccount);
        this.accountHinzufuegen(neuerAccount);

        return neuerNutzer;
    }

    public Nutzer nutzerLogin(String nutzerID, String pin) {
        for (Nutzer n : this.nutzer) {
            if (n.getUUID().compareTo(nutzerID) == 0 && n.pinValidieren(pin)) {
                return n;
            } 
        }
        return null;
    }

    public String getName() {
        return this.name;
    }
}