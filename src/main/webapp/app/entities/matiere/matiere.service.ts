import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMatiere } from 'app/shared/model/matiere.model';

type EntityResponseType = HttpResponse<IMatiere>;
type EntityArrayResponseType = HttpResponse<IMatiere[]>;

@Injectable({ providedIn: 'root' })
export class MatiereService {
  public resourceUrl = SERVER_API_URL + 'api/matieres';

  constructor(protected http: HttpClient) {}

  create(matiere: IMatiere): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matiere);
    return this.http
      .post<IMatiere>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(matiere: IMatiere): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(matiere);
    return this.http
      .put<IMatiere>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMatiere>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMatiere[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(matiere: IMatiere): IMatiere {
    const copy: IMatiere = Object.assign({}, matiere, {
      dateCreation: matiere.dateCreation && matiere.dateCreation.isValid() ? matiere.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification:
        matiere.dateModification && matiere.dateModification.isValid() ? matiere.dateModification.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateCreation = res.body.dateCreation ? moment(res.body.dateCreation) : undefined;
      res.body.dateModification = res.body.dateModification ? moment(res.body.dateModification) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((matiere: IMatiere) => {
        matiere.dateCreation = matiere.dateCreation ? moment(matiere.dateCreation) : undefined;
        matiere.dateModification = matiere.dateModification ? moment(matiere.dateModification) : undefined;
      });
    }
    return res;
  }
}
