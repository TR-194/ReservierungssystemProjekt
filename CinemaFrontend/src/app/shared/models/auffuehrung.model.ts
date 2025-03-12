import { Preismodell, STANDARD_PREISMODELL } from './preismodell.model';

export interface Auffuehrung {
  id: number;
  datum: string;  // ISO-Format: YYYY-MM-DD
  uhrzeit: string; // HH:MM
  filmId: number;  // Referenz zur Film-ID statt vollständigem Objekt
  kinosaalId: number;  // Referenz zur Kinosaal-ID
  preismodell: Preismodell;  // Enthält die fixen Preise
}