import { Kunde } from './kunde.model';
import { Zahlung } from './zahlung.model';
import { Sitzplatz } from './sitzplatz.model';
import { Auffuehrung } from './auffuehrung.model';

export interface Buchung {
  id?: number;              // Optional für bestehende Buchungen
  kunde: Kunde;             // Der Kunde, der die Buchung durchführt
  auffuehrungId: number;    // ID der gebuchten Aufführung
  sitzplaetze: number[];    // Liste der Sitzplatz-IDs
  zahlung: Zahlung;         // Zahlungsdetails
}
