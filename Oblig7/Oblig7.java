import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.*;
import javafx.event.*;
import javafx.geometry.Pos;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;


public class Oblig7 extends Application {
  Window screen;
  Pane pane;
  Labyrint l;
  FileChooser velgFil = new FileChooser();
  Text info;
  GridPane rutenett;
  //Viser om ei av rutene er aktive for aa sjaa etter utveg
  HvitKnapp aktivRute = null;
  //Held noverande fargeindeks, for aa halda same farge om ein bytter labyrint
  int fargeIndeks;

  String startInfo = "Trykk paa en aapen rute for aa se utveien!\n\n";

  MediaView mv;

  class HvitKnappBehandler implements EventHandler<ActionEvent> {
    HvitKnapp denneKnappen;

    HvitKnappBehandler(HvitKnapp k, GridPane rutenett) {
      denneKnappen = k;
    }

    @Override
    public void handle(ActionEvent e) {
      //Finn kortaste rute fra posisjonen i labyrinten om det finst ein utveg
      boolean[][] ruterILosning = null;
      try {
        String vei = l.finnKortesteUtveiFra(GridPane.getColumnIndex(denneKnappen), GridPane.getRowIndex(denneKnappen)).hent(0);
        ruterILosning = losningStringTilTabell(vei, l.hentAntKolonner(), l.hentAntRader());
        int antVeier = l.finnUtveiFra(GridPane.getColumnIndex(denneKnappen), GridPane.getRowIndex(denneKnappen)).stoerrelse();
        info.setText("Fant utvei! Den korteste har " + l.antStegIKortesteUtvei(GridPane.getColumnIndex(denneKnappen), GridPane.getRowIndex(denneKnappen)) + " trinn.\n"
        + "Fant til sammen " + antVeier + " utveier.\n");
        aktivRute = denneKnappen;
        //System.out.println(rutenett.getChildren().indexOf(aktivRute));
      } catch (UgyldigListeIndeks u) {
        info.setText("Ingen utvei!\n\n");
        aktivRute = null;
      } finally {
        //Teikner opp den nye vegen
        tegnOppVei(ruterILosning, denneKnappen);
      }
    }
  }

  //Kvit knapp er klikkbar m/HvitKnappBehandler
  class HvitKnapp extends Button {
    HvitKnapp() {
      super(" ");
      setOnAction(new HvitKnappBehandler(this, rutenett));
    }

    public void marker(String merke) {
      setText(merke);
    }
  }

  //Svart knapp er ikkje klikkbar
  class SortKnapp extends Button {
    SortKnapp() {
      super(" ");
    }
  }

