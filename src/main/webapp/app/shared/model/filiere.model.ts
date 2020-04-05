import { ISpecialite } from 'app/shared/model/specialite.model';

export interface IFiliere {
  id?: number;
  libfiliere?: string;
  specialites?: ISpecialite[];
}

export class Filiere implements IFiliere {
  constructor(public id?: number, public libfiliere?: string, public specialites?: ISpecialite[]) {}
}
