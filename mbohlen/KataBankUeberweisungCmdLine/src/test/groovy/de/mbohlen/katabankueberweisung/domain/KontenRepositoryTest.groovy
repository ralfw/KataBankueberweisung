package de.mbohlen.katabankueberweisung.domain;

import groovy.util.GroovyTestCase;

class KontenRepositoryTest extends GroovyTestCase {

    KontenRepository kr

    /**
     * Erzeugt ein fertig initialisiertes KontenRepository, nur für Testzwecke.
     * @return Test-Kundenrepository
     */
    public static KontenRepository erzeugeInitialisiertesTestKontenRepository() {
        KontenRepositoryImpl kr = new KontenRepositoryImpl()
        kr.init(KontenRepositoryTest.class.getResource("KontenRepositoryTestKontenliste.txt"))
        return kr
    }   
    
    @Override
    protected void setUp() throws Exception {
        kr = erzeugeInitialisiertesTestKontenRepository()
    }
    
    public final void testSollteEinzelnesKontoFinden() {
        Konto k = Konto.findByKontoNummer("11223344")
        assert k != null
        assert k.inhaber.name == "Maria Traum"
        
        k = Konto.findByKontoNummer("illegaleNummer")
        assert k == null
    }
    
    public void testSollteEinzelnenKundenFinden() {
        Kunde k = Kunde.findByName ("Peter Mustermann")
        assert k != null
        assert k.name == "Peter Mustermann"
    }

}
