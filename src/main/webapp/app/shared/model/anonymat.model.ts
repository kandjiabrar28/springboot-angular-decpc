import { ICandidat } from 'app/shared/model/candidat.model';
import { ITour } from 'app/shared/model/tour.model';

export interface IAnonymat {
  id?: number;
  numanoymat?: number;
  candidat?: ICandidat;
  tour?: ITour;
}

export class Anonymat implements IAnonymat {
  constructor(public id?: number, public numanoymat?: number, public candidat?: ICandidat, public tour?: ITour) {}
}
