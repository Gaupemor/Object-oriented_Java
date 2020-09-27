"""
Held klassen Regneklynge.

Opprettar Node-objekt og Rack-objekt,
held racks i ei liste,
og legg nodane inn i ledige racks.

M/deloppgaave d) les inn fraa fil,
tek inn filnamn som parameter.
"""

#inporterer klassen Node
from node import Node
#Importerer klassen Rack
from rack import Rack

class Regneklynge:
    #Konstruktoeren, tek inn filnamn som parameter
    def __init__(self, filnavn):
        #Opner fila for aa lesa av
        self._innfil = open(filnavn, "r")
        #sett makstalet for noder per rack
        #lik verdien i fyrste linje i innfila
        self._noderPerRack = int(self._innfil.readline())
        #Tom liste som held racks i regneklynga
        self._racks = []

        #Kaller paa privat metode for aa legga til nodar
        self._leggTilNoderFraFil(self._innfil)
        #Lukker fila
        self._innfil.close()

    #Metode som tek inn fil som parameter,
    #Og legg til noder i rekneklynga etter verdiane i fila
    def _leggTilNoderFraFil(self, innfil):
        #Les inn 2. linja i fila
        nyLinje = innfil.readline()
        #Saa lenge linja me las inn ikkje er tom...
        while nyLinje != "":
            #Splitt verdiane i linja v/mellomrom
            parametre = nyLinje.split()
            #For kvar verdi fraa null til 1. verdi i parametre[],
            #d.v.s. talet paa nye noder me skal oppretta etter fila...
            for i in range(int(parametre[0])):
                #Kall metoden i Regneklynge.leggTilNode(),
                #m/dei siste to verdiane i parametre[] som parametrar etter fila...
                self.leggTilNode(int(parametre[2]), int(parametre[1]))
            #Les inn ei ny linje fraa fila
            nyLinje = innfil.readline()

    #Metode som tek inn to int som parametrar,
    #minnestorleik og talet paa prosessorar,
    #Og legg den nye noden inn i eit ledig rack,
    #evt. opprett nytt rack om alle er fulle
    def leggTilNode(self, prosessorerINode, minneGBINode):
        #Opprett nytt Node-objekt m/gjevne verdiar
        node = Node(prosessorerINode, minneGBINode)
        #sett ingen racks er ledige
        ledigeRacks = False
        #For kvart element i lista _racks...
        for i in self._racks:
            #Viss elementet, racket, har ledig plass...
            if i.sjekkLedigPlass():
                #Legg til noden i racket
                i.leggTilNode(node)
                #sett at det er ledige racks
                ledigeRacks = True
        #Om ingen racks er ledige...
        if not ledigeRacks:
            #Lag eit nytt rack m/kapasitet _noderPerRack
            rack = Rack(self._noderPerRack)
            #Legg det nye racket inn i lista _racks
            self._racks.append(rack)
            #Legg noden inn i det nye racket
            rack.leggTilNode(node)

    #Returnerar talet paa prosessorar i rekneklynga
    def antProsessorer(self):
        prosessorer = 0
        #For kvart rack...
        for i in self._racks:
            #Legg til talet paa nodar
            prosessorer += i.antProsessorer()
        #Returner talet
        return prosessorer

    #Returnerar talet paa noder i rekneklynga
    def antNoder(self):
        noder = 0
        #For kvart rack...
        for i in self._racks:
            #Legg til talet paa nodar
            noder += i.antNoder()
        #Returner talet
        return noder

    #Returnerar talet paa racks i rekneklynga
    def antRacks(self):
        #Returnerar lengda av lista _racks
        return len(self._racks)

    #Funksjon som tek inn tal for paakrevd minne som parameter,
    #Og returnerar talet paa nodar som har god nok kapasitet
    def noderMedNokMinne(self, paakrevdMinne):
        noder = 0
        #For kvart racks
        for i in self._racks:
            #Legg til talet paa noder m/ nok minne
            noder += i.noderMedNokMinne(paakrevdMinne)
        #Returner talet
        return noder
