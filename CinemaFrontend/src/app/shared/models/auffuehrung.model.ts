export interface Auffuehrung {
  id: number;
  datum: string;  // ISO-Format: YYYY-MM-DD
  uhrzeit: string; // HH:MM
  filmId: number;  // Referenz zur Film-ID statt vollständigem Objekt
  kinosaalId: number;  // Referenz zur Kinosaal-ID
  preismodellId: number; // Falls Preismodell separat gespeichert wird
}