import { ICandidat } from 'app/shared/model/candidat.model';
import { ISalle } from 'app/shared/model/salle.model';

export interface ITable {
  id?: number;
  numtable?: number;
  candidat?: ICandidat;
  salle?: ISalle;
}

export class Table implements ITable {
  constructor(public id?: number, public numtable?: number, public candidat?: ICandidat, public salle?: ISalle) {}
}
