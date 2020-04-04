import { Moment } from 'moment';
import { IMatiere } from 'app/shared/model/matiere.model';
import { ICandidat } from 'app/shared/model/candidat.model';

export interface INote {
  id?: number;
  note?: string;
  dateCreation?: Moment;
  dateModification?: Moment;
  matiere?: IMatiere;
  candidat?: ICandidat;
}

export class Note implements INote {
  constructor(
    public id?: number,
    public note?: string,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public matiere?: IMatiere,
    public candidat?: ICandidat
  ) {}
}
