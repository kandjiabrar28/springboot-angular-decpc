import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISurveillant } from 'app/shared/model/surveillant.model';

type EntityResponseType = HttpResponse<ISurveillant>;
type EntityArrayResponseType = HttpResponse<ISurveillant[]>;

@Injectable({ providedIn: 'root' })
export class SurveillantService {
  public resourceUrl = SERVER_API_URL + 'api/surveillants';

  constructor(protected http: HttpClient) {}

  create(surveillant: ISurveillant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(surveillant);
    return this.http
      .post<ISurveillant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(surveillant: ISurveillant): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(surveillant);
    return this.http
      .put<ISurveillant>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISurveillant>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISurveillant[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(surveillant: ISurveillant): ISurveillant {
    const copy: ISurveillant = Object.assign({}, surveillant, {
      datenais: surveillant.datenais && surveillant.datenais.isValid() ? surveillant.datenais.format(DATE_FORMAT) : undefined,
      dateCreation:
        surveillant.dateCreation && surveillant.dateCreation.isValid() ? surveillant.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification:
        surveillant.dateModification && surveillant.dateModification.isValid()
          ? surveillant.dateModification.format(DATE_FORMAT)
          : undefined
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
      res.body.forEach((surveillant: ISurveillant) => {
        surveillant.datenais = surveillant.datenais ? moment(surveillant.datenais) : undefined;
        surveillant.dateCreation = surveillant.dateCreation ? moment(surveillant.dateCreation) : undefined;
        surveillant.dateModification = surveillant.dateModification ? moment(surveillant.dateModification) : undefined;
      });
    }
    return res;
  }
}
