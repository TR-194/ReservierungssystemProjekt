import { KategorieTyp } from './sitzkategorie.model';

export interface Sitzreihe {
  id: number;
  reihenNummer: number;
  kategorieId: KategorieTyp | number;  // Akzeptiert `KategorieTyp` & `number`
  sitzplatzIds: number[];  // Liste der Sitzplätze in dieser Reihe
  kinosaalId: number; // Zugehöriger Kinosaal
}