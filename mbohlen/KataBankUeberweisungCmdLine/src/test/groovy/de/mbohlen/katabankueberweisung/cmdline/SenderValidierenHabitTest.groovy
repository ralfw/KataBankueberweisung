package de.mbohlen.katabankueberweisung.cmdline

import groovy.util.GroovyTestCase

class SenderValidierenHabitTest extends GroovyTestCase {

    public final void testSollteLeereEingabenErkennen() {
        SenderValidierenHabit svh = new SenderValidierenHabit()
        assert !svh.istValide()

        svh.senderName = "John Doe"
        assert !svh.istValide()

        svh.senderKontoNummer = "123456"
        try {
            assert !svh.istValide()
        }
        catch (Exception e) {
            // das war zu erwarten
            return
        }
        fail("Hier hätte eine Exception fliegen müssen!")
    }
}
