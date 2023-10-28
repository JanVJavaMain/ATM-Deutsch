import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank dieBank = new Bank("Eine Bank. ;)");
        Nutzer einNutzer = dieBank.nutzerHinzufuegen("John", "Doe", "1234");
        Account neuerAccount = new Account("Checken", einNutzer, dieBank);
        einNutzer.accountHinzufuegen(neuerAccount);
        dieBank.accountHinzufuegen(neuerAccount);

        Nutzer curNutzer;
        while (true) {
            curNutzer = ATM.mainMenuPrompt(dieBank, sc);
            ATM.printUserMenu(curNutzer, sc);
        }
    }

    public static Nutzer mainMenuPrompt(Bank dieBank, Scanner sc) {
        String nutzerID;
        String pin;
        Nutzer authNutzer;

        do {
            System.out.printf("\n\nWillkommen zu %s\n\n", dieBank.getName());
            System.out.print("NutzerID eingeben: ");
            nutzerID = sc.nextLine();
            System.out.printf("Pin eingeben: ");
            pin = sc.nextLine();

            authNutzer = dieBank.nutzerLogin(nutzerID, pin);
            if (authNutzer == null) {
                System.out.println("Falsche NutzerID oder falsches Passwort.");
            }
        } while(authNutzer == null);

        return authNutzer;
    }

    public static void printUserMenu(Nutzer derNutzer, Scanner sc) {
        derNutzer.accountzusammenfassungAusgeben();

        int wahl;

        do {
            System.out.printf("Willkommen, %s. Was koennen wie fuer Sie tun?", derNutzer.getVorname());
            System.out.println(" 1: Zeige Transaktionen.");
            System.out.println(" 2: Geld abheben.");
            System.out.println(" 3: Geld aufs Konto ablegen.");
            System.out.println(" 4: Geld ueberweisen.");
            System.out.println(" 5: Programm beenden..");
            System.out.println();
            wahl = sc.nextInt();

            if (wahl < 1 || wahl > 5){
                System.out.println("Invalide Auswah. Bitte geben Sie eine Zahl zwischen 1 und 5 ein.");
            }
        } while (wahl < 1 || wahl > 5);
        
        switch (wahl) {
            case 1: 
                ATM.zeigeTransVerlauf(derNutzer, sc);
                break;
            case 2:
                ATM.zahleGeldAus(derNutzer, sc);
                break;
            case 3:
                ATM.zahleGeldEin(derNutzer, sc);
                break;
            case 4:
                ATM.transferiereGeld(derNutzer, sc);
                break; 
            case 5:
                sc.nextLine();
                break;
        }

        if (wahl != 5) {
            ATM.printUserMenu(derNutzer, sc);
        }
    } 

    public static void zeigeTransVerlauf(Nutzer derNutzer, Scanner sc) {
        int derAcc;

        do {
            System.out.printf("Geben Sie die Nummer (1-%d) des Accounts ein: ", derNutzer.numAccounts());
            derAcc = sc.nextInt() - 1;
            if (derAcc < 0 || derAcc >= derNutzer.numAccounts()) {
                System.out.println("Invalider Account. Bitte versuchen Sie es erneut.");
            }
        } while (derAcc < 0 || derAcc >= derNutzer.numAccounts());

        derNutzer.gibDieAccountTransaktionen(derAcc);
    }

    public static void transferiereGeld(Nutzer derNutzer, Scanner sc) {
        int vonAcc, zumAcc;
        double bilanz, accBil;
        double menge;

        do {
            System.out.printf("Geben Sie die Nummer (1-%d) des Accounts ein, von dem sie aus ueberweisen moechten", derNutzer.numAccounts());
            vonAcc = sc.nextInt() - 1;
            if (vonAcc < 0 || vonAcc >= derNutzer.numAccounts()) {
                System.out.println("Invalider Account. Bitte versuchen Sie es erneut.");
            }
        } while (vonAcc < 0 || vonAcc >= derNutzer.numAccounts());
        accBil = derNutzer.getAccBilanz(vonAcc);

        do {
            System.out.printf("Geben Sie die Nummer (1-%d) des Accounts ein, zu dem sie ueberweisen moechten", derNutzer.numAccounts());
            zumAcc = sc.nextInt() - 1;
            if (zumAcc < 0 || zumAcc >= derNutzer.numAccounts()) {
                System.out.println("Invalider Account. Bitte versuchen Sie es erneut.");
            }
        } while (zumAcc < 0 || zumAcc >= derNutzer.numAccounts());

        do {
            System.out.printf("Geben Sie eine Menge zum Transfer ein (max. $%.02f): $", accBil);
            menge = sc.nextDouble();
            if (menge < 0) {
                System.out.println("Die Menge solle ueber 0 sein.");
            } else if(menge > accBil) {
                System.out.printf("Die Menge sollte nicht die aktuelle Bilanz uebersteigen. " + "Aktuelle Bilanz: $%.02f.\n", accBil);
            }
        } while(menge < 0 || menge > accBil);

        derNutzer.fuehreTransaktionAus(vonAcc, -1*menge, String.format("Transaktion zum Account %s", derNutzer.getAccUUID(zumAcc)));
        derNutzer.fuehreTransaktionAus(zumAcc, +1*menge, String.format("Transaktion zum Account %s", derNutzer.getAccUUID(vonAcc)));
    } 

    public static void zahleGeldAus(Nutzer derNutzer, Scanner sc) {
        int vonAcc;
        double bilanz, accBil;
        double menge;
        String memo;

        do {
            System.out.printf("Geben Sie die Nummer (1-%d) des Accounts ein, von dem Sie aus abheben moechten", derNutzer.numAccounts());
            vonAcc = sc.nextInt() - 1;
            if (vonAcc < 0 || vonAcc >= derNutzer.numAccounts()) {
                System.out.println("Invalider Account. Bitte versuchen Sie es erneut.");
            }
        } while (vonAcc < 0 || vonAcc >= derNutzer.numAccounts());
        accBil = derNutzer.getAccBilanz(vonAcc);

        do {
            System.out.printf("Geben Sie eine Menge zum Transfer ein (max. $%.02f): $", accBil);
            menge = sc.nextDouble();
            if (menge < 0) {
                System.out.println("Die Menge solle ueber 0 sein.");
            } else if(menge > accBil) {
                System.out.printf("Die Menge sollte nicht die aktuelle Bilanz uebersteigen. " + "Aktuelle Bilanz: $%.02f.\n", accBil);
            }
        } while(menge < 0 || menge > accBil);

        sc.nextLine();
        System.out.println("Memo eingeben: ");
        memo = sc.nextLine();

        derNutzer.fuehreTransaktionAus(vonAcc, -1*menge, memo);
    }

    public static void zahleGeldEin(Nutzer derNutzer, Scanner sc) {
        int zumAcc;
        double bilanz, accBil;
        double menge; //Keine Ahnung, ob das auch geht... Die Menge sollte eigentlich die sein, die der Nutzer eingibt.
        String memo;

        do {
            System.out.printf("Geben Sie die Nummer (1-%d) des Accounts ein, zu dem sie ueberweisen moechten", derNutzer.numAccounts());
            zumAcc = sc.nextInt() - 1;
            if (zumAcc < 0 || zumAcc >= derNutzer.numAccounts()) {
                System.out.println("Invalider Account. Bitte versuchen Sie es erneut.");
            }
        } while (zumAcc < 0 || zumAcc >= derNutzer.numAccounts());
        accBil = derNutzer.getAccBilanz(zumAcc);

        do {
            System.out.printf("Geben Sie eine Menge zum Transfer ein (max. $%.02f): $", accBil);
            menge = sc.nextDouble();
            if (menge < 0) {
                System.out.println("Die Menge solle ueber 0 sein.");
            }
        } while(menge < 0);

        sc.nextLine();
        System.out.println("Memo eingeben: ");
        memo = sc.nextLine();

        derNutzer.fuehreTransaktionAus(zumAcc, menge, memo);
    }
}
