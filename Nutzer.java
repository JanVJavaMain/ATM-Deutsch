import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Nutzer {
    private String vorname, nachname;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;

    public Nutzer(String vorname, String nachname, String pin, Bank dieBank) {
        this.vorname = vorname;
        this.nachname = nachname;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        this.uuid = dieBank.getNeueNutzerUUID();

        this.accounts = new ArrayList<Account>();

        System.out.printf("Neuer Nutzer %s, %s mit der ID %s erstellt.\n", nachname, vorname, this.uuid);
    }

    public void accountHinzufuegen(Account einAcc) {
        this.accounts.add(einAcc); 
    }

    public String getUUID() {
        return this.uuid;
    } 

    public boolean pinValidieren(String einPin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(einPin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }  
    
    public String getVorname() {
        return this.vorname;
    }

    public void accountzusammenfassungAusgeben() {
        System.out.printf("\n\n%s's Accountzusammenfassung(en)", this.vorname);
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("%d:%s\n", i+1, this.accounts.get(i).getZusammenfassungsLinie());
        }
        System.out.println();
    }

    public int numAccounts() {
    return this.accounts.size();
    }

    public void gibDieAccountTransaktionen(int accInd) {
        this.accounts.get(accInd).druckeTransaktionenVerlauf();
    }

    public double getAccBilanz(int accInd) {
        return this.accounts.get(accInd).getBilanz();
    }

    public String getAccUUID(int accInd) {
        return this.accounts.get(accInd).getUUID();
    }

    public void fuehreTransaktionAus(int accInd, double menge, String memo) {
        this.accounts.get(accInd).transaktionAusfuehren(menge, memo);
    }
}