  @Override
  public void start(Stage stage) throws FileNotFoundException {
    velgFil.setTitle("Aapne labyrintfil");
    VBox ordning = new VBox(10);
    HBox ekstraKnapper = new HBox(10);
    HBox rutenettBoks = new HBox();
    info = new Text(startInfo);

    //Opprett labyrint i vindauget
    rutenett = nyttRutenett(stage);

    //Opprett knapp for aa bytta labyrint
    Button nyLabyrintKnapp = new Button("Velg ny labyrint");
    //Style
    nyLabyrintKnapp.setStyle("-fx-background-color: GainsBoro");
    nyLabyrintKnapp.setOnMouseEntered(e -> nyLabyrintKnapp.setStyle("-fx-background-color: White"));
    nyLabyrintKnapp.setOnMouseExited(e -> nyLabyrintKnapp.setStyle("-fx-background-color: GainsBoro"));
    nyLabyrintKnapp.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        try {
          //Oppretter nytt rutenett og skifter ut gamle
          rutenett = nyttRutenett(stage);
          rutenettBoks.getChildren().remove(0);
          rutenettBoks.getChildren().add(rutenett);
          //Holder paa fargekombinasjonen
          endreFarger(fargeIndeks);
          info.setText(startInfo);
          screen.sizeToScene();
          stage.centerOnScreen();
        } catch(FileNotFoundException f) {}
      }
    });



    //Opprett knapp for aa visa fleire utveger
    Button flereUtveierKnapp = new Button("Vis en annen utvei");
    //Style
    flereUtveierKnapp.setStyle("-fx-background-color: GainsBoro");
    flereUtveierKnapp.setOnMouseEntered(e -> flereUtveierKnapp.setStyle("-fx-background-color: White"));
    flereUtveierKnapp.setOnMouseExited(e -> flereUtveierKnapp.setStyle("-fx-background-color: GainsBoro"));
    flereUtveierKnapp.setOnAction(new EventHandler<ActionEvent>() {
      HvitKnapp sjekkRute = null;
      Liste<String> veier = null;
      boolean[][] ruterILosning = null;
      int indeks = 0;

      public void handle(ActionEvent e) {
        //Om ingen aktiv rute, hopp ut
        if(aktivRute == null) return;
        //Henter vegene til ruta om dei ikkje er funnen enno
        if(aktivRute != sjekkRute) {
          veier = l.finnUtveiFra(GridPane.getColumnIndex(aktivRute), GridPane.getRowIndex(aktivRute));
          String kortesteVei = l.finnKortesteUtveiFra(GridPane.getColumnIndex(aktivRute), GridPane.getRowIndex(aktivRute)).hent(0);
          //Saann at kortaste veg kjem til slutt igjen
          for(int i = 0; i < veier.stoerrelse(); i++) {
            if(kortesteVei.equals(veier.hent(i))) {
              veier.fjern(i);
              i = veier.stoerrelse();
            }
          } veier.leggTil(kortesteVei);
          sjekkRute = aktivRute;
          indeks = 0;
          ruterILosning = losningStringTilTabell(veier.hent(0), l.hentAntKolonner(), l.hentAntRader());
        } else {
          //Gar videre og i loop
          indeks++;
          if(indeks >= veier.stoerrelse()) indeks = 0;
          ruterILosning = losningStringTilTabell(veier.hent(indeks), l.hentAntKolonner(), l.hentAntRader());
        }
        //Viser losningsnr og teikner opp ruta
        info.setText(info.getText().split("Losning nr: ")[0] + "Losning nr: " + Integer.toString(indeks + 1));
        tegnOppVei(ruterILosning, aktivRute);
      }
    });

    Button endreFarger = new Button("Endre farger");
    //Style
    endreFarger.setStyle("-fx-background-color: GainsBoro");
    endreFarger.setOnMouseEntered(e -> endreFarger.setStyle("-fx-background-color: White"));
    endreFarger.setOnMouseExited(e -> endreFarger.setStyle("-fx-background-color: GainsBoro"));
    endreFarger.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        int valgIndeks = -1;
        ArrayList<String> valg = new ArrayList<String>();
        valg.add("Original");
        valg.add("Svart paa hvitt");
        valg.add("Hvitt paa svart");
        valg.add("Undervannstema");
        valg.add("Vulkan");
        valg.add("Blomstereng");
        valg.add("Tetris");
        valg.add("Skogen");
        valg.add("I skyene");
        ChoiceDialog<String> dialog = new ChoiceDialog<String>("Original", valg);
        dialog.setTitle("Endre farger");
        dialog.setHeaderText("Velg en ny fargekombinasjon for bakgrunn og vegger");
        dialog.setContentText("Velg her:");
        Optional<String> resultat = dialog.showAndWait();
        if(resultat.isPresent()) {
          valgIndeks = valg.indexOf(resultat.get());
          endreFarger(valgIndeks);
        } else return;
      }
    });

    Button spillKnapp = new Button("Trivia");
    //Style
    spillKnapp.setStyle("-fx-background-color: GainsBoro");
    spillKnapp.setOnMouseEntered(e -> spillKnapp.setStyle("-fx-background-color: White"));
    spillKnapp.setOnMouseExited(e -> spillKnapp.setStyle("-fx-background-color: GainsBoro"));
    spillKnapp.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        hentSporsmaal();
      }
    });

    //Hbox held dei to ekstra knappane,
    //(1) ny labyrint, og (2) vis fleire utvegar
    ekstraKnapper.getChildren().add(nyLabyrintKnapp);
    ekstraKnapper.getChildren().add(flereUtveierKnapp);
    ekstraKnapper.getChildren().add(endreFarger);
    //ekstraKnapper.getChildren().add(spillKnapp);
    ekstraKnapper.setAlignment(Pos.TOP_CENTER);

    //Legg til element i 'ordning' for aa ordna elementa loddrett
    //STOERRE MELLOMROM MELLOM ELEMENTA I 'ordning'??

    rutenettBoks.getChildren().add(rutenett);
    rutenettBoks.setAlignment(Pos.TOP_CENTER);

    //Rutenett boer ALLTID vera element 0 pga. endring av labyrint seinare
    ordning.getChildren().add(rutenettBoks);
    ordning.getChildren().add(info);
    ordning.getChildren().add(ekstraKnapper);

    //For lydfil
    mv = new MediaView();

    //Klargjoer vindauge
    pane = new Pane();
    //pane.setPrefSize(500, 500);
    pane.getChildren().add(ordning);
    Scene scene = new Scene(pane);
    stage.setTitle("Labyrint");
    stage.setScene(scene);
    endreFarger(0);
    MediaPlayer mp = new MediaPlayer(new Media(new File("Sequoyah.mp3").toURI().toString()));
    mp.setAutoPlay(true);
    mv.setMediaPlayer(mp);
    screen = stage.getScene().getWindow();
    stage.show();

  }

  //Hjelpemetode for aa opne ny labyrintfil
  private void aapneFil(Stage stage) throws FileNotFoundException {
    File labyrintFil = null;
    boolean cont = true;
    //Medan labyrintFil ikkje er vald
    while(cont) {
      //Sjekk om fila er gyldig, opne
      //Prov aa laga labyrint av han, fang opp unntak
      labyrintFil = velgFil.showOpenDialog(stage);
      //Prov aa laga labyrint med den valde fila
      try {
        l = Labyrint.lesFraFil(labyrintFil);
        cont = false;
      //Om brukaren kryssa seg ut, retunerar FileChooser null
      } catch(NullPointerException n) {
        //Lukk filvelingsvindauge, klarerer labyrinten no for det om
        cont = false;
        info.setText(startInfo);
        //Om ikkje finst nokon labyrint fraa foer, drep heile programmet
        //(om i byrjinga av programmet)
        if(l == null) System.exit(0);
      //Om det vert ein annan feil, gjev melding om at det er vald feil fil
    } catch(Exception e) {
        //ERROR message - 'Ugyldig fil'
        Alert melding = new Alert(AlertType.ERROR);
        melding.setTitle("Feilmelding");
        melding.setHeaderText("Ugyldig fil valgt");
        melding.setContentText("Du har valgt en fil som ikke er gyldig.\n"
        + "Velg en fil som inneholder rett formatering\nfor å opprette en labyrint.");
        melding.showAndWait();
      }
    }
  }

  //Hjelpemetode for aa finna ruta
  private GridPane nyttRutenett(Stage stage) throws FileNotFoundException {
    aapneFil(stage);
    GridPane nyttRutenett = new GridPane();
    //default
    int knappeStorrelse = 30;
    int tekstStorrelse = 12;
    if((l.hentAntRader() > 30) || (l.hentAntKolonner() > 60)) {
      knappeStorrelse = 13; tekstStorrelse = 5;
    }
    for(int i = 0; i < l.hentAntRader(); i++) {
      for(int j = 0; j < l.hentAntKolonner(); j++) {
        Button knapp;
        if(l.hentRute(j, i).tilTegn() == '.') knapp = new HvitKnapp();
        else knapp = new SortKnapp();
        knapp.setFont(new Font(tekstStorrelse));
        knapp.setPrefSize(knappeStorrelse, knappeStorrelse);
        knapp.setMaxSize(knappeStorrelse, knappeStorrelse);
        knapp.setMinSize(knappeStorrelse, knappeStorrelse);
        nyttRutenett.add(knapp, j, i);
      }
    }
    return nyttRutenett;
  }

  //Hjelpemetode for aa teikna opp veg
  private void tegnOppVei(boolean[][] ruterILosning, HvitKnapp startRute) {
    //Visk ut
    for(Node n : rutenett.getChildren()) {
      try {
        ((HvitKnapp)n).marker(" ");
      } catch(Exception ex) {}
    }

    //Marker paa nytt
    for(Node n : rutenett.getChildren()) {
      try {
        int i = GridPane.getRowIndex(n);
        int j = GridPane.getColumnIndex(n);
        if(ruterILosning[i][j]) ((HvitKnapp)n).marker("x");
      } catch(Exception ex) {}
    }
    ((HvitKnapp)startRute).marker("X");
  }

  //Medgjeven metode
  private boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
      boolean[][] losning = new boolean[hoyde][bredde];
      java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
      java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
      while (m.find()) {
          int x = Integer.parseInt(m.group(1));
          int y = Integer.parseInt(m.group(2));
          losning[y][x] = true;
      }
      return losning;
  }

  private void endreFarger(int i) {
    boolean ensfarget = true;
    boolean byttSang = true;
    if(fargeIndeks == i) byttSang = false;
    else fargeIndeks = i;
    String bakgrunn = "-fx-background-color: ";
    String vegger = "-fx-background-color: ";
    Color tekstFarge = Color.BLACK;
    String lydfil = "";
    //BEHOLDE FARGE ETTER VALGT NY LABYRINT??
    switch(i) {
      //Original (labyrint som hekkar)
      case 0:   bakgrunn += "PaleGoldenRod";
                vegger += "DarkOliveGreen";
                tekstFarge = Color.DARKSLATEGREY;
                info.setFill(Color.DARKSLATEGREY);
                lydfil = "Labyrint.mp3";
                break;
      //Svart paa Kvitt
      case 1:   bakgrunn += "White";
                vegger += "Black";
                tekstFarge = Color.BLACK;
                info.setFill(Color.BLACK);
                lydfil = "SPK.mp3";
                break;
      //Kvitt paa Svart
      case 2:   bakgrunn += "Black";
                vegger += "White";
                tekstFarge = Color.WHITE;
                info.setFill(Color.WHITE);
                lydfil = "KPS.mp3";
                break;
      //Undervatntema
      case 3:   bakgrunn += "LightSeaGreen";
                vegger += "DarkSlateBlue";
                tekstFarge = Color.NAVY;
                info.setFill(Color.NAVY);
                lydfil = "Vann.mp3";
                break;
      //Vulkan
      case 4:   bakgrunn += "LightGrey";
                vegger = "Orange, OrangeRed, DarkOrange, DarkRed, Red";
                tekstFarge = Color.DARKSLATEGREY;
                info.setFill(Color.DARKSLATEGREY);
                ensfarget = false;
                lydfil = "Vulkan.mp3";
                break;
      //Blomstereng
      case 5:   bakgrunn += "PaleGreen";
                vegger = "MediumOrchid, Pink, MediumTurquoise, Gold, GainsBoro";
                tekstFarge = Color.CADETBLUE;
                info.setFill(Color.CADETBLUE);
                ensfarget = false;
                lydfil = "Eng.mp3";
                break;
      //Tetris
      case 6:   bakgrunn += "Black";
                vegger = "Red, Yellow, Magenta, Blue, Cyan, Green, Orange";
                tekstFarge = Color.DARKTURQUOISE;
                info.setFill(Color.DARKTURQUOISE);
                ensfarget = false;
                //Tetris
                lydfil = "Tetris.mp3";
                break;
      //Skogen
      case 7:   bakgrunn += "DarkSeaGreen";
                vegger = "Green, LightGreen, LimeGreen, MediumSeaGreen, OliveDrab";
                tekstFarge = Color.SIENNA;
                info.setFill(Color.SIENNA);
                ensfarget = false;
                //Sequoyah
                lydfil = "Sequoyah.mp3";
                break;
      //I skyene
      case 8:   bakgrunn += "FloralWhite";
                vegger += "LightSteelBlue";
                tekstFarge = Color.SLATEGREY;
                info.setFill(Color.SLATEGREY);
                //Stratos lookout
                lydfil = "Himmel.mp3";
                break;
      default:  break;
    }
    if(byttSang){
      mv.getMediaPlayer().stop();
      MediaPlayer mp = new MediaPlayer(new Media(new File(lydfil).toURI().toString()));
      mp.setAutoPlay(true);
      mv.setMediaPlayer(mp);
    } pane.setStyle(bakgrunn);
    String[] farger = vegger.split(", ");
    for(Node b : rutenett.getChildren()) {
      if(b instanceof HvitKnapp) {
        ((HvitKnapp)b).setStyle(bakgrunn);
        ((HvitKnapp)b).setTextFill(tekstFarge);
      } else {
        if(ensfarget) ((SortKnapp)b).setStyle(vegger);
        else {
          int rnd = new Random().nextInt(farger.length);
          ((SortKnapp)b).setStyle("-fx-background-color: " + farger[rnd]);
        }
      }
    }
  }

  private void hentSporsmaal() {
    ArrayList<String> valg = new ArrayList<String>();
    boolean ingenSporsmaal = false;
    //Tre mulige sporsmaal hver
    int rnd = new Random().nextInt(3);
    //Sporsmaal
    String s = "";
    //Feilmelding
    String f = "";
    int r = 0;
    //Labyrinter
    if(fargeIndeks == 0) {
      switch(rnd) {
        case 0:     s = "Hvilket spraak kommer order 'labyrint' fraa?";
                    valg.add("Gresk");
                    valg.add("Latin");
                    valg.add("Sanskrit");
                    r = 0;
                    f = "Ordet kommer fra gresk, \n trolig sammensetning av ordene \nlabrys, dobbeløks, og inthos, sted";
                    break;
        case 1:     s = "Hva er forskjell mellom de engelske ordene  'maze' og 'labyrinth'?";
                    valg.add("Ingenting");
                    valg.add("Antall utganger");
                    valg.add("Om de er naturlige eller menneskeskapte formasjoner.");
                    r = 1;
                    f = "'Maze' kan ha flere utganger, en 'labyrinth' har bare en.";
                    break;
        case 2:     s = "Hvor er den stoerste labyrinten i verden?";
                    valg.add("Italia");
                    valg.add("Hellas");
                    valg.add("Buckingham Palace");
                    r = 0;
                    f = "Masone-labyrinten i Italia stod ferdig i 2015,\nog er rundt 2000m² stor.";
                    break;
        default:    break;
      }
    } if(fargeIndeks == 1) {
      ingenSporsmaal = true;
    } if(fargeIndeks == 2) {
      ingenSporsmaal = true;
    //Vann og havet
    } if(fargeIndeks == 3) {
      switch(rnd) {
        case 0:     valg.add("Under 7km");
                    valg.add("Mellom 7- og 10-tusen meter.");
                    valg.add("Over 1 mil");
                    s = "Hvor dyp er Marianergropa?";
                    f = "Det dypeste kjente punktet er 10'994 m.u.h.,\ni Challengerdypet.";
                    r = 2;
                    break;
        case 1:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        case 2:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        default:    break;
      }
    //Vulkan
    } if(fargeIndeks == 4) {
      switch(rnd) {
        case 0:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        case 1:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        case 2:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        default:    break;
      }
    //Flora
    } if(fargeIndeks == 5) {
      switch(rnd) {
        case 0:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        case 1:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        case 2:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        default:    break;
      }
    //Spill
    } if(fargeIndeks == 6) {
      switch(rnd) {
        case 0:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        case 1:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        case 2:     valg.add("");
                    valg.add("");
                    valg.add("");
                    break;
        default:    break;
      //Fauna
      } if(fargeIndeks == 7) {
        switch(rnd) {
          case 0:     s = "";
                      valg.add("");
                      valg.add("");
                      valg.add("");
                      break;
          case 1:     s = "";
                      valg.add("");
                      valg.add("");
                      valg.add("");
                      break;
          case 2:     s = "";
                      valg.add("");
                      valg.add("");
                      valg.add("");
                      break;
          default:    break;
        }
      }
    }
    ChoiceDialog<String> d = new ChoiceDialog<String>(valg.get(0), valg);
    d.setTitle("Trivia");
    d.setContentText("Velg et alternativ:");
    d.setHeaderText(s);
    Optional<String> result = d.showAndWait();
    if(result.isPresent()) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Svar");
      if(result.get().equals(valg.get(r))) {
        alert.setHeaderText("Riktig!");
      } else {
        alert.setHeaderText("Feil!");
        alert.setContentText(f);
      }
    }
  }

  //Koyr applikasjonen
  public static void main(String[] args) {
    Application.launch(args);
  }
}
