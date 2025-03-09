import { Film } from './film.model';
import { Auffuehrung } from './auffuehrung.model';
import { Kinosaal } from './kinosaal.model';

export interface Reservierung {
  id: number;
  kundeId: number;
  film: Film;
  auffuehrung: Auffuehrung;
  kinosaal: Kinosaal;
  sitzplaetze: string[];
  datum: Date;
  uhrzeit: string;
  preis: number;
}
