import { ICorrecteur } from 'app/shared/model/correcteur.model';

export interface IPlageCopie {
  id?: number;
  plage?: string;
  typecopie?: string;
  nombrecopie?: number;
  correcteur?: ICorrecteur;
}

export class PlageCopie implements IPlageCopie {
  constructor(
    public id?: number,
    public plage?: string,
    public typecopie?: string,
    public nombrecopie?: number,
    public correcteur?: ICorrecteur
  ) {}
}
