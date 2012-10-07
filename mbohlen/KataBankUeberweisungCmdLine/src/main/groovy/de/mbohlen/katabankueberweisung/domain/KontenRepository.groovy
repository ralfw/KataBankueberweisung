package de.mbohlen.katabankueberweisung.domain

interface KontenRepository {
    Konto findKontoByKontoNummer (String kontoNummer)
}
