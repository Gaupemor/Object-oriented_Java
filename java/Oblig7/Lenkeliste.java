/*
 * Den generiske klassa Lenkeliste<T>,
 * implementerar grensesnittet Liste<T>,
 * held ei enkeltlenka liste av noder.
 *
 * Nodene held referanse til neste node i lista
 * og sin eigen verdi av ukjent type T.
 *
 * FIFO - fjern()
 */

import java.util.Iterator;

public class Lenkeliste<T> implements Liste<T> {

  //Den foerste noden i lenkelista
  protected Node foerste;

  //Set foerste noden som ingenting til aa byrja med
  public Lenkeliste() {
    foerste = null;
  }

  //Nodeklasse for noder i lenkelista
  public class Node {
    //Verdien av ukjent type T noden held
    T verdi;
    //Den neste noden i lenkelista
    Node neste;

    //Tek inn og sett verdi av ukjent type T,
    //sett referanse til neste node som ingenting til aa byrja med
    public Node(T v) {
      verdi = v;
      neste = null;
    }

    //Hent verdien av ukjent type T noden held
    public T hentVerdi() {
      return verdi;
    }

    //Sett ny verdi av ukjent type T
    public void settVerdi(T nyVerdi) {
      verdi = nyVerdi;
    }

    //Hent neste node i lenkelista
    public Node hentNeste() {
      return neste;
    }

    //Sett neste node i lenkelista
    public void settNeste(Node n) {
      neste = n;
    }
  }

  public class LenkelisteIterator<T> implements Iterator<T> {

    private Lenkeliste<T> lenkeliste;
    private int indeks;

    public LenkelisteIterator(Lenkeliste<T> l) {
      lenkeliste = l;
    }

    public boolean hasNext() {
      return indeks < lenkeliste.stoerrelse();
    }

    public T next() {
      return lenkeliste.hent(indeks++);
    }

  }

  //Returnerar ny iterator for denne lenkelista
  @Override
  public LenkelisteIterator<T> iterator() {
    return new LenkelisteIterator<T>(this);
  }

  //Returnerar storleiken, talet paa element, i lenkelista
  @Override
  public int stoerrelse() {
    Node naaverende = foerste;
    int i = 0;
    //Tel 1 for kvart element i lista til elementet er null
    while(naaverende != null) {
      naaverende = naaverende.hentNeste();
      i++;
    }
    //Returerar talet paa element i lenkelista
    return i;
  }

  //Legg til nytt element i vald posisjon,
  //kastar unntak om indeksen gjeven er ugyldig
  @Override
  public void leggTil(int pos, T x) throws UgyldigListeIndeks {
    //Sjekker om gjeven indeks er gyldig, om ikkje forkast - minustal eller over storleik
    if((pos < 0) || (pos > stoerrelse())) {
      throw new UgyldigListeIndeks(pos);
    }
    //Held fram om pos er gyldig indeks
    //Om pos er null...
    if(pos == 0) {
      //sett foerste som ny node, flytt referansar
      Node nyNode = new Node(x);
      //Om lista ikkje allereie er tom...
      if(stoerrelse() != 0){
        //Sett foerre foerste som nesten til noden
        nyNode.settNeste(foerste);
      }
      //Ny node er foerst
      foerste = nyNode;
    //Om pos ikkje er 0...
    } else {
      //Gaa gjennom lista til du kjem til posisjonen
      Node etterNy = foerste;
      Node foerNy = null;
      for(int i = 0; i < pos; i++) {
        foerNy = etterNy;
        etterNy = etterNy.hentNeste();
      }
      //Opprett ny node
      Node nyNode = new Node(x);
      //Flyttar referansar og legg ny node inn i lenkelista
      foerNy.settNeste(nyNode);
      nyNode.settNeste(etterNy);
    }
  }

  //Legg til ny node bakerst i lista
  @Override
  public void leggTil(T x) {
    //Om lista er tom...
    if(foerste == null) {
      //sett foerste
      foerste = new Node(x);
    //Om ikkje...
    } else {
      Node foerNy = foerste;
      //gaar gjennom lista til han kjem til endes
      while(foerNy.hentNeste() != null) {
        foerNy = foerNy.hentNeste();
      }
      //Legg til bakerst
      foerNy.settNeste(new Node(x));
    }
  }

