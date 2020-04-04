import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICentre } from 'app/shared/model/centre.model';

type EntityResponseType = HttpResponse<ICentre>;
type EntityArrayResponseType = HttpResponse<ICentre[]>;

@Injectable({ providedIn: 'root' })
export class CentreService {
  public resourceUrl = SERVER_API_URL + 'api/centres';

  constructor(protected http: HttpClient) {}

  create(centre: ICentre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(centre);
    return this.http
      .post<ICentre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(centre: ICentre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(centre);
    return this.http
      .put<ICentre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICentre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICentre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(centre: ICentre): ICentre {
    const copy: ICentre = Object.assign({}, centre, {
      dateCreation: centre.dateCreation && centre.dateCreation.isValid() ? centre.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification:
        centre.dateModification && centre.dateModification.isValid() ? centre.dateModification.format(DATE_FORMAT) : undefined
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
      res.body.forEach((centre: ICentre) => {
        centre.dateCreation = centre.dateCreation ? moment(centre.dateCreation) : undefined;
        centre.dateModification = centre.dateModification ? moment(centre.dateModification) : undefined;
      });
    }
    return res;
  }
}
