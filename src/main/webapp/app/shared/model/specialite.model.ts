import { ISession } from 'app/shared/model/session.model';
import { INiveau } from 'app/shared/model/niveau.model';
import { IFiliere } from 'app/shared/model/filiere.model';

export interface ISpecialite {
  id?: number;
  libspec?: string;
  sessions?: ISession[];
  niveaus?: INiveau[];
  filiere?: IFiliere;
}

export class Specialite implements ISpecialite {
  constructor(
    public id?: number,
    public libspec?: string,
    public sessions?: ISession[],
    public niveaus?: INiveau[],
    public filiere?: IFiliere
  ) {}
}
