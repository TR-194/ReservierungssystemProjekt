export interface Sitzplatz {
  id: number;
  nummer: number;  // Sitznummer innerhalb der Reihe
  status: 'frei' | 'reserviert' | 'gebucht';
  sitzreiheId: number; // Referenz zur Sitzreihe
  reservierungId?: number; // Falls reserviert
  buchungId?: number; // Falls gebucht
}
