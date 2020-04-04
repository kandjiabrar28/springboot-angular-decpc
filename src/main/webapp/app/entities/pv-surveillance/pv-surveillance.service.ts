import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPVSurveillance } from 'app/shared/model/pv-surveillance.model';

type EntityResponseType = HttpResponse<IPVSurveillance>;
type EntityArrayResponseType = HttpResponse<IPVSurveillance[]>;

@Injectable({ providedIn: 'root' })
export class PVSurveillanceService {
  public resourceUrl = SERVER_API_URL + 'api/pv-surveillances';

  constructor(protected http: HttpClient) {}

  create(pVSurveillance: IPVSurveillance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pVSurveillance);
    return this.http
      .post<IPVSurveillance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pVSurveillance: IPVSurveillance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pVSurveillance);
    return this.http
      .put<IPVSurveillance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPVSurveillance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPVSurveillance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pVSurveillance: IPVSurveillance): IPVSurveillance {
    const copy: IPVSurveillance = Object.assign({}, pVSurveillance, {
      heureDeb: pVSurveillance.heureDeb && pVSurveillance.heureDeb.isValid() ? pVSurveillance.heureDeb.toJSON() : undefined,
      heureFin: pVSurveillance.heureFin && pVSurveillance.heureFin.isValid() ? pVSurveillance.heureFin.toJSON() : undefined,
      datesurv: pVSurveillance.datesurv && pVSurveillance.datesurv.isValid() ? pVSurveillance.datesurv.format(DATE_FORMAT) : undefined,
      dateCreation:
        pVSurveillance.dateCreation && pVSurveillance.dateCreation.isValid() ? pVSurveillance.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification:
        pVSurveillance.dateModification && pVSurveillance.dateModification.isValid()
          ? pVSurveillance.dateModification.format(DATE_FORMAT)
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.heureDeb = res.body.heureDeb ? moment(res.body.heureDeb) : undefined;
      res.body.heureFin = res.body.heureFin ? moment(res.body.heureFin) : undefined;
      res.body.datesurv = res.body.datesurv ? moment(res.body.datesurv) : undefined;
      res.body.dateCreation = res.body.dateCreation ? moment(res.body.dateCreation) : undefined;
      res.body.dateModification = res.body.dateModification ? moment(res.body.dateModification) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pVSurveillance: IPVSurveillance) => {
        pVSurveillance.heureDeb = pVSurveillance.heureDeb ? moment(pVSurveillance.heureDeb) : undefined;
        pVSurveillance.heureFin = pVSurveillance.heureFin ? moment(pVSurveillance.heureFin) : undefined;
        pVSurveillance.datesurv = pVSurveillance.datesurv ? moment(pVSurveillance.datesurv) : undefined;
        pVSurveillance.dateCreation = pVSurveillance.dateCreation ? moment(pVSurveillance.dateCreation) : undefined;
        pVSurveillance.dateModification = pVSurveillance.dateModification ? moment(pVSurveillance.dateModification) : undefined;
      });
    }
    return res;
  }
}
