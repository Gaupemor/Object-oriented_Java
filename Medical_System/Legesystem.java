/*
 * Brukarprogram for legesystemet.
 */

import java.util.Scanner;

public class Legesystem {

  static String exit = "'exit' for aa gaa attende til hovudmenyen.\n";

  public static void main(String[] args) {
    //Leger sortert alfabetisk etter namn
    SortertLenkeliste<Lege> leger = new SortertLenkeliste<Lege>();
    Lenkeliste<Resept> resepter = new Lenkeliste<Resept>();
    Lenkeliste<Legemiddel> legemiddler = new Lenkeliste<Legemiddel>();
    Lenkeliste<Pasient> pasienter = new Lenkeliste<Pasient>();

    //Scanner  for brukarinput
    Scanner inn = new Scanner(System.in);

    //Hovudmenyen
    String hovedmeny = "~Hovudmeny~\n0. Skriv ut totaloversikt\n1. Legg til nytt element i system\n2. Bruk resept til vald pasient\n3. Skriv ut statistikk\n";
    System.out.print(hovedmeny + "'exit' for aa avslutta programmet.\n> ");

    //Tek inn brukarinput
    String input = inn.nextLine();


    //D1 - Loop for hovudmeny
    boolean cont = true;
    while(cont) {
      //Om brukar skreiv 'exit'...
      if(input.equals("exit")) {
        //...gaa ut av loopen (avslutt program)
        cont = false;
      //0. skriv ut totaloversikt
      } else if(input.equals("0")) {
        System.out.println();
        skrivUtTotaloversikt(leger, resepter, legemiddler, pasienter);
        //Skriver ut menyen att
        System.out.print(hovedmeny + exit + "> ");
        //Tek inn brukarinput att, gaar gjennom loopen att
        input = inn.nextLine();
      //1. legg nytt element til systemet
      } else if(input.equals("1")) {
        System.out.println();
        leggInnNyttElement(leger, resepter, legemiddler, pasienter);
        System.out.print(hovedmeny + exit + "> ");
        input = inn.nextLine();
      //2. bruk ein resept til ein pasient
      } else if(input.equals("2")) {
        System.out.println();
        brukResept(pasienter, resepter);
        System.out.print(hovedmeny + exit + "> ");
        input = inn.nextLine();
      //3. skriv ut statistikk
      } else if(input.equals("3")) {
        System.out.println();
        skrivUtStatistikk(leger, resepter, legemiddler, pasienter);
        System.out.print(hovedmeny + exit + "> ");
        input = inn.nextLine();
      //Klarer terminalen (skjult kommando)
      } else if(input.equals("clear")) {
        for(int i = 0; i < 51; i++) {
          System.out.println();
        }
        System.out.print(hovedmeny + exit + "> ");
        input = inn.nextLine();
      //Om, noko anna, ugyldig input, gjev feilmelding.
      } else {
        System.out.println("\nUgyldig input.\n");
        System.out.print(hovedmeny + exit + "> ");
        input = inn.nextLine();
      }
    }
  }

  //D2
  static void skrivUtTotaloversikt(SortertLenkeliste<Lege> leger, Lenkeliste<Resept> resepter, Lenkeliste<Legemiddel> legemiddler, Lenkeliste<Pasient> pasienter) {
    String ingen = "        Ingen registrerte for augenblinken.";
    System.out.println("Totaloversikt");

    //Leger - skrivast ut i alfabetisk rekkefylgje (sortert lenkeliste)
    System.out.println("    Leger (m/ alle utskrivne resepter):");
    //Om ingen legar i systemet, gjev melding.
    if(leger.stoerrelse() == 0) {
      System.out.println(ingen);
    //Om minst ein lege finst...
    } else {
      //For kvar lege, skriv info
      for(Lege l : leger) {
        System.out.println("        " + l.hentNavn());
        for(Resept r :  l.hentReseptliste()) {
          System.out.print("            ");
          System.out.printf("%-15s%s%-15s%5s", r.hentLegemiddel().hentNavn(), " til pasient ", r.hentPasient().hentNavn(), "(ID: " + r.hentId() + ")");
          System.out.println();
        }
      }
      System.out.println();
    }

    //Pasientar
    System.out.println("    Pasienter (m/ alle registrerte resepter):");
    if(pasienter.stoerrelse() == 0) {
      System.out.println(ingen);
    } else {
      for(Pasient p : pasienter) {
        System.out.print("        " + p.hentNavn() + " (" + p.hentFoedselsnr() + ")");
        for(Resept r : p.hentReseptliste()) {
          System.out.print("\n            ");
          System.out.printf("%-4s%s%-15s%s", r.farge(), " resept paa ", r.hentLegemiddel().hentNavn(), " fraa " + r.hentLege().hentNavn() + " (" + r.hentReit() + " reit att).");
        }
        System.out.println();
      }
      System.out.println();
    }

    //Legemiddel
    System.out.println("    Legemiddel i systemet:");
    if(legemiddler.stoerrelse() == 0) {
      System.out.println(ingen);
    } else {
      for(Legemiddel l : legemiddler) {
        //Skriv ut om vanleg, narkotisk eller vanedannende
        System.out.print("        ");
        //Formatert output
        System.out.printf("%-15s%s%5.2f%s%6.2f%s", l.hentNavn(), "inneheld ", l.hentVirkestoff(), " mg verkestoff for kr. ", l.hentPris(), " (ID: " + l.hentId() + ")");
        System.out.println();
      }
    }
    System.out.println();
  }


