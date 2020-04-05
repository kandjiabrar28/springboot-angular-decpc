import { Moment } from 'moment';
import { ISession } from 'app/shared/model/session.model';
import { ITour } from 'app/shared/model/tour.model';
import { IJury } from 'app/shared/model/jury.model';

export interface IExamen {
  id?: number;
  nomexamen?: string;
  dateExamen?: Moment;
  sessions?: ISession[];
  tours?: ITour[];
  juries?: IJury[];
}

export class Examen implements IExamen {
  constructor(
    public id?: number,
    public nomexamen?: string,
    public dateExamen?: Moment,
    public sessions?: ISession[],
    public tours?: ITour[],
    public juries?: IJury[]
  ) {}
}
