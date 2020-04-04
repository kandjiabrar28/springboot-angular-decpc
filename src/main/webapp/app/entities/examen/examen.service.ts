import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IExamen } from 'app/shared/model/examen.model';

type EntityResponseType = HttpResponse<IExamen>;
type EntityArrayResponseType = HttpResponse<IExamen[]>;

@Injectable({ providedIn: 'root' })
export class ExamenService {
  public resourceUrl = SERVER_API_URL + 'api/examen';

  constructor(protected http: HttpClient) {}

  create(examen: IExamen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examen);
    return this.http
      .post<IExamen>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(examen: IExamen): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(examen);
    return this.http
      .put<IExamen>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IExamen>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IExamen[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(examen: IExamen): IExamen {
    const copy: IExamen = Object.assign({}, examen, {
      dateCreation: examen.dateCreation && examen.dateCreation.isValid() ? examen.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification:
        examen.dateModification && examen.dateModification.isValid() ? examen.dateModification.format(DATE_FORMAT) : undefined
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
      res.body.forEach((examen: IExamen) => {
        examen.dateCreation = examen.dateCreation ? moment(examen.dateCreation) : undefined;
        examen.dateModification = examen.dateModification ? moment(examen.dateModification) : undefined;
      });
    }
    return res;
  }
}
