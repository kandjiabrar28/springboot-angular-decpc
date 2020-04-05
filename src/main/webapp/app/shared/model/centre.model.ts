import { ISalle } from 'app/shared/model/salle.model';

export interface ICentre {
  id?: number;
  nom?: string;
  region?: string;
  departement?: string;
  telephone?: string;
  email?: string;
  salles?: ISalle[];
}

export class Centre implements ICentre {
  constructor(
    public id?: number,
    public nom?: string,
    public region?: string,
    public departement?: string,
    public telephone?: string,
    public email?: string,
    public salles?: ISalle[]
  ) {}
}
