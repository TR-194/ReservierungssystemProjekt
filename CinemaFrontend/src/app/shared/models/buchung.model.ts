export interface Buchung {
  id?: number;  // Optional für neue Buchungen
  email: string;
  auffuehrungId: number;  // ID der gebuchten Aufführung
  sitzplatzIds: number[];  // Liste der Sitzplatz-IDs
  zahlungId?: number;  // Falls Zahlung separat gespeichert wird
}