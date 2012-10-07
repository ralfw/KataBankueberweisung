package de.mbohlen.katabankueberweisung.cmdline

class SenderValidierenHabit {

    String senderName
    String senderKontoNummer

    boolean istValide() {
        if (senderName == null || senderName.empty || senderKontoNummer == null || senderKontoNummer.empty) {
            return false
        }
        throw new UnsupportedOperationException("Rest der Validierung noch nicht implementiert")
    }

}
