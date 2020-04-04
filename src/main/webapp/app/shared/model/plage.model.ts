import { Moment } from 'moment';
import { ICorrecteur } from 'app/shared/model/correcteur.model';

export interface IPlage {
  id?: number;
  plage?: string;
  typecopie?: string;
  nombrecopie?: number;
  dateCreation?: Moment;
  dateModification?: Moment;
  correcteur?: ICorrecteur;
}

export class Plage implements IPlage {
  constructor(
    public id?: number,
    public plage?: string,
    public typecopie?: string,
    public nombrecopie?: number,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public correcteur?: ICorrecteur
  ) {}
}
