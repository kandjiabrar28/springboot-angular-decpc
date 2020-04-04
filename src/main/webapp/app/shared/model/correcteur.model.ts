import { Moment } from 'moment';
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
  datenais?: Moment;
  dateCreation?: Moment;
  dateModification?: Moment;
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
    public datenais?: Moment,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public matieres?: IMatiere[]
  ) {}
}
