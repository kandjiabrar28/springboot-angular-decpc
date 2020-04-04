import { Moment } from 'moment';

export interface IPVSurveillance {
  id?: number;
  epreuve?: string;
  heureDeb?: Moment;
  heureFin?: Moment;
  datesurv?: Moment;
  dateCreation?: Moment;
  dateModification?: Moment;
}

export class PVSurveillance implements IPVSurveillance {
  constructor(
    public id?: number,
    public epreuve?: string,
    public heureDeb?: Moment,
    public heureFin?: Moment,
    public datesurv?: Moment,
    public dateCreation?: Moment,
    public dateModification?: Moment
  ) {}
}
