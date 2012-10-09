package de.mbohlen.katabankueberweisung.domain

/**
 * Domänenklasse, also "D" im Sinne von DCI. 
 */
class Konto {
    String kontonummer
    Double kontostand
    Double ueberweisungslimit
    
    Kunde inhaber
    
    boolean gehoert (String inhaberName) {
        return inhaber != null && inhaber.name == inhaberName
    }
    
    void erhoeheStand (Double betrag) {
        kontostand += betrag
    }
    
    void erniedrigeStand (Double betrag) {
        kontostand -= betrag
    }
    
}
