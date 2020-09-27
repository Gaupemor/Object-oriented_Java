/*
 * Fastlege, subklasse av Lege,
 * implementerar brukargrensesnittet Kommuneavtale.
 *
 * Tek inn og held eit heiltal avtalenummer,
 * med ein hentemetode som implementerar ein deklarert metode i Kommuneavtale.
 */

class Fastlege extends Lege implements Kommuneavtale
{
  int avtalenummer;

  Fastlege(String navnInn, int avtalenummerInn)
  {
    super(navnInn);
    avtalenummer = avtalenummerInn;
  }

  //Implementerar metode deklarert i Kommuneavtale
  public int hentAvtalenummer()
  {
    return avtalenummer;
  }
}
