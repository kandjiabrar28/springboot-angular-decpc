import { Moment } from 'moment';
import { IExamen } from 'app/shared/model/examen.model';

export interface IJury {
  id?: number;
  numjury?: string;
  dateCreation?: Moment;
  dateModification?: Moment;
  examen?: IExamen[];
}

export class Jury implements IJury {
  constructor(
    public id?: number,
    public numjury?: string,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public examen?: IExamen[]
  ) {}
}
