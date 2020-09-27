//Rute representerar ei rute i labyrinten,
//er abstrakt og maa verta implementert som anten ein kvit eller svart rute

public abstract class Rute {

  //Labyrinten
  Labyrint labyrint;
  //Koordinatar
  int rad;
  int kol;
  //Naborutene
  Rute nord;
  Rute sor;
  Rute ost;
  Rute vest;

  //Tek inn posisjonen i konstruktoeren,
  //indeks for rad og kolonne
  public Rute(int r, int k) {
    rad = r;
    kol = k;
  }

  //Rekursiv funksjon for aa finna utgang i labyrinten,
  //kalt fraa finnUtvei()
  private void gaa(Rute start, String veiInn) {
    //Held koordinaten til ruta
    String koor = ("(" + kol + ", " + rad + ")");

    //BASISSTEG
    //Om svart, bryt av.
    if(tilTegn() == '#') return;
    //Om har vore paa ruta foer, syklisk, bryt av.
    if(veiInn.contains(koor)) return;

    //Om du skal halda fram, legg ruta sin koordinat til 'vei'
    String vei = veiInn + " --> " + koor;

    //Om opning...
    if((nord == null) || (sor == null) || (ost == null) || (vest == null)) {
      //...legg til vegen i instansvariabelen utveiListe i Labyrint.
      labyrint.utveiListe.leggTil(vei);
      return;
    }

    //REKURSJONSSTEG
    //Om naboen ikkje var den forrige og er ei kvit rute, gaa til naboen
    if((nord != start) && (tilTegn() == '.')) nord.gaa(this, vei);
    if((sor != start) && (tilTegn() == '.')) sor.gaa(this, vei);
    if((ost != start) && (tilTegn() == '.')) ost.gaa(this, vei);
    if((vest != start) && (tilTegn() == '.')) vest.gaa(this, vei);
  }

  //Returnerar ei Liste<String> med utvegane fraa denne ruta i labyrinten
  public Liste<String> finnUtvei() {
    //Oppretter tom liste for instansvariabelen utveiListe i labyrinten
    labyrint.utveiListe = new Lenkeliste<String>();
    //Om start paa svart, send attende
    if(tilTegn() == '#') return labyrint.utveiListe;

    //Gaa for kvar retning, sjekker om startruta er ein utgang
    String koor = "(" + kol + ", " + rad + ")";
    //nord
    if(nord == null) labyrint.utveiListe.leggTil(koor);
    else nord.gaa(this, koor);
    //sor
    if(sor == null) labyrint.utveiListe.leggTil(koor);
    else sor.gaa(this, koor);
    //ost
    if(ost == null) labyrint.utveiListe.leggTil(koor);
    else ost.gaa(this, koor);
    //vest
    if(vest == null) labyrint.utveiListe.leggTil(koor);
    else vest.gaa(this, koor);
    //Returner lenkelista
    return labyrint.utveiListe;
  }

  public int hentRad() {
    return rad;
  }

  public int hentKolonne() {
    return kol;
  }

  //Legger til naboane til ruta (v/hentNaboer i Labyrint)
  public void leggTilNaboer(Rute[] n) {
    nord = n[0];
    sor = n[1];
    ost = n[2];
    vest = n[3];
  }

  //Legger til Labyrint-referansen
  public void leggTilLabyrint(Labyrint l) {
    labyrint = l;
  }

  //Implementerast i HvitRute, '.' og SortRute, '#'
  public abstract char tilTegn();
}
