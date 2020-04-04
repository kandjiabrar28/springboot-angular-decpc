import { Moment } from 'moment';
import { ITAnonym } from 'app/shared/model/t-anonym.model';
import { INote } from 'app/shared/model/note.model';

export interface ICandidat {
  id?: number;
  prenom?: string;
  nom?: string;
  provenance?: string;
  cni?: string;
  telephone?: string;
  sexe?: string;
  datenais?: Moment;
  niveau?: string;
  dateCreation?: Moment;
  dateModification?: Moment;
  tanonyms?: ITAnonym[];
  notes?: INote[];
}

export class Candidat implements ICandidat {
  constructor(
    public id?: number,
    public prenom?: string,
    public nom?: string,
    public provenance?: string,
    public cni?: string,
    public telephone?: string,
    public sexe?: string,
    public datenais?: Moment,
    public niveau?: string,
    public dateCreation?: Moment,
    public dateModification?: Moment,
    public tanonyms?: ITAnonym[],
    public notes?: INote[]
  ) {}
}