  //D3
  static void leggInnNyttElement(SortertLenkeliste<Lege> leger, Lenkeliste<Resept> resepter, Lenkeliste<Legemiddel> legemiddler, Lenkeliste<Pasient> pasienter) {
    //Sjekk at opplysingane er gyldiSortertLenkeliste<Lege> leger, Lenkeliste<Resept> respeter, Lenkeliste<Legemiddel> legemiddler, Lenkeliste<Pasient> pasienterge foer du opprettar objektet!!
    //Lege, pasient og legemiddel kan opprettast uavhengig av andre objekt,
    //Men Resept krev at du har gyldig Lege, pasient og legemiddel foer du kan leggja det inn i systemet

    Scanner inn = new Scanner(System.in);
    String input = "";
    //Held paa til ein har laga eit gyldig objekt,
    //avbryt eller vert avbryta
    boolean cont = true;
    while(cont) {
      System.out.println("Kva type element vil du leggja til i systemet?");
      System.out.println("0. Lege");
      System.out.println("1. Pasient");
      System.out.println("2. Legemiddel");
      System.out.println("3. Resept");
      System.out.print(exit);

      input = inn.nextLine();

      if(input.equals("0")) {
        System.out.println("\nVald aa oppretta og leggja til ein ny lege.");
        System.out.println("Skriv inn informasjon om legen...");

        String legeNavn = "";
        int legeAvtalenr = -1;

        System.out.print("Namn: ");
        input = inn.nextLine();
        cont = true;
        while(cont) {
          if(inneholder(input, "tall")) {
            System.out.println("Ugyldig input. Namnet til legen kan ikkje innehalda tal.");
            System.out.print("Namn: ");
            input = inn.nextLine();
          } else {
            legeNavn = input;
            cont = false;
          }
        }

        System.out.print("Avtalenummer (0 om ingen kommuneavtale): ");
        input = inn.nextLine();
        while(legeAvtalenr == -1) {
          try {
          legeAvtalenr = Integer.parseInt(input);
          } catch(NumberFormatException e) {
            System.out.println("Ugyldig input. Avtalenummeret maa vera eit gyldig heiltal.");
            System.out.print("Avtalenummer (0 om ingen kommuneavtale): ");
            input = inn.nextLine();
          }
        }

        if(legeAvtalenr == 0) {
          leger.leggTil(new Lege(legeNavn));
          System.out.println("\nLagt til ny lege i systemet.\n");
        } else {
          leger.leggTil(new Fastlege(legeNavn, legeAvtalenr));
          System.out.println("\nLagt til ny fastlege i systemet.\n");
        }

      } else if(input.equals("1")) {

        String pasientNavn = "";
        String pasientFoedselsnr = "";

        System.out.println("\nVald aa oppretta og leggja til ein ny pasient.");
        System.out.println("Skriv inn informasjon om pasienten...");

        System.out.print("Namn: ");
        input = inn.nextLine();
        cont = true;
        while(cont) {
          if(inneholder(input, "tall")) {
            System.out.println("Ugyldig input. Namnet til pasienten kan ikkje innehalda tal.");
            System.out.print("Namn: ");
            input = inn.nextLine();
          } else {
            pasientNavn = input;
            cont = false;
          }
        }

        System.out.print("Foedselsnummer: ");
        input = inn.nextLine();
        cont = true;
        while(cont) {
          if(inneholder(input, "bokstaver") || input.length() != 11) {
            System.out.println("Ugyldig input. Foedselsnummer maa vera eit nummer paa 11 siffer.");
            System.out.print("Foedselsnummer: ");
            input = inn.nextLine();
          } else {
            pasientFoedselsnr = input;
            cont = false;
          }
        }

        pasienter.leggTil(new Pasient(pasientNavn, pasientFoedselsnr));
        System.out.println("\nNy pasient " + pasientNavn + " er lagt til i systemet.\n");
        return;

      } else if(input.equals("2")) {

        String legemiddelNavn = "";
        double legemiddelPris = 0;
        double legemiddelVirkestoff = 0;
        int legemiddelGrad = 0;

        System.out.println("\nVald aa oppretta og leggja til eit nytt legemiddel.");
        System.out.println("Skriv inn informasjon om legemiddelet...");

        System.out.print("Namn: ");
        input = inn.nextLine();
        cont = true;
        while(cont) {
          if(inneholder(input, "tall")) {
            System.out.println("Ugyldig input. Namnet paa legemiddelet kan ikkje innehalda tal.");
            System.out.print("Namn: ");
            input = inn.nextLine();
          } else {
            legemiddelNavn = input;
            cont = false;
          }
        }

        System.out.print("Pris (desimaltal m/punktum): ");
        input = inn.nextLine();
        while(legemiddelPris == 0) {
          try {
            legemiddelPris = Double.parseDouble(input);
          } catch(NumberFormatException e) {
            System.out.println("Ugyldig input. Prisen maa vera eit gyldig desimaltal.");
            System.out.print("Pris (desimaltal m/punktum): ");
            input = inn.nextLine();
          }
        }

        System.out.print("Verkestoff i mg (desimaltal m/punktum): ");
        input= inn.nextLine();
        while(legemiddelVirkestoff == 0) {
          try {
            legemiddelVirkestoff = Double.parseDouble(input);
          } catch(NumberFormatException e) {
            System.out.println("Ugyldig input. Mengda av verkestoff maa vera eit gyldig desimaltal.");
            System.out.print("Verkestoff i mg (desimaltal m/punktum): ");
            input = inn.nextLine();
          }
        }

        System.out.println("Kva type legemiddel vil du oppretta?");
        System.out.println("0. Narkotisk legemiddel");
        System.out.println("1. Vanedannande legemiddel");
        System.out.println("2. Alminneleg legemiddel");
        System.out.print(exit);
        input = inn.nextLine();
        cont = true;
        while(cont) {
          if(input.equals("0") || input.equals("1")) {
            System.out.print("Grad av styrke (heiltal): ");
            input = inn.nextLine();
            while(legemiddelGrad == 0){
              try {
              legemiddelGrad = Integer.parseInt(input);
              } catch(NumberFormatException e) {
                System.out.println("Ugyldig input. Styrke maa vera eit gyldig heiltal.");
                System.out.print("Grad av styrke (heiltal): ");
                input = inn.nextLine();
              }
            }
            //Narkotisk
            if(input.equals("0")) {
              legemiddler.leggTil(new LegemiddelA(legemiddelNavn, legemiddelPris, legemiddelVirkestoff, legemiddelGrad));
              System.out.println("\nNytt narkotisk legemiddel " + legemiddelNavn + " er lagt til i systemet.\n");
              return;
            }
            //Vanedannande
            else {
              legemiddler.leggTil(new LegemiddelB(legemiddelNavn, legemiddelPris, legemiddelVirkestoff, legemiddelGrad));
              System.out.println("\nNytt vanedannande legemiddel " + legemiddelNavn + " er lagt til i systemet.\n");
              return;
            }
          } else if(input.equals("2")) {
            legemiddler.leggTil(new LegemiddelC(legemiddelNavn, legemiddelPris, legemiddelVirkestoff));
            System.out.println("\nNytt alminneleg legemiddel " + legemiddelNavn + " er lagt til i systemet.\n");
            return;
          } else if(input.equals("exit")) {
            System.out.println("\nGaar attende til hovudmenyen.\n");
            return;
          } else {
            System.out.println("Ugyldig input");
            input = inn.nextLine();
          }
        }

      } else if(input.equals("3")) {
        System.out.println("\nVald aa oppretta og leggja til en ny resept.");
        if(leger.stoerrelse() == 0 || pasienter.stoerrelse() == 0 || legemiddler.stoerrelse() == 0) {
          System.out.println("\nFeil. Anten finst det ikkje ein gyldig lege, pasient, legemiddel, eller fleire av desse i systemet.");
          System.out.println("Du kan ikkje oppretta ein ny respet foer det finst minst eit element av alle desse i systemet.");
          System.out.println("Sjaa med 'Skriv ut totaloversikt' i hovudmenyen om kva element som manglar om du er usikker.\n");
        } else {

          Legemiddel nyttLegemiddel = null;
          Lege nyLege = null;
          Pasient nyPasient = null;

          System.out.println("Vel lege som skriv ut resepten.");

          int i = 0;
          for(Lege l : leger) {
            System.out.println(i + ". " + l.hentNavn());
            i++;
          }

          System.out.print("> ");
          input = inn.nextLine();

          int j = -1;

          while(j == -1) {
            try {
              j = Integer.parseInt(input);
              leger.hent(j);
            } catch(NumberFormatException | UgyldigListeIndeks e) {
              j = -1;
              System.out.println("\nUgyldig input. Du maa skriva eit gyldig tal for aa naa legen.");
              System.out.print("> ");
              input = inn.nextLine();
            }
          }
          nyLege = leger.hent(j);

          System.out.println("Vel legemiddel resepten er for.");

          i = 0;
          for(Legemiddel lm : legemiddler) {
            System.out.println(i + ". " + lm.hentNavn());
            i++;
          }

          System.out.print("> ");
          input = inn.nextLine();

          j = -1;

          while(j == -1) {
            try {
              j = Integer.parseInt(input);
              legemiddler.hent(j);
            } catch(NumberFormatException | UgyldigListeIndeks e) {
              j = -1;
              System.out.println("\nUgyldig input. Du maa skriva eit gyldig tal for aa naa legemiddelet.");
              System.out.print("> ");
              input = inn.nextLine();
            }
          }
          nyttLegemiddel = legemiddler.hent(j);

          System.out.println("Vel pasienten resepten er for.");

          i = 0;
          for(Pasient p : pasienter) {
            System.out.println(i + ". " + p.hentNavn());
            i++;
          }

          System.out.print("> ");
          input = inn.nextLine();

          j = -1;

          while(j == -1) {
            try {
              j = Integer.parseInt(input);
              pasienter.hent(j);
            } catch(NumberFormatException | UgyldigListeIndeks e) {
              j = -1;
              System.out.println("\nUgyldig input. Du maa skriva eit gyldig tal for aa naa pasienten.");
              System.out.print("> ");
              input = inn.nextLine();
            }
          }
          nyPasient = pasienter.hent(j);

          System.out.println("Kva type resept vil du leggja til i systemet?");
          System.out.println("0. Hvit");
          System.out.println("1.     -P");
          System.out.println("2.     -Militaer");
          System.out.println("3. Blaa");

          System.out.print("> ");
          String nummer = inn.nextLine();
          cont = true;
          while(cont) {
            if(nummer.equals("1")) {
              Resept r = new ReseptHvitP(nyttLegemiddel, nyLege, nyPasient);
              resepter.leggTil(r);
              nyLege.leggTilResept(r);
              nyPasient.leggTilResept(r);
              cont = false;
            } else if(nummer.equals("0") || nummer.equals("2") || nummer.equals("3")) {
              int reseptReit = 0;
              System.out.print("Tal paa reit: ");
              input = inn.nextLine();
              cont = true;
              while(cont) {
                try {
                  reseptReit = Integer.parseInt(input);
                  if(reseptReit <= 0) {
                    System.out.println("Ugyldig input. Maa vera heiltal over 0.");
                    System.out.print("Tal paa reit: ");
                    input = inn.nextLine();
                  } else {
                    cont = false;
                  }
                } catch(NumberFormatException e) {
                  System.out.println("Ugyldig input. Maa vera heiltal over 0.");
                  System.out.print("Tal paa reit: ");
                  input = inn.nextLine();
                }
              }
              if(nummer.equals("0")) {
                Resept r = new ReseptHvit(nyttLegemiddel, nyLege, nyPasient, reseptReit);
                resepter.leggTil(r);
                nyLege.leggTilResept(r);
                nyPasient.leggTilResept(r);
              } else if(nummer.equals("2")) {
                Resept r = new ReseptHvitMilitaer(nyttLegemiddel, nyLege, nyPasient, reseptReit);
                resepter.leggTil(r);
                nyLege.leggTilResept(r);
                nyPasient.leggTilResept(r);
              } else {
                Resept r = new ReseptBlaa(nyttLegemiddel, nyLege, nyPasient, reseptReit);
                resepter.leggTil(r);
                nyLege.leggTilResept(r);
                nyPasient.leggTilResept(r);
              }

            } else {
              System.out.println("Ugyldig input. Maa vera eit av tala i lista.");
              nummer = inn.nextLine();
            }
          }
          System.out.println("\nNy resept lagt til i systemet.\n");
        }
      } else if(input.equals("exit")) {
        System.out.println("\nGaar attende til hovudmenyen.\n");
        cont = false;
      } else {
        System.out.println("\nUgyldig input. Skriv eit av tala knyta til ein av typane i lista under eller skriv 'exit' for aa gaa attende til hovudmenyen.\n");
      }
    }
  }


