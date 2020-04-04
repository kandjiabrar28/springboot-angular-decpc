import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IJury } from 'app/shared/model/jury.model';

type EntityResponseType = HttpResponse<IJury>;
type EntityArrayResponseType = HttpResponse<IJury[]>;

@Injectable({ providedIn: 'root' })
export class JuryService {
  public resourceUrl = SERVER_API_URL + 'api/juries';

  constructor(protected http: HttpClient) {}

  create(jury: IJury): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jury);
    return this.http
      .post<IJury>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(jury: IJury): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jury);
    return this.http
      .put<IJury>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IJury>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IJury[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(jury: IJury): IJury {
    const copy: IJury = Object.assign({}, jury, {
      dateCreation: jury.dateCreation && jury.dateCreation.isValid() ? jury.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification: jury.dateModification && jury.dateModification.isValid() ? jury.dateModification.format(DATE_FORMAT) : undefined
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
      res.body.forEach((jury: IJury) => {
        jury.dateCreation = jury.dateCreation ? moment(jury.dateCreation) : undefined;
        jury.dateModification = jury.dateModification ? moment(jury.dateModification) : undefined;
      });
    }
    return res;
  }
}
