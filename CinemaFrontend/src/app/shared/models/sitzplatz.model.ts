export interface Sitzplatz {
  id: number;
  nummer: number;  // Sitznummer innerhalb der Reihe
  status: 'frei' | 'reserviert' | 'gebucht';
  sitzreiheId: number; // Sitzreihe enthält die Kategorie
  reservierungId?: number; // Falls reserviert
  buchungId?: number; // Falls gebucht
}