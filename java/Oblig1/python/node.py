"""
Held klassen Node.

Representerar ein datamaskin i ei rekneklynge.

Tek inn talet paa prosessorar og minnestorleik i GB som parametrar,
og kan returnera desse verdiane.
"""

class Node:
    #Konstruktoeren, tek inn prosessortal og minnestorleik som parametrar
    def __init__(self, antProsessorer, minneGB):
        #sett talet paa prosessorar
        self._antProsessorer = antProsessorer
        #sett minnestorleiken
        self._minneGB = minneGB


    #Returnerar talet paa prosessorar
    def antProsessorer(self):
        return self._antProsessorer

    #Returnerar minnestorleiken
    def minneIGB(self):
        return self._minneGB
