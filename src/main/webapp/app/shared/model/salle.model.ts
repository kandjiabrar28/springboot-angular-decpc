import { ITable } from 'app/shared/model/table.model';
import { IPVSurveillance } from 'app/shared/model/pv-surveillance.model';
import { ICentre } from 'app/shared/model/centre.model';

export interface ISalle {
  id?: number;
  numsalle?: number;
  nomsalle?: string;
  tables?: ITable[];
  pvsurveillances?: IPVSurveillance[];
  centre?: ICentre;
}

export class Salle implements ISalle {
  constructor(
    public id?: number,
    public numsalle?: number,
    public nomsalle?: string,
    public tables?: ITable[],
    public pvsurveillances?: IPVSurveillance[],
    public centre?: ICentre
  ) {}
}
