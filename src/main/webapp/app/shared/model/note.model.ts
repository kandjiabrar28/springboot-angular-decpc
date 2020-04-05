import { IMatiere } from 'app/shared/model/matiere.model';
import { ICorrecteur } from 'app/shared/model/correcteur.model';
import { ICandidat } from 'app/shared/model/candidat.model';

export interface INote {
  id?: number;
  note?: number;
  matiere?: IMatiere;
  correcteur?: ICorrecteur;
  candidat?: ICandidat;
}

export class Note implements INote {
  constructor(
    public id?: number,
    public note?: number,
    public matiere?: IMatiere,
    public correcteur?: ICorrecteur,
    public candidat?: ICandidat
  ) {}
}
