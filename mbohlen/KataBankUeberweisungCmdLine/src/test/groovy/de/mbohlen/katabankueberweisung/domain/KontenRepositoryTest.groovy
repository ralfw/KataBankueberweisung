package de.mbohlen.katabankueberweisung.domain;

import groovy.util.GroovyTestCase;

class KontenRepositoryTest extends GroovyTestCase {

    KontenRepository kr
    
    @Override
    protected void setUp() throws Exception {
        kr = new KontenRepository()
        kr.init(KontenRepositoryTest.class.getResource("KontenRepositoryTestKontenliste.txt"))
    }
    
    public final void testSollteEinzelnesKontoFinden() {
        Konto k = kr.findKontoByKontoNummer("11223344")
        assert k != null
        assert k.inhaber.name == "Maria Traum"
        
        k = kr.findKontoByKontoNummer("illegaleNummer")
        assert k == null
    }

}
