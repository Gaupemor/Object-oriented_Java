/*
Klassen Regneklynge.

Oppretter Node-objekt og Rack-objekt,
held racks i ei ArrayList,
og legg nodane inn i leidige racks.

M/ innlesing fraa fil.
*/

//For aa lesa fraa anna fil
import java.io.File;
//For aa sjaa at fila er ok
import java.io.FileNotFoundException;
//For aa lesa innverdi fraa fil
import java.util.Scanner;
//For aa oppretta ArrayList
import java.util.ArrayList;

public class Regneklynge
{
  //Oppretter instansvariablane:
  //Makstalet paa nodar per rack
  int noderPerRack;
  //ArrayList som held rackobjekt
  ArrayList<Rack> racks;
  //Fil som programmet skal lesa fraa
  File innfil;
  //Scanner som let programmet ta innverdi fraa anna fil
  Scanner inn;

  //Konstruktoeren m/Exception i tilfelle innfila er korrupt e.l.,
  //og tek inn stringverdi for namnet paa innfila.
  Regneklynge(String filnavn) throws FileNotFoundException
  {
    //Sett verdi for Scanner til innfil
    inn = new Scanner(innfil = new File(filnavn));

    //Sett verdi for ArrayList
    racks = new ArrayList<Rack>();

    //Kallar paa den private metoden leggTilNoderFraFil
    leggTilNoderFraFil();
  }

  //Privat metode som les inn verdiar m/Scanner fraa innfil,
  //brukar desse som parametarar til metoden leggTilNoder().
  private void leggTilNoderFraFil()
  {
    //Les inn 1. linje = talet for makstal paa nodar per rack
    noderPerRack = Integer.parseInt(inn.nextLine());
    //Medan det finst ein ny verdi i fila (linje)
    while(inn.hasNextInt())
    {
      //Sett verdiar for tala i linja i fila
      int antallNoderFil = inn.nextInt();
      int antallGBFil = inn.nextInt();
      int antallProsessorerFil = inn.nextInt();

      //For kvar verdi fraa 0 til verdien antallNoderFil...
      for(int i = 0; i < antallNoderFil; i++)
      {
          //Kall paa metoden leggTilNode() m/verdiar fraa fila
          leggTilNode(antallGBFil, antallProsessorerFil);
      }
    }
    //Lukk innfil
    inn.close();
  }

  //Metode som oppretter eit nodeobjekt,
  //med verdiar for minnestorleik og prosesortal metoden tek inn,
  //og legg noden til i eit leidig rack,
  //evt. oppretter eit nytt rack om alle er fulle.
  public void leggTilNode(int minneGBINode, int prosessorerINode)
  {
    //Nytt nodeobjekt m/tilhoeyrande veridar
    Node node = new Node(minneGBINode, prosessorerINode);
    boolean ledigeRacks = false;
    //(betinging for aa hindra at programmet proever aa leggja til same node i fleire racks)
    boolean nodeLagtTil = false;
    //For kvart rack i klynga (element i ArrayList)...
    for(Rack i : racks)
    {
      //Om det er leidig plass i racket og noden ikkje er lagt inn...
      if(i.sjekkLedigPlass() && !nodeLagtTil)
      {
        //legg noden til i det leidige racket
        i.leggTilNode(node);
        ledigeRacks = true;
        nodeLagtTil = true;
      }
    }
    //Om det ikkje er nokre leidige racks...
    if(!ledigeRacks)
    {
      //Oppretter nytt rack m/makstal paa noder per rack
      Rack rack = new Rack(noderPerRack);
      //Legg til rack i klynge (ArrayList)
      racks.add(rack);
      //Legg til noden i det nye racket
      rack.leggTilNode(node);
    }
  }

  //Funksjon som les av talet paa prosessorar i rekneklynga,
  //returnerar heiltalsverdi
  public int lesAntProsessorer()
  {
    int prosessorer = 0;
    //For kvart rack i rekneklynga (element i ArrayList)...
    for(Rack i : racks)
    {
      //legg til talet paa prosessorar i racket.
      prosessorer += i.lesAntProsessorer();
    }
    //Returner talet paa prosessorar
    return prosessorer;
  }

  //Funksjon som les av talet paa nodar i rekneklynga,
  //returnerar heiltalsverdi
  public int lesAntNoder()
  {
    int noder = 0;
    //For kvart rack i rekneklynga (element i ArrayList)...
    for(Rack i : racks)
    {
      //legg til talet paa nodar i racket.
      noder += i.lesAntNoder();
    }
    //Returner talet paa nodar
    return noder;
  }

  //Funksjon som les talet paa racks i rekneklynga,
  //returnerar heiltalsverdi
  public int lesAntRacks()
  {
    //Returner tal paa element i ArrayList
    return racks.size();
  }

  //Funksjon som tek inn minstekrav for minnestorleik,
  //og finn kor mange nodar i rekneklynga har stort nok minne,
  //returnerar heiltalsverdi
  public int noderMedNokMinne(int paakrevdMinne)
  {
    int noder = 0;
    //For kvart rack i rekneklynga (element i ArrayList)...
    for(Rack i : racks)
    {
      //legg til talet paa nodar med nok minne.
      noder += i.noderMedNokMinne(paakrevdMinne);
    }
    //Returner talet paa godkjente nodar.
    return noder;
  }
}
