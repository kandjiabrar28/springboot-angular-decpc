import { Moment } from 'moment';
import { IMatiere } from 'app/shared/model/matiere.model';
import { ITAnonym } from 'app/shared/model/t-anonym.model';

export interface ITour {
  id?: number;
  numtour?: number;
  dateCreation?: Moment;
  dateModification?: Moment;
  matiere?: IMatiere;
  tAnonym?: ITAnonym;
}

export class Tour implements ITour {
  constructor(
    public id?: number,
    public numtour?: number,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public matiere?: IMatiere,
    public tAnonym?: ITAnonym
  ) {}
}
