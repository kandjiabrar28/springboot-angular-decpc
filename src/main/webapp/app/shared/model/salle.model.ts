import { Moment } from 'moment';
import { ITables } from 'app/shared/model/tables.model';
import { IPVSurveillance } from 'app/shared/model/pv-surveillance.model';
import { ISurveillant } from 'app/shared/model/surveillant.model';
import { ICentre } from 'app/shared/model/centre.model';

export interface ISalle {
  id?: number;
  numsalle?: number;
  nomsalle?: string;
  dateCreation?: Moment;
  dateModification?: Moment;
  tables?: ITables[];
  pvsurveillance?: IPVSurveillance;
  surveillants?: ISurveillant[];
  centre?: ICentre;
}

export class Salle implements ISalle {
  constructor(
    public id?: number,
    public numsalle?: number,
    public nomsalle?: string,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public tables?: ITables[],
    public pvsurveillance?: IPVSurveillance,
    public surveillants?: ISurveillant[],
    public centre?: ICentre
  ) {}
}
