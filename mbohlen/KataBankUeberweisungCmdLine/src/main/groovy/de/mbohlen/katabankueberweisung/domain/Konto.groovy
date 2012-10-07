package de.mbohlen.katabankueberweisung.domain

class Konto {
    String kontonummer
    Double kontostand
    
    Kunde inhaber
    
    boolean gehoert (String inhaberName) {
        return inhaber != null && inhaber.name == inhaberName
    }
}
