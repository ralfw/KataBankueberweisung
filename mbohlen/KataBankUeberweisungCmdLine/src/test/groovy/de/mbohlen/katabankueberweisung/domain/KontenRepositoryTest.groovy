package de.mbohlen.katabankueberweisung.domain;

import groovy.util.GroovyTestCase;

class KontenRepositoryTest extends GroovyTestCase {

    KontenRepository kr

    /**
     * Erzeugt ein fertig initialisiertes KontenRepository, nur für Testzwecke.
     * @return Test-Kundenrepository
     */
    public static KontenRepository erzeugeInitialisiertesTestKontenRepository() {
        KontenRepository kr = new KontenRepositoryImpl()
        kr.init(KontenRepositoryTest.class.getResource("KontenRepositoryTestKontenliste.txt"))
        return kr
    }   
    
    @Override
    protected void setUp() throws Exception {
        kr = erzeugeInitialisiertesTestKontenRepository()
    }
    
    public final void testSollteEinzelnesKontoFinden() {
        Konto k = kr.findKontoByKontoNummer("11223344")
        assert k != null
        assert k.inhaber.name == "Maria Traum"
        
        k = kr.findKontoByKontoNummer("illegaleNummer")
        assert k == null
    }

}
