/*
 * Lege, superklassen til Fastlege.
 *
 * Tek inn og held ein string 'navn' og ein hentemetode.
 */

class Lege
{
  String navn;

  Lege(String navnInn)
  {
    navn = navnInn;
  }

  public String hentNavn()
  {
    return navn;
  }
}
