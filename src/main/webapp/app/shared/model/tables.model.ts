import { Moment } from 'moment';
import { ICandidat } from 'app/shared/model/candidat.model';
import { ISalle } from 'app/shared/model/salle.model';

export interface ITables {
  id?: number;
  numtable?: number;
  dateCreation?: Moment;
  dateModification?: Moment;
  candidat?: ICandidat;
  salle?: ISalle;
}

export class Tables implements ITables {
  constructor(
    public id?: number,
    public numtable?: number,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public candidat?: ICandidat,
    public salle?: ISalle
  ) {}
}
