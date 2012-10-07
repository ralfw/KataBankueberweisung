package de.mbohlen.katabankueberweisung.cmdline

import de.mbohlen.katabankueberweisung.domain.KontenRepositoryTest
import de.mbohlen.katabankueberweisung.domain.Konto
import groovy.util.GroovyTestCase

class SenderValidierenHabitTest extends GroovyTestCase {

    GeldUeberweisenContext svh

    @Override
    protected void setUp() throws Exception {
        KontenRepositoryTest.erzeugeInitialisiertesTestKontenRepository()
        svh = new GeldUeberweisenContext()
    }

    public final void testSollteLeereEingabenErkennen() {
        assert !svh.senderIstValide()

        svh.senderName = "Paul Zettel"
        assert !svh.senderIstValide()

        svh.senderKontoNummer = "56788765"
        assert svh.senderIstValide()
    }
}
