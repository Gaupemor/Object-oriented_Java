import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Labyrint representerar sjoelve labyrinten,
//og held ein array-tabell med rutane i labyrinten

public class Labyrint {

  //Held rutane i labyrinten som eit brett
  Rute[][] ruter;
  //Koordinatar
  int antRader;
  int antKolonner;

  //Hjelpeliste som held utvegar fraa labyrint
  //kvar gang finnUtveiFra vert kalla
  public Liste<String> utveiListe;

  private Labyrint(Rute[][] r, int ar, int ak) {
    ruter = r;
    antRader = ar;
    antKolonner = ak;

    //Legger Labyrint-referanse til kvar rute,
    //kan ikkje gjerast i lesFraFil() foer labyrinten vert oppretta
    for(Rute[] rad : ruter) {
      for(Rute rute : rad) {
        rute.leggTilLabyrint(this);
      }
    }
  }

  //toString() her returnerar labyrintbrettet i '.' og '#'
  @Override
  public String toString() {
    String labyrintUtskrift = "";
    labyrintUtskrift += (antRader + " " + antKolonner + "\n");
    for(Rute[] rad : ruter) {
      for(Rute rute : rad) {
        labyrintUtskrift += rute.tilTegn();
      }
      labyrintUtskrift += "\n";
    }
    return labyrintUtskrift;
  }

  //BYTTA OM, HER: Koordinat x, y
  //Finn alle utvegane fraa ruta med gjevne koordinatar
  public Liste<String> finnUtveiFra(int kol, int rad) {
    return ruter[rad][kol].finnUtvei();
  }

  //Finn kortaste utveg(ar) fraa
  public Liste<String> finnKortesteUtveiFra(int kol, int rad) {
    //Henter liste med utvegar v/finnUtveiFra()
    Liste<String> utveier = finnUtveiFra(kol, rad);
    //Ny liste som held kortaste utveg(ar)
    Liste<String> kortesteUtveier = new Lenkeliste<String>();
    //Om ingen utvegar finst...
    if(utveier.stoerrelse() == 0) return utveier;
    //teller for aa sjekka mot talet på '(' i stringen,
    //el. ruter i vegen
    int teller = 1;
    boolean kortesteUtveiFunnet = false;
    //Medan kortaste rute ikkje er funnen...
    while(!kortesteUtveiFunnet) {
      //For kvar veg i lista utveier...
      for(String s : utveier) {
        //...opprett ny int som held talet paa ruter i vegen
        int antallRuter = 0;
        //For kvart teikn i stringen...
        for(int i = 0; i < s.length(); i++) {
          //Sjekk om det er ei ny rute, og viss ja legg til talet paa ruter i veg
          if(s.charAt(i) == '(') antallRuter++;
        }
        //Om talet paa ruter i veg er lik tellartalet...
        if(antallRuter == teller) {
          //...legg til vegen som kortaste veg
          kortesteUtveier.leggTil(s);
          //sett kortaste veg er funnen
          kortesteUtveiFunnet = true;
        }
      }
      //Pluss paa teller
      teller++;
    }
    //Returner lista med kortaste utveg
    return kortesteUtveier;
  }

  //Finn talet paa steg for kortaste rute
  //returnerar -1 om ingen utvegar finst
  public int antStegIKortesteUtvei(int kol, int rad) {
    //Henter liste med korteste utveier
    Liste<String> kortesteUtveier = finnKortesteUtveiFra(kol, rad);
    //Om ingen utvegar, returner -1
    if(kortesteUtveier.stoerrelse() == 0) return -1;
    //For kvart teikn i stringen, tell '('
    int antSteg = 0;
    for(int i = 0; i < kortesteUtveier.hent(0).length(); i++) {
      if(kortesteUtveier.hent(0).charAt(i) == '(') antSteg++;
    }
    //Returner talet paa steg i vegen
    return antSteg;
  }

  //Static factory method: Opprett Labyrint-objekt etter info fraa fil
  public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
    //Leser inn fraa fil
    Scanner inn = new Scanner(fil);
    //Les tal paa rader og kolonnar i labyrinten, i 1. linje
    int antRaderInn = Integer.parseInt(inn.next());
    int antKolonnerInn = Integer.parseInt(inn.next());
    //Opprett ny, tom array
    Rute[][] ruterInn = new Rute[antRaderInn][antKolonnerInn];
    //Hjelpevariablar
    String linje = "";
    char tegn = '0';
    //Les inn info om ruter fraa fila
    //Ikkje for-each her fordi rutene ikkje er oppretta enno
    //For kvar rad...
    for(int i = 0; i < antRaderInn; i++) {
      //...les inn ei ny linje i fila
      linje = inn.next();
      //For kvart kolonne el. teikn i raden...
      for(int j = 0; j < antKolonnerInn; j++) {
        //...les av teiknet
        tegn = linje.charAt(j);
        if(tegn == '.') {
          //Labyrintopning om paa kanten
          if((i == 0) || (i == (antRaderInn - 1)) || (j == 0) || (j == (antKolonnerInn - 1))) ruterInn[i][j] = new Aapning(i, j);
          //Vanleg kvit rute om ikkje
          else ruterInn[i][j] = new HvitRute(i, j);
          //Svart rute om '#'
        } else ruterInn[i][j] = new SortRute(i, j);
      }
    }

    //Legg til naboar,
    //maa gjerast etter alle rutane er oppretta
    for(Rute[] rad : ruterInn) {
      for(Rute rute : rad) {
        //brukar privat metode for aa sjekka om nokre naboar vert null
        rute.leggTilNaboer(hentNaboer(rute.hentRad(), rute.hentKolonne(), antRaderInn, antKolonnerInn, ruterInn));
      }
    }

    //Opprett Labyrint-objektet
    return new Labyrint(ruterInn, antRaderInn, antKolonnerInn);
  }

  //Hjelpemetode for aa sjekke kva naboar ruta har
  private static Rute[] hentNaboer(int ra, int ko, int ar, int ak, Rute[][] r) {
    Rute[] naboer = new Rute[4];
    //0. nord
    if(ra == 0) naboer[0] = null;
    else naboer[0] = r[(ra - 1)][ko];
    //1. sor
    if(ra == (ar - 1)) naboer[1] = null;
    else naboer[1] = r[(ra + 1)][ko];
    //2. ost
    if(ko == (ak - 1)) naboer[2] = null;
    else naboer[2] = r[ra][(ko + 1)];
    //3. vest
    if(ko == 0) naboer[3] = null;
    else naboer[3] = r[ra][(ko - 1)];
    //Returner array med naboinfo
    return naboer;
  }
}
