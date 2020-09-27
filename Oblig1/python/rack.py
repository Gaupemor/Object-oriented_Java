"""
Held klassen Rack.

Held opp til eit gjeve tal nodar i ei liste.
Klassa hentar i hovudsak informasjon fraa nodane ho held.
"""

class Rack:
    #Konstruktoeren, tek inn makstalet paa noder instansen kan helda
    def __init__(self, maksAntNoder):
        #sett minnekapasiteten til det gjevne talet
        self._maksAntNoder = maksAntNoder
        #Lista som held nodane i racket
        self._noder = []

    #Funksjon som sjekker om det er ledig plass i racket,
    #Returnerar ein sanningsverdi
    def sjekkLedigPlass(self):
        #sett ikkje ledig plass
        ledigPlass = False
        #Viss minnekapasiteten er stoerre enn talet paa nodar...
        if self._maksAntNoder > len(self._noder):
            #Er det ledig plass i racket
            ledigPlass = True
        #Returner om det er ledig plass eller ikkje
        return ledigPlass

    #Metode som legg til node i dette racket
    def leggTilNode(self, nyNode):
        self._noder.append(nyNode)

    #Funksjon som returnerar talet paa prosessorar i racket
    def antProsessorer(self):
        prosessorer = 0
        #For kvar node i racket...
        for i in self._noder:
            #Legg til talet paa prosessorar
            prosessorer += i.antProsessorer()
        #Returner talet
        return prosessorer

    #Returnerar talet paa nodar i racket
    def antNoder(self):
        #Returner talet paa element i lista _noder
        return len(self._noder)

    #Funksjon som tek inn paakrevd minnestorleik som parameter,
    #Og returnerar talet paa nodar som har god nok kapasitet
    def noderMedNokMinne(self, paakrevdMinne):
        noder = 0
        #For kvar node i racket...
        for i in self._noder:
            #Viss minnestorleiken i noden er stoerre eller erlik paakrevd minnestorleik...
            if i.minneIGB() >= paakrevdMinne:
                #Legg til eit tal i teljaren
                noder += 1
        #Returner talet
        return noder
