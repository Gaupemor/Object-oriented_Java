/*
 * Fastlege, subklasse av Lege,
 * implementerar brukargrensesnittet Kommuneavtale.
 *
 * Tek inn og held eit heiltal avtalenummer,
 * med ein hentemetode som implementerar ein deklarert metode i Kommuneavtale.
 */

class Fastlege extends Lege implements Kommuneavtale {
  int avtalenummer;

  Fastlege(String n, int a) {
    super(n);
    avtalenummer = a;
  }

  //Implementerar metode deklarert i Kommuneavtale
  public int hentAvtalenummer() {
    return avtalenummer;
  }
}
