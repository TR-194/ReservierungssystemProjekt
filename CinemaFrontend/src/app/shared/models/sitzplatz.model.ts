export interface Sitzplatz {
  id: number;
  nummer: number;  // Sitznummer innerhalb der Reihe
  status: 'frei' | 'reserviert' | 'gebucht';
}