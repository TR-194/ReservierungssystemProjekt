export enum KategorieTyp {
  LOGE_MIT_SERVICE = 'Loge mit Service',
  LOGE = 'Loge',
  PARKETT = 'Parkett'
}

export interface Sitzkategorie {
  id: number;
  name: KategorieTyp;
  preis: number;
}