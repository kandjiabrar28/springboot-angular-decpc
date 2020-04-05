import { IPlageCopie } from 'app/shared/model/plage-copie.model';
import { INote } from 'app/shared/model/note.model';
import { IMatiere } from 'app/shared/model/matiere.model';

export interface ICorrecteur {
  id?: number;
  grade?: string;
  prenom?: string;
  nom?: string;
  provenance?: string;
  cni?: string;
  telephone?: string;
  sexe?: string;
  plagecopies?: IPlageCopie[];
  notes?: INote[];
  matieres?: IMatiere[];
}

export class Correcteur implements ICorrecteur {
  constructor(
    public id?: number,
    public grade?: string,
    public prenom?: string,
    public nom?: string,
    public provenance?: string,
    public cni?: string,
    public telephone?: string,
    public sexe?: string,
    public plagecopies?: IPlageCopie[],
    public notes?: INote[],
    public matieres?: IMatiere[]
  ) {}
}
