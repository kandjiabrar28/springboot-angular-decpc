import { INote } from 'app/shared/model/note.model';
import { ITour } from 'app/shared/model/tour.model';
import { ICorrecteur } from 'app/shared/model/correcteur.model';

export interface IMatiere {
  id?: number;
  libmatiere?: string;
  noteelimin?: number;
  coefficient?: number;
  notes?: INote[];
  tours?: ITour[];
  correcteurs?: ICorrecteur[];
}

export class Matiere implements IMatiere {
  constructor(
    public id?: number,
    public libmatiere?: string,
    public noteelimin?: number,
    public coefficient?: number,
    public notes?: INote[],
    public tours?: ITour[],
    public correcteurs?: ICorrecteur[]
  ) {}
}
