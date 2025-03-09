import { Film } from './film.model';
import { Kinosaal } from './kinosaal.model';
import { Preismodell } from './preismodell.model';

export interface Auffuehrung {
  id: number;
  datum: string;  // ISO-Format: YYYY-MM-DD
  uhrzeit: string; // HH:MM
  film: Film;
  kinosaal: Kinosaal;
  preismodell: Preismodell; // Add Preismodell
}
