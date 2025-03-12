export interface Film {
  id: number;
  titel: string;
  dauer: number; // Dauer in Minuten
  fsk: number; // Altersfreigabe (z.B. 6, 12, 16, 18)
}