export interface Zahlung {
  id: number;
  betrag: number;
  filmtitel: string;
  aufuehrungId: number;
  zahlungsdatum: string;  // ISO-Format: YYYY-MM-DD
  buchungId: number; // Verknüpfung zur Buchung
}
