/*
 * Testprogram som oppretter objekt av klassene
 * Legemiddel, Resept og Lege, og deira subklasser,
 * og testar metodane i super- og subklassene.
 */

class Testprogram
{
  public static void main(String[] args)
  {
    //Legemiddel-objekt,
    //av subklassene LegemiddelA, -B og -C
    Legemiddel a = new LegemiddelA("a", 449.5, 12.5, 10);
    Legemiddel b = new LegemiddelB("b", 299.99, 7.5, 5);
    Legemiddel c = new LegemiddelC("c", 100.0, 5.0);

    //Lege-objekt,
    //av superklassen og subklassen Fastlege
    Lege lars = new Lege("Lars");
    Lege rita = new Fastlege("Rita", 508216);

    //Resept-objekt,
    //av subklassene ReseptHvitP, ReseptHvitMilitaer og ReseptBlaa
    Resept hvitM = new ReseptHvitMilitaer(a, lars, 101, 2);
    Resept hvitPOverRabatt = new ReseptHvitP(b, lars, 102);
    Resept hvitPUnderRabatt = new ReseptHvitP(c, lars, 103);
    Resept blaa = new ReseptBlaa(a, rita, 104, 2);


    //1. testrunde - Legemiddel
    System.out.println("\nTest: Legemiddel\n");

    //Casting for aa naa eigne subklasse-metoder
    System.out.println("A:  Narkotisk styrke:     " + ((LegemiddelA)a).hentNarkotiskStyrke());
    System.out.println("B:  Vanedannande styrke:  " + ((LegemiddelB)b).hentVanedannendeStyrke());
    System.out.println("C:  Felles eigenskapar");
    //Tester alle felles metodar for super Legemiddel v/ LegemidelC-objekt c
    testFellesLegemiddel(c, 50.0);

    //2. testrunde - Resept
    System.out.println("\nTest: Resept\n");

    //@Override farge() i abstrakt subklasse ReseptHvit
    System.out.println(hvitPOverRabatt.farge() + " P-resept");
    //@Override prisAaBetale() i subklasse ReseptHvitP
    System.out.println("1.    Eigentleg pris:     " + hvitPOverRabatt.hentLegemiddel().hentPris());
    System.out.println("1.    Pris aa betala:     " + hvitPOverRabatt.prisAaBetale());
    System.out.println("2.    Eigentleg pris:     " + hvitPUnderRabatt.hentLegemiddel().hentPris());
    System.out.println("2.    Pris aa betala:     " + hvitPUnderRabatt.prisAaBetale());
    //ReseptHvitP-objekt skal alltid ha 3 reit
    System.out.println("      Reit:               " + hvitPOverRabatt.hentReit());
    System.out.println("        Proever aa bruka resept " + (hvitPOverRabatt.hentReit() + 1) + " gangar...");
    //Treng eigen variabel til for-loop, sidane verdien .hentReit() forandrast under loopen
    int startReit = hvitPOverRabatt.hentReit();
    //for-loop som sjekker bruk av resepten 1 gang meir enn reittalet
    for(int i = 0; i <= startReit; i++)
    {
      System.out.println("        " + hvitPOverRabatt.bruk());
    }
    //@Override farge() i abstrakt subklasse ReseptHvit
    System.out.println(hvitM.farge() + " militaerresept");
    //@Override prisAaBetale() i subklasse ReseptHvitMilitaer
    System.out.println("      Eigentleg pris:     " + hvitM.hentLegemiddel().hentPris());
    System.out.println("      Pris aa betala:     " + hvitM.prisAaBetale());
    //@Override farge() i subklasse ReseptBlaa
    System.out.println(blaa.farge() + " resept (m/ felles)");
    //@Override prisAaBetale() i subklasse ReseptBlaa
    System.out.println("      Eigentleg pris:     " + blaa.hentLegemiddel().hentPris());
    System.out.println("      Pris aa betala:     " + blaa.prisAaBetale());
    //Tester alle felles metodar for super Resept v/ ReseptBlaa-objekt blaa
    testFellesResept(blaa);

    //3. testrunde - Lege
    System.out.println("\nTest: Lege\n");

    //Testar Lege-objektet sin eine metode, .hentNavn()
    System.out.println("Lege " + lars.hentNavn() + " har ikkje kommuneavtale.");
    //Casting for aa naa den subklassemetoden .hentAvtalenummer() integrert v/brukargrensesnitt Kommuneavtale
    System.out.println("Fastlege " + rita.hentNavn() + " har avtalenr. " + ((Fastlege)rita).hentAvtalenummer() + ".");
    System.out.println();

  }

  //Testar alle metodane som er felles for Legemiddel-objekt,
  //teh inn eit Legemiddel-objekt og ein flyttalvariabel til ny pris
  static void testFellesLegemiddel(Legemiddel x, double nyPris)
  {
    System.out.println("      Legemiddelnamn:     " + x.hentNavn());
    System.out.println("      ID:                 " + x.hentID());
    System.out.println("      Verkestoff i mg:    " + x.hentVirkestoff());
    System.out.println("      Pris:               " + x.hentPris());
    //Gjev variabelen pris i objektet ein ny verdi
    x.settNyPris(nyPris);
    System.out.println("      Ny pris:            " + x.hentPris());
  }

  //Testar alle metodane som er felles for Resept-objekt,
  //og som gjev felles resultat v/ same input
  static void testFellesResept(Resept y)
  {
    System.out.println("      Farge:              " + y.farge());
    System.out.println("      ID:                 " + y.hentID());
    System.out.println("      PasientID:          " + y.hentPasientId());
    //Hentar Legemiddel-variabelen namn gjennom Resept-objektet sine metodar
    System.out.println("      Legemiddel namn:    " + y.hentLegemiddel().hentNavn());
    System.out.println("      Reit:               " + y.hentReit());
    System.out.println("        Proever aa bruka resept " + (y.hentReit() + 1) + " gangar...");
    //Treng eigen variabel til for-loop, sidan verdien .hentReit() forandrast under loopen
    int startReit = y.hentReit();
    //for-loop som sjekker bruk av resepten 1 gang meir enn reittalet
    for(int i = 0; i <= startReit; i++)
    {
      System.out.println("        " + y.bruk());
    }
    //Hentar Lege-variabelen namn gjennom Resept-objektet sine metodar
    System.out.println("      Utskriven v/ " + y.hentLege().hentNavn());
  }
}
