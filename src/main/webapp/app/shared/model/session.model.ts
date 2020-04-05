import { Moment } from 'moment';
import { IExamen } from 'app/shared/model/examen.model';
import { ISpecialite } from 'app/shared/model/specialite.model';

export interface ISession {
  id?: number;
  dateSession?: Moment;
  examen?: IExamen;
  specialite?: ISpecialite;
}

export class Session implements ISession {
  constructor(public id?: number, public dateSession?: Moment, public examen?: IExamen, public specialite?: ISpecialite) {}
}
