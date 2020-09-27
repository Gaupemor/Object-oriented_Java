/*
Hovudprogrammet m/main-metode.
For aa sjekka om klassene fungerar som dei skal.
M/innlesing fraa fil.

Oppretter eit Regneklynge-objekt, abel.

Skriv ut talet paa nodar etter minnestorleik,
talet paa prosessorar,
og talet paa racks i rekneklynga.
*/

//For aa sjaa at fila er ok
import java.io.FileNotFoundException;

class Hovedprogram
{
  //Metoden main som koeyrer programmet,
  //vert kasta om det er problem med innfila
  public static void main(String[] args) throws FileNotFoundException
  {
    System.out.println("\nOblig 1 - IN1010 - Vår 2018\n");

    //Namnet paa fila abel skal lesa fraa
    String innfil = "regneklynge.txt";
    //Oppretter rekneklynga abel m/filnamn som parametar
    Regneklynge abel = new Regneklynge(innfil);

    //Skriv ut verdiane for abel
    System.out.println();
    System.out.println("Noder m/ minst... ");
    System.out.println("   32 GB: " + abel.noderMedNokMinne(32));
    System.out.println("   64 GB: " + abel.noderMedNokMinne(64));
    System.out.println("   128 GB: " + abel.noderMedNokMinne(128));
    System.out.println("");
    System.out.println("Antall prosessorer: " + abel.lesAntProsessorer());
    System.out.println("Antall racks: " + abel.lesAntRacks());
  }
}
