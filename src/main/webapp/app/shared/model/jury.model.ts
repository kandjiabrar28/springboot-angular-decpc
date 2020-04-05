import { IExamen } from 'app/shared/model/examen.model';

export interface IJury {
  id?: number;
  numjury?: number;
  examen?: IExamen[];
}

export class Jury implements IJury {
  constructor(public id?: number, public numjury?: number, public examen?: IExamen[]) {}
}