  //D4
  static void brukResept(Lenkeliste<Pasient> pasienter, Lenkeliste<Resept> resepter) {

    //Om det ikkje er nokre pasienter i systemet
    if(pasienter.stoerrelse() == 0) {
      System.out.println("Det er ikkje nokre pasientar i systemet akkurat no.");
      return;
    }

    Scanner inn = new Scanner(System.in);

    System.out.println("Kva for ein pasient vil du bruka resept til?");
    int i = 0;
    for(Pasient p : pasienter) {
      System.out.println(i + ": " + p.hentNavn());
      i++;
    }
    System.out.print(exit + "> ");
    String input = inn.nextLine();

    int inputIntPasient = -1;

    while(inputIntPasient == -1) {
      if(input.equals("exit")) {
        System.out.println("\nGaar attende til hovudmenyen.\n");
        return;
      }
      try {
        inputIntPasient = Integer.parseInt(input);
        pasienter.hent(inputIntPasient);
      } catch(NumberFormatException | UgyldigListeIndeks e) {
        inputIntPasient = -1;
        System.out.println("\nUgyldig input. Du maa skriva eit gyldig tal for aa naa pasienten.");
        System.out.print("> ");
        input = inn.nextLine();
      }
    }

    if(pasienter.hent(inputIntPasient).hentReseptliste().stoerrelse() == 0) {
      System.out.println(pasienter.hent(inputIntPasient).hentNavn() + " har ikkje nokre gyldige resepter akkurat no.\n");
      return;
    }

    System.out.println();

    System.out.println("Kva legemiddel vil du bruka resept for?");
    i = 0;
    for(Resept r : pasienter.hent(inputIntPasient).hentReseptliste()) {
      System.out.printf("%-18s%s", i + ": " + r.hentLegemiddel().hentNavn(), "(" + r.hentReit() + " reit)\n");
      i++;
    }
    System.out.print(exit + "> ");
    input = inn.nextLine();

    int inputIntResept = -1;

    while(inputIntResept == -1) {
      if(input.equals("exit")) {
        System.out.println("\nGaar attende til hovudmenyen.\n");
        return;
      }
      try {
        inputIntResept = Integer.parseInt(input);
        resepter.hent(inputIntResept);
      } catch(NumberFormatException | UgyldigListeIndeks e) {
        inputIntResept = -1;
        System.out.println("\nUgyldig input. Du maa skriva eit gyldig tal for aa naa reseptene.");
        System.out.print("> ");
        input = inn.nextLine();
      }
    }

    //Om resepten er ugyldig...
    if(!pasienter.hent(inputIntPasient).hentReseptliste().hent(inputIntResept).bruk()) {
      System.out.println("\nUgyldig resept. Det er ikkje fleire reit paa denne resepten for: " + pasienter.hent(inputIntPasient).hentReseptliste().hent(inputIntResept).hentLegemiddel().hentNavn() + "\n");
      return;
    }
    System.out.println();

    //Om resepten er gyldig, sei kor mange reit att
    System.out.println(pasienter.hent(inputIntPasient).hentNavn() + " har " + pasienter.hent(inputIntPasient).hentReseptliste().hent(inputIntResept).hentReit() + " reit att paa " + pasienter.hent(inputIntPasient).hentReseptliste().hent(inputIntResept).hentLegemiddel().hentNavn() + "-resepten.");
    System.out.println();
  }

