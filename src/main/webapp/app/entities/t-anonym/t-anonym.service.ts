import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITAnonym } from 'app/shared/model/t-anonym.model';

type EntityResponseType = HttpResponse<ITAnonym>;
type EntityArrayResponseType = HttpResponse<ITAnonym[]>;

@Injectable({ providedIn: 'root' })
export class TAnonymService {
  public resourceUrl = SERVER_API_URL + 'api/t-anonyms';

  constructor(protected http: HttpClient) {}

  create(tAnonym: ITAnonym): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tAnonym);
    return this.http
      .post<ITAnonym>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tAnonym: ITAnonym): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tAnonym);
    return this.http
      .put<ITAnonym>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITAnonym>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITAnonym[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tAnonym: ITAnonym): ITAnonym {
    const copy: ITAnonym = Object.assign({}, tAnonym, {
      dateCreation: tAnonym.dateCreation && tAnonym.dateCreation.isValid() ? tAnonym.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification:
        tAnonym.dateModification && tAnonym.dateModification.isValid() ? tAnonym.dateModification.format(DATE_FORMAT) : undefined
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
      res.body.forEach((tAnonym: ITAnonym) => {
        tAnonym.dateCreation = tAnonym.dateCreation ? moment(tAnonym.dateCreation) : undefined;
        tAnonym.dateModification = tAnonym.dateModification ? moment(tAnonym.dateModification) : undefined;
      });
    }
    return res;
  }
}
