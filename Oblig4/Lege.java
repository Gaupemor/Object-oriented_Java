/*
 * Lege, superklassen til Fastlege.
 *
 * Tek inn og held ein string 'navn' og ein hentemetode.
 */

class Lege implements Comparable<Lege> {
  String navn;
  Lenkeliste<Resept> resepter;

  public Lege(String navnInn) {
    navn = navnInn;
    resepter = new Lenkeliste<Resept>();
  }

  public String hentNavn() {
    return navn;
  }

  public void leggTilResept(Resept r) {
    resepter.leggTil(r);
  }

  public Lenkeliste<Resept> hentReseptliste() {
    return resepter;
  }

  @Override
  public int compareTo(Lege l) {
    //Sorter alfabetisk,
    //brukar String-klassa sin compareTo()
    return hentNavn().compareTo(l.hentNavn());
  }
}
