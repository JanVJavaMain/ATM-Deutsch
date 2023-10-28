import java.util.Date;

public class Transaktion {
    private double menge;
    private Date zeitpunkt;
    private String memo; //z.B. bei der Kreditkarte, etc
    private Account imAccount;

    public Transaktion(double menge, Account imAccount) {
        this.menge = menge;
        this.imAccount = imAccount;
        this.zeitpunkt = new Date();
        this.memo = "";
    }

    public Transaktion(double menge, String memo, Account imAccount) {
        this(menge, imAccount);
        this.memo = memo;
    }

    public double getMenge() {
        return this.menge;
    }

    public String getZusammenfassendeLinie() {
        if (this.menge >= 0) {
            return String.format("%s : $%.02f : %s", this.zeitpunkt.toString(), this.menge, this.memo);
        } else {
            return String.format("%s : $(%.02f) : %s", this.zeitpunkt.toString(), -this.menge, this.memo);
        }
    }
}
