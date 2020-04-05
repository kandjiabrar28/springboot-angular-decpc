import { IAnonymat } from 'app/shared/model/anonymat.model';
import { IMatiere } from 'app/shared/model/matiere.model';
import { IExamen } from 'app/shared/model/examen.model';

export interface ITour {
  id?: number;
  numtour?: number;
  anonymats?: IAnonymat[];
  matiere?: IMatiere;
  examen?: IExamen;
}

export class Tour implements ITour {
  constructor(
    public id?: number,
    public numtour?: number,
    public anonymats?: IAnonymat[],
    public matiere?: IMatiere,
    public examen?: IExamen
  ) {}
}
