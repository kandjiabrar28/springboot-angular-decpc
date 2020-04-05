import { Moment } from 'moment';
import { IPVSurveillance } from 'app/shared/model/pv-surveillance.model';

export interface ISurveillant {
  id?: number;
  fonction?: string;
  prenom?: string;
  nom?: string;
  provenance?: string;
  cni?: string;
  telephone?: string;
  sexe?: string;
  datenais?: Moment;
  pvsurveillances?: IPVSurveillance[];
}

export class Surveillant implements ISurveillant {
  constructor(
    public id?: number,
    public fonction?: string,
    public prenom?: string,
    public nom?: string,
    public provenance?: string,
    public cni?: string,
    public telephone?: string,
    public sexe?: string,
    public datenais?: Moment,
    public pvsurveillances?: IPVSurveillance[]
  ) {}
}
