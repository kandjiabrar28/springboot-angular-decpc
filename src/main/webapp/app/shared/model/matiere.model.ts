import { Moment } from 'moment';
import { ICorrecteur } from 'app/shared/model/correcteur.model';

export interface IMatiere {
  id?: number;
  libmatiere?: string;
  noteelimin?: number;
  coefficient?: number;
  dateCreation?: Moment;
  dateModification?: Moment;
  correcteurs?: ICorrecteur[];
}

export class Matiere implements IMatiere {
  constructor(
    public id?: number,
    public libmatiere?: string,
    public noteelimin?: number,
    public coefficient?: number,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public correcteurs?: ICorrecteur[]
  ) {}
}
