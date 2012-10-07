package de.mbohlen.katabankueberweisung.domain

interface KontenRepository {
    void init (URL kontenListenURL)
    Konto findKontoByKontoNummer (String kontoNummer)
}
