package de.mbohlen.katabankueberweisung.cmdline

import de.mbohlen.katabankueberweisung.domain.KontenRepository
import de.mbohlen.katabankueberweisung.domain.KontenRepositoryTest
import de.mbohlen.katabankueberweisung.domain.Konto
import groovy.util.GroovyTestCase

class SenderValidierenHabitTest extends GroovyTestCase {

    SenderValidierenHabit svh

    @Override
    protected void setUp() throws Exception {
        svh = new SenderValidierenHabit()
        svh.kontenRepository = KontenRepositoryTest.erzeugeInitialisiertesTestKontenRepository()
    }

    public final void testSollteLeereEingabenErkennen() {
        assert !svh.istValide()

        svh.senderName = "Paul Zettel"
        assert !svh.istValide()

        svh.senderKontoNummer = "56788765"
        assert svh.istValide()
    }
}
