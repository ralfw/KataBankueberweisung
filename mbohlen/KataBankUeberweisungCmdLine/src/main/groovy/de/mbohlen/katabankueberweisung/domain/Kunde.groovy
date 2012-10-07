package de.mbohlen.katabankueberweisung.domain

class Kunde {
    String name
    String handyNr
    Double ueberweisungslimit

    List<Konto> konten
}
