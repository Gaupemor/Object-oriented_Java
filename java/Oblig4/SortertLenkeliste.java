/*leggTi() - OM VERDI ER LIK VERDI TIL ELEMENTET I LISTA - KVA KJEM FYRST??
 * Den generiske klassa SortertLenkeliste<T>,
 * subklasse av <Lenkeliste T>,
 * og kor den ukjente typen T implementerar grensesnittet Comparable<T>,
 * held ei sortert lenkeliste med noder som held verdi av ukjent type.
 *
 * Klassa sorterer lista med samanlikning ved metoden compareTo(),
 * og har stengt metodane leggTil(int pos, T x) og sett() fraa superklassen Lenkeliste
 * for aa halda lista sortert.
 * Om ein proever aa naa desse, kastar klassa eit unntak av typen UnsupportedOperationException.
 */

public class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T> {

  //leggTil() fraa super er stengt,
  //skal ikkje vera mogleg aa legga til element i eigen vald posisjon for aa halda lista sortert
  @Override
  public void leggTil(int pos, T x) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Ulovlig aa legge til nytt element i egenvalgt posisjon i SortertLenkeliste, for aa holde rett rekkefoelge.");
  }

  //Legg til nytt element etter sortert rekkefylgje,
  //fraa minst foerst til stoerst sist
  @Override
  public void leggTil(T x) {
    //Om lista er tom...
    if(foerste == null) {
      //sett foerste.
      foerste = new Node(x);
    //Eller om den foerste verdien er stoerre enn den nye verdien...
    } else if(foerste.hentVerdi().compareTo(x) > 0) {
      //sett ny node som foerst,
      //og den foerre foerste som nesten til den nye noden.
      Node nyNode = new Node(x);
      nyNode.settNeste(foerste);
      foerste = nyNode;
    //Ellers (om den nye verdien er stoerre enn den foerste)...
    } else {
      //gaa gjennom lista til du finn verdien i lista foer det som er stoerre ELLER ERLIK den nye verdien
      Node foerNy = foerste;
      while((foerNy.hentNeste() != null) && (foerNy.hentNeste().hentVerdi().compareTo(x) < 0)) {
        foerNy = foerNy.hentNeste();
      }
      //Opprett ny node med gjeven verdi
      Node nyNode = new Node(x);
      //Sett inn den nye noden mellom den med mindre verdi og den med stoerre ELLER ERLIK verdi,
      //flyttar referansar
      Node etterNy = foerNy.hentNeste();
      foerNy.settNeste(nyNode);
      nyNode.settNeste(etterNy);
    }
  }

  //sett() fraa super er stengt,
  //skal ikkje vera mogleg aa setta ny verdi til ein node i vald posisjon for aa halda lista sortert
  @Override
  public void sett(int pos, T x) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Ulovlig aa sette ny verdi til element i SortertLenkeliste, for aa holde rett rekkefoelge.");
  }

  //Fjernar det stoerste elementet i lista,
  //d.v.s. det siste elementet naar lista er riktig sortert,
  //og returnerar verdien noden held,
  //kastar unntak om lista er tom
  @Override
  public T fjern() throws UgyldigListeIndeks {
    return fjern(stoerrelse() - 1);
  }
}
