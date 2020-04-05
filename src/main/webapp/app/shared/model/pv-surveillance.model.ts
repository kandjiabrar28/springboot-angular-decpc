import { Moment } from 'moment';
import { ISalle } from 'app/shared/model/salle.model';
import { ISurveillant } from 'app/shared/model/surveillant.model';

export interface IPVSurveillance {
  id?: number;
  epreuve?: string;
  heureDeb?: Moment;
  heureFin?: Moment;
  datesurv?: Moment;
  salle?: ISalle;
  surveillant?: ISurveillant;
}

export class PVSurveillance implements IPVSurveillance {
  constructor(
    public id?: number,
    public epreuve?: string,
    public heureDeb?: Moment,
    public heureFin?: Moment,
    public datesurv?: Moment,
    public salle?: ISalle,
    public surveillant?: ISurveillant
  ) {}
}
