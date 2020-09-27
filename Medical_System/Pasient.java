public class Pasient {

  static int antObjektPasient;

  int Id;
  String navn;
  String foedselsnr;
  Stabel<Resept> resepter;

  public Pasient(String n, String f) {
    Id = settId();
    navn = n;
    foedselsnr = f;
    resepter = new Stabel<Resept>();
  }

  private int settId()
  {
    //Auker static int med ein,
    antObjektPasient++;
    //Returnerar talet
    return antObjektPasient;
  }

  public String hentNavn() {
    return navn;
  }

  public String hentFoedselsnr() {
    return foedselsnr;
  }

  public void leggTilResept(Resept r) {
    resepter.leggPaa(r);
  }

  public Stabel<Resept> hentReseptliste() {
    return resepter;
  }
}
