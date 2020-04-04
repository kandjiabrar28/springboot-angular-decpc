import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlage } from 'app/shared/model/plage.model';

type EntityResponseType = HttpResponse<IPlage>;
type EntityArrayResponseType = HttpResponse<IPlage[]>;

@Injectable({ providedIn: 'root' })
export class PlageService {
  public resourceUrl = SERVER_API_URL + 'api/plages';

  constructor(protected http: HttpClient) {}

  create(plage: IPlage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plage);
    return this.http
      .post<IPlage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(plage: IPlage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(plage);
    return this.http
      .put<IPlage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPlage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPlage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(plage: IPlage): IPlage {
    const copy: IPlage = Object.assign({}, plage, {
      dateCreation: plage.dateCreation && plage.dateCreation.isValid() ? plage.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification: plage.dateModification && plage.dateModification.isValid() ? plage.dateModification.format(DATE_FORMAT) : undefined
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
      res.body.forEach((plage: IPlage) => {
        plage.dateCreation = plage.dateCreation ? moment(plage.dateCreation) : undefined;
        plage.dateModification = plage.dateModification ? moment(plage.dateModification) : undefined;
      });
    }
    return res;
  }
}
