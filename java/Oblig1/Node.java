/*
Klassen Node.

Representerar ein datamaskin i ei rekneklynge.

Tek inn talet paa prosessorar og minnestorleik i GB som parametarar,
og kan returnera desse verdiane.
*/

public class Node
{
  //Oppretter instansvariablane:
  //Tal paa prosessorar
  private int antProsessorer;
  //Tal paa minne i GB
  private int minneGB;

  //Konstruktoeren, tek inn prosessortal og minnestorleik,
  //sett innverdiane til instansvariablane
  public Node(int minneGBInn, int antProsessorerInn)
  {
    antProsessorer = antProsessorerInn;
    minneGB = minneGBInn;
  }

  //Returnerar talet paa prosessorar
  public int lesAntProsessorer()
  {
    return antProsessorer;
  }

  //Returnerar talet paa minne i GB
  public int lesMinneGB()
  {
    return minneGB;
  }
}
