export interface Kinosaal {
  id: number;
  name: string;
  freigegeben: boolean;
  sitzreihenIds: number[];  // Liste der Sitzreihen-IDs (Entkopplung)
}