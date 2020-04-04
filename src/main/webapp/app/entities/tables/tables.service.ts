import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITables } from 'app/shared/model/tables.model';

type EntityResponseType = HttpResponse<ITables>;
type EntityArrayResponseType = HttpResponse<ITables[]>;

@Injectable({ providedIn: 'root' })
export class TablesService {
  public resourceUrl = SERVER_API_URL + 'api/tables';

  constructor(protected http: HttpClient) {}

  create(tables: ITables): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tables);
    return this.http
      .post<ITables>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tables: ITables): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tables);
    return this.http
      .put<ITables>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITables>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITables[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tables: ITables): ITables {
    const copy: ITables = Object.assign({}, tables, {
      dateCreation: tables.dateCreation && tables.dateCreation.isValid() ? tables.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification:
        tables.dateModification && tables.dateModification.isValid() ? tables.dateModification.format(DATE_FORMAT) : undefined
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
      res.body.forEach((tables: ITables) => {
        tables.dateCreation = tables.dateCreation ? moment(tables.dateCreation) : undefined;
        tables.dateModification = tables.dateModification ? moment(tables.dateModification) : undefined;
      });
    }
    return res;
  }
}
