import { ISpecialite } from 'app/shared/model/specialite.model';

export interface INiveau {
  id?: number;
  libniveau?: string;
  specialites?: ISpecialite[];
}

export class Niveau implements INiveau {
  constructor(public id?: number, public libniveau?: string, public specialites?: ISpecialite[]) {}
}
