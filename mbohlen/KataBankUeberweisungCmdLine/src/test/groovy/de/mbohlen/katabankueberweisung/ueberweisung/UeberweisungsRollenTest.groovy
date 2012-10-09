package de.mbohlen.katabankueberweisung.ueberweisung

import java.util.Date

import de.mbohlen.katabankueberweisung.domain.Konto
import de.mbohlen.katabankueberweisung.domain.Kunde
import de.mbohlen.katabankueberweisung.domain.UeberweisungsJournalEintrag
import groovy.util.GroovyTestCase

class UeberweisungsRollenTest extends GroovyTestCase {

    Konto senderKonto
    String empfaengerBlz
    String empfaengerKontoNummer
    String empfaengerName

    @Override
    protected void setUp() throws Exception {
        Kunde opa = new Kunde (name: "Opa Müller", handyNr: "0170-1234567")
        senderKonto = new Konto(
                inhaber: opa, kontonummer: "12345678",
                kontostand: 2000.00, ueberweisungslimit: 3000.00
                )
        empfaengerBlz = "10020000"
        empfaengerKontoNummer = "9876543"
        empfaengerName = "Emil Enkel"
    }

    public void testSollteGeldAbziehenUndKorrektenJournalEintragErgeben() {
        GeldQuelle quelle
        GeldSenke senke

        senderKonto.metaClass.mixin(UeberweisungsGeldQuelle)
        quelle = senderKonto as GeldQuelle

        senke = new UeberweisungsGeldSenke(
                bankLeitzahl: empfaengerBlz,
                kontoNummer: empfaengerKontoNummer,
                empfaenger: empfaengerName
                )

        UeberweisungsJournalEintrag ergebnisEintrag
        UeberweisungsJournalEintrag.metaClass.save << { -> ergebnisEintrag = delegate }

        Double testBetrag = 123.45
        String testText = "Geschenk zu Weihnachten"
        quelle.ueberweisenAn(testBetrag, testText, senke)

        def erwarteterEintrag = [
                    senderKontoNummer: "12345678",
                    ueberweisungsBetrag: testBetrag,
                    ueberweisungsText: testText,
                    empfaengerName: empfaengerName,
                    empfaengerKontonummer: empfaengerKontoNummer,
                    empfaengerBLZ: empfaengerBlz,
                ]

        erwarteterEintrag.keySet().each { key ->
            assert ergebnisEintrag."$key" == erwarteterEintrag.get(key)
        }
        
        assert senderKonto.kontostand == 1876.55
    }
}
