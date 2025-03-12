export interface Reservierung {
  id: number;
  auffuehrungId: number;
  sitzplatzIds: number[];
  datum: string;  // ISO-Format: YYYY-MM-DD
  uhrzeit: string; // HH:MM
  preis: number;
  name: string;
  email: string;
  status: 'aktiv' | 'storniert' | 'abgelaufen';
}