  public static void skrivUtStatistikk(SortertLenkeliste<Lege> leger, Lenkeliste<Resept> resepter, Lenkeliste<Legemiddel> legemiddler, Lenkeliste<Pasient> pasienter) {
    /* Vert presentert som ein undermeny: Vanedannande legemiddel utskrivne, vanedannande militaer, og narkotikum
     *
     * Skal skriva statistikk om:
     * Totalt tal paa utskrivne resepter paa vanedannande legemiddel
     * Tal paa vanedannande legemiddel utskrivne til militaere
     * Statistikk om mogleg misbruk av narkotikum:
     *  - Alfabetisk namn paa alle leger som har skrive ut minst ein resept paa narkotisk legemiddel,
     *    og talet paa desse per lege.
     *  - Namn paa alle pasientar med minst ein gyldig resept paa slike,
     *    og talet paa desse per pasient.
     */
     Scanner inn = new Scanner(System.in);

     String statistikkMeny = "Kva ynskjer du aa sjaa statistikk for?\n0. Tal paa resepter med vanedannande legemiddel utskrivne i det heile.\n1. Tal paa resepter med vanedannande legemiddel utskrivne paa militaerresept\n2. Statistikk for utskriving av resepter paa narkotiske legemiddel\n";

     System.out.print(statistikkMeny + exit + "> ");
     String input = inn.nextLine();

     boolean cont = true;
     while (cont) {
       if(input.equals("0")) {
         int i = 0;
         for(Resept r : resepter) {
           if(r.hentLegemiddel() instanceof LegemiddelB) {
             i++;
           }
         }
         System.out.println("Tal paa resepter m/vanedannande legemiddel i det heile: " + i + "\n");
         cont = false;
       } else if(input.equals("1")) {
         int i = 0;
         for(Resept r : resepter) {
           if(r.hentLegemiddel() instanceof LegemiddelB && r instanceof ReseptHvitMilitaer) {
             i++;
           }
         }
         System.out.println("Tal paa resepter m/vanedannande legemiddel paa militaerresept: " + i + "\n");
         cont = false;
       } else if(input.equals("2")) {
         //Alfabetisk namn paa alle leger som har skrive ut minst ein resept paa narkotisk legemiddel,
         //og talet paa desse per lege.
         System.out.println("\nDesse legane har skrive ut resept paa narkotiske legemiddel...");
         boolean reseptFinnes = false;
         for(Lege l : leger) {
           int i = 0;
           for(Resept r : l.hentReseptliste()) {
             if(r.hentLegemiddel() instanceof LegemiddelA) {
               i++;
             }
           }
           if(i > 0) {
             System.out.println(l.hentNavn() + ": " + i + " stk");
             reseptFinnes = true;
           }
         }
         System.out.println();
         if(!reseptFinnes) {
           System.out.println("Ingen leger i systemet har skrive ut resept for narkotiske legemiddel.\n");
	 }
         //Namn paa alle pasientar med minst ein gyldig resept paa slike,
         //og talet paa desse per pasient.
         System.out.println("\nDesse pasientane har faat utlevert resept for narkotiske legemiddel...");
         reseptFinnes = false;
         for(Pasient p : pasienter) {
           int i = 0;
           for(Resept r : p.hentReseptliste()) {
             if(r.hentLegemiddel() instanceof LegemiddelA) {
               i++;
             }
           }
           if(i > 0) {
             System.out.println(p.hentNavn() + ": " + i + " stk");
             reseptFinnes = true;
           }
         }
         System.out.println();
         if(!reseptFinnes) {
           System.out.println("Ingen pasientar i systemet har faat utlevert resept for narkotiske legemiddel.\n");
         }
	 cont = false;
       } else if(input.equals("exit")) {
         System.out.println("Gaar attende til hovudmenyen.");
         return;
       } else {
         System.out.println("Ugyldig input. Maa vera tal fraa lista.");
         System.out.print("> ");
         input = inn.nextLine();
       }
     }
  }

  public static boolean inneholder(String s, String kommando) throws RuntimeException {
    //'tall' eller 'bokstav'
    if(kommando == "tall") {
      for(char c : s.toCharArray()) {
        if(Character.isDigit(c)) return true;
      }
      return false;
    } else if(kommando == "bokstaver") {
      try {
        Double.parseDouble(s);
      } catch(NumberFormatException e) {
        return true;
      }
      return false;
    } else {
      throw new RuntimeException("Ugyldig input i metode 'inneholder': " + kommando);
    }
  }
}
