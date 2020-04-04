import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISalle } from 'app/shared/model/salle.model';

type EntityResponseType = HttpResponse<ISalle>;
type EntityArrayResponseType = HttpResponse<ISalle[]>;

@Injectable({ providedIn: 'root' })
export class SalleService {
  public resourceUrl = SERVER_API_URL + 'api/salles';

  constructor(protected http: HttpClient) {}

  create(salle: ISalle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salle);
    return this.http
      .post<ISalle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(salle: ISalle): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(salle);
    return this.http
      .put<ISalle>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISalle>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISalle[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(salle: ISalle): ISalle {
    const copy: ISalle = Object.assign({}, salle, {
      dateCreation: salle.dateCreation && salle.dateCreation.isValid() ? salle.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification: salle.dateModification && salle.dateModification.isValid() ? salle.dateModification.format(DATE_FORMAT) : undefined
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
      res.body.forEach((salle: ISalle) => {
        salle.dateCreation = salle.dateCreation ? moment(salle.dateCreation) : undefined;
        salle.dateModification = salle.dateModification ? moment(salle.dateModification) : undefined;
      });
    }
    return res;
  }
}
