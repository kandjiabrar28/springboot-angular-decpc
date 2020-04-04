import { Moment } from 'moment';
import { ITour } from 'app/shared/model/tour.model';
import { ICandidat } from 'app/shared/model/candidat.model';

export interface ITAnonym {
  id?: number;
  numanonym?: string;
  dateCreation?: Moment;
  dateModification?: Moment;
  tours?: ITour[];
  candidat?: ICandidat;
}

export class TAnonym implements ITAnonym {
  constructor(
    public id?: number,
    public numanonym?: string,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public tours?: ITour[],
    public candidat?: ICandidat
  ) {}
}
