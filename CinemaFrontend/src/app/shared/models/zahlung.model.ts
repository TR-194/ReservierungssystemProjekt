export interface Zahlung {
  id: number;
  betrag: number;
  zahlungsdatum: string;  // ISO-Format: YYYY-MM-DD
  methode: 'Bar'; 
  buchungId: number; // Verknüpfung zur Buchung
}
