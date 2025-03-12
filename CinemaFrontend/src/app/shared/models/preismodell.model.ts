export interface Preismodell {
  parkettPreis: number;
  logePreis: number;
  logeMitServicePreis: number;
}

// Festgelegte Preismodell-Werte:
export const STANDARD_PREISMODELL: Preismodell = {
  parkettPreis: 8.00,
  logePreis: 10.00,
  logeMitServicePreis: 15.00
};