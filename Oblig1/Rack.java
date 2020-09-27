/*
Klassen Rack.

Held opp til eit gjeven tal med nodar i ei ArrayList.
Klassa hentar i hovudsak informasjon fraa nodane objektet held.
*/

//For aa kunne oppretta ei ArrayList
import java.util.ArrayList;

public class Rack
{
  //Oppretter instansvariablane:
  //Maks tal paa nodar i racket
  int maksAntNoder;
  //Liste som held nodeobjekt
  ArrayList<Node> noder;

  //Konstruktoeren,
  //som tek inn makstalet paa nodarr og sett verdien til instansvariabelen,
  //og gjev verdi til lista m/nodar
  public Rack(int maksAntNoderInn)
  {
    maksAntNoder = maksAntNoderInn;
    noder = new ArrayList<Node>();
  }

  //Funksjon som sjekker om det er leidig plass i racket,
  //returnerar sanningsverdi
  public boolean sjekkLedigPlass()
  {
    //Sett ikkje leidig plass
    boolean ledigPlass = false;
    //Viss makstal for nodar i rack er stoerre enn tal paa element i ArrayList...
    if(maksAntNoder > noder.size())
    {
      //er det leidig plass.
      ledigPlass = true;
    }
    //Returner om det er leidig plass eller ikkje
    return ledigPlass;
  }

  //Metode som legg til nytt nodeobjekt i ArrayList
  public void leggTilNode(Node nyNode)
  {
    noder.add(nyNode);
  }

  //Funksjon som les talet paa prosessorar i racket,
  //returnerar heiltalsverdi
  public int lesAntProsessorer()
  {
    int prosessorer = 0;
    //For kvar node i racket (i ArrayList)...
    for(Node i : noder)
    {
      //Legg til talet paa prosessorar.
      prosessorer += i.lesAntProsessorer();
    }
    //Returner talet paa prosessorar
    return prosessorer;
  }

  //Funksjon som les talet paa nodar i racket,
  //returnerar heiltalsverdi
  public int lesAntNoder()
  {
    //Returnerar talet paa element i ArrayList
    return noder.size();
  }

//Funksjon som sjekker om kvar node i racket har eit minimum tal m/minne,
//returnerar heiltalsverdi
  public int noderMedNokMinne(int paakrevdMinne)
  {
    //Teljar for godkjente nodar
    int noderGodkjente = 0;
    //For kvar node i racket (i ArrayList)...
    for(Node i : noder)
    {
      //Viss noden har meir eller likt minimumskravet for minne...
      if(i.lesMinneGB() >= paakrevdMinne)
      {
        //legg ein til teljaren
        noderGodkjente++;
      }
    }
    //Returnerar talet paa godkjente nodar
    return noderGodkjente;
  }
}
