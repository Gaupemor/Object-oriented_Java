import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Filnavn som 0. argument
//'Detaljert' som 1. argument for aa see alle utveier
//Unnlat 1. argument for aa see antall mulige utveier og den/de korteste ruta/rutene

class Oblig5 {
    public static void main(String[] args) {
        String filnavn = null;
        if (args.length > 0) {
          //Sjekker om bruker skrev inn annet 1. argument enn 'detaljert'
          if(args.length > 1 && !args[1].equals("detaljert")) {
            System.out.println("Feil argument etter filnavn. Enten 'detaljert' eller ingenting.");
            return;
          }
            filnavn = args[0];
        } else {
            System.out.println("FEIL! Riktig bruk av programmet: "
                               +"java Oblig5 <labyrintfil>");
            return;
        }
        File fil = new File(filnavn);
        Labyrint l = null;
        try {
            l = Labyrint.lesFraFil(fil);
        } catch (FileNotFoundException e) {
            System.out.printf("FEIL: Kunne ikke lese fra '%s'\n", filnavn);
            System.exit(1);
        }

        System.out.println(l.toString());

        // les start-koordinater fra standard input
        Scanner inn = new Scanner(System.in);
        System.out.println("Skriv inn koordinater <kolonne> <rad> ('a' for aa avslutte)");
        String[] ord = inn.nextLine().split(" ");
        while (!ord[0].equals("a")) {

            try {
                int startKol = Integer.parseInt(ord[0]);
                int startRad = Integer.parseInt(ord[1]);

                Liste<String> utveier = l.finnUtveiFra(startKol, startRad);
                if (utveier.stoerrelse() != 0) {

                    try {
                        //Skriver ut hver utvei om 'detaljert'
                        if(args[1].equals("detaljert")) {
                            for (String u : utveier) {
                                System.out.println(u);
                            }
                        }
                    } catch(ArrayIndexOutOfBoundsException f) {
                        //Skriver ut antall utveier og den/de korteste ruta/rutene
                        System.out.println("Fant i det hele " + utveier.stoerrelse() + " utvei(er) fra labyrinten.");
                        System.out.println("Korteste rute(r):");
                        System.out.println(l.antStegIKortesteUtvei(startKol, startRad) + " steg");
                        utveier = l.finnKortesteUtveiFra(startKol, startRad);
                        for(String v : utveier) {
                          System.out.println(v);
                        }
                    }
                } else {
                    System.out.println("Ingen utveier.");
                }
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Ugyldig input!");
            }

            System.out.println("Skriv inn nye koordinater ('a' for aa avslutte)");
            ord = inn.nextLine().split(" ");
        }
    }
}
