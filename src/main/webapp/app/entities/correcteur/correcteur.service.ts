import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICorrecteur } from 'app/shared/model/correcteur.model';

type EntityResponseType = HttpResponse<ICorrecteur>;
type EntityArrayResponseType = HttpResponse<ICorrecteur[]>;

@Injectable({ providedIn: 'root' })
export class CorrecteurService {
  public resourceUrl = SERVER_API_URL + 'api/correcteurs';

  constructor(protected http: HttpClient) {}

  create(correcteur: ICorrecteur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(correcteur);
    return this.http
      .post<ICorrecteur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(correcteur: ICorrecteur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(correcteur);
    return this.http
      .put<ICorrecteur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICorrecteur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICorrecteur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(correcteur: ICorrecteur): ICorrecteur {
    const copy: ICorrecteur = Object.assign({}, correcteur, {
      datenais: correcteur.datenais && correcteur.datenais.isValid() ? correcteur.datenais.format(DATE_FORMAT) : undefined,
      dateCreation: correcteur.dateCreation && correcteur.dateCreation.isValid() ? correcteur.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification:
        correcteur.dateModification && correcteur.dateModification.isValid() ? correcteur.dateModification.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.datenais = res.body.datenais ? moment(res.body.datenais) : undefined;
      res.body.dateCreation = res.body.dateCreation ? moment(res.body.dateCreation) : undefined;
      res.body.dateModification = res.body.dateModification ? moment(res.body.dateModification) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((correcteur: ICorrecteur) => {
        correcteur.datenais = correcteur.datenais ? moment(correcteur.datenais) : undefined;
        correcteur.dateCreation = correcteur.dateCreation ? moment(correcteur.dateCreation) : undefined;
        correcteur.dateModification = correcteur.dateModification ? moment(correcteur.dateModification) : undefined;
      });
    }
    return res;
  }
}
