"""
Hovudprogrammet.
For aa sjekka om klassene fungerar som dei skal.
M/deloppgaave d) les inn fraa fil.

Opprettar eit Regneklynge-objekt, abel.

Skriv ut talet paa nodar etter minnestorleik,
talet paa prosessorar,
og talet paa racks i rekneklynga.
"""

#Importerer klassen Regneklynge
from regneklynge import Regneklynge

#Metoden for hovudprogramet
def main():
    print("\nOblig 8\n")

    #Filnamnet regneklynga skal lesa av fraa
    innfil = "regneklynge.txt"
    #Opprettar regneklynga abel m/filnamn som parameter
    abel = Regneklynge(innfil)

    #Skriv ut verdiane for abel
    print("Noder m/ minst... ")
    print("   32 GB: ", abel.noderMedNokMinne(32))
    print("   64 GB: ", abel.noderMedNokMinne(64))
    print("   128 GB: ", abel.noderMedNokMinne(128))
    print()
    print("Antall prosessorer: ", abel.antProsessorer())
    print("Antall racks: ", abel.antRacks())

#Koeyrer hovudprogrammet
main()
