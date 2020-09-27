public class Melding {
  String meldingString;
  int sekvensNr;
  int kanalId;

  public Melding(String m, int s, int k) {
    meldingString = m;
    sekvensNr = s;
    kanalId = k;
  }

  public String hent() {
    return meldingString;
  }

  public int hentSekvensNr() {
    return sekvensNr;
  }

  public int hentKanalId() {
    return kanalId;
  }
}
