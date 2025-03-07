import { Sitzreihe } from "./sitzreihe.model";

export interface Kinosaal {
  id: number;
  name: string;
  sitzreihe: Sitzreihe[];
}