  //Sett ny verdi til node ved gjeven indeks,
  //kastar unntak om indeksen gjeven er ugyldig
  @Override
  public void sett(int pos, T x) throws UgyldigListeIndeks {
    //Sjekker om gjeven indeks er gyldig, om ikkje forkast - tom liste, minustal eller pos over eller erlik storleiken
    if((foerste == null) || (pos < 0) || (pos >= stoerrelse())) {
      throw new UgyldigListeIndeks(pos);
    }
    //Held fram om pos er gyldig indeks
    Node endreNode = foerste;
    //Gaar gjennom lista til pos...
    for(int i = 0; i < pos; i++) {
      endreNode = endreNode.hentNeste();
    }
    //Sett ny verdi den valde til noden
    endreNode.settVerdi(x);
  }

  //Returnerar verdien til noden etter gjeven indeks,
  //UTEN aa fjerna noden,
  //kastar unntak om indeksen gjeven er ugyldig
  @Override
  public T hent(int pos) throws UgyldigListeIndeks {
    //Sjekker om gjeven indeks er gyldig, om ikkje forkast - tom liste, minustal eller pos over eller erlik storleiken
    if((foerste == null) || (pos < 0) || (pos >= stoerrelse())) {
      throw new UgyldigListeIndeks(pos);
    }
    //Held fram om pos er gyldig indeks
    Node hentNode = foerste;
    //Gaar gjennom lista til pos...
    for(int i = 0; i < pos; i++) {
      hentNode = hentNode.hentNeste();
    }
    //Returner verdien som noden ved gjeven indeks held
    return hentNode.hentVerdi();
  }

  //Returnerar veriden til noden etter gjeven indeks,
  //fjernar noden og flyttar referansar,
  //kastar unntak om indeksen gjeven er ugyldig
  @Override
  public T fjern(int pos) throws UgyldigListeIndeks {
    //Sjekker om gjeven indeks er gyldig, om ikkje forkast - tom liste, minustal eller pos over eller erlik storleiken
    if(foerste == null) {
      throw new UgyldigListeIndeks(-1);
    } else if ((pos < 0) || (pos >= stoerrelse())) {
      throw new UgyldigListeIndeks(pos);
    }
    //Held fram om pos er gyldig indeks
    Node fjernNode = foerste;
    Node foerFjern = null;
    //Gaar gjennom lista til pos...
    for(int i = 0; i < pos; i++) {
      foerFjern = fjernNode;
      fjernNode = fjernNode.hentNeste();
    }
    //Opprett ny peikar for verdien til noden
    T returnerVerdi = fjernNode.hentVerdi();
    //Flyttar referansar og fjernar noden fraa lenkelista
    //Om aleine i lista...
    if((foerFjern == null) && (fjernNode.hentNeste() == null)) {
      //sett foerste tom.
      foerste = null;
    //Ellers om noden er foerst i lista...
    } else if (foerFjern == null) {
      //sett foerste som nesten til foerste.
      foerste = foerste.hentNeste();
    //Ellers (midt i eller til slutt i lista)...
    } else {
      //sett nesten til noden foer fjernNode som nesten til fjernNode.
      foerFjern.settNeste(fjernNode.hentNeste());
    }
    //Returner verdien til noden som vart fjerna
    return returnerVerdi;
  }

  //Fjernar foerste node i lista og returnerar verdien han held, FIFO,
  //kastar unntak om lista er tom
  @Override
  public T fjern() throws UgyldigListeIndeks {
    //Forkast om lista er tom, returner -1
    if(foerste == null) {
      throw new UgyldigListeIndeks(-1);
    }
    //Opprett ny peikar for verdien til noden
    T returnerVerdi = foerste.hentVerdi();
    //Fjernar foerste element i lenkelista, sett foerste som nesten til foerste
    foerste = foerste.hentNeste();
    //Returnerar verdien som vart fjerna
    return returnerVerdi;
  }
}
