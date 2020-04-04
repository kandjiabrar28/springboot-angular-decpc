import { Moment } from 'moment';
import { ITour } from 'app/shared/model/tour.model';
import { IJury } from 'app/shared/model/jury.model';

export interface IExamen {
  id?: number;
  nomexamen?: string;
  dateCreation?: Moment;
  dateModification?: Moment;
  tour?: ITour;
  juries?: IJury[];
}

export class Examen implements IExamen {
  constructor(
    public id?: number,
    public nomexamen?: string,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public tour?: ITour,
    public juries?: IJury[]
  ) {}
}
