import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITour } from 'app/shared/model/tour.model';

type EntityResponseType = HttpResponse<ITour>;
type EntityArrayResponseType = HttpResponse<ITour[]>;

@Injectable({ providedIn: 'root' })
export class TourService {
  public resourceUrl = SERVER_API_URL + 'api/tours';

  constructor(protected http: HttpClient) {}

  create(tour: ITour): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tour);
    return this.http
      .post<ITour>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(tour: ITour): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tour);
    return this.http
      .put<ITour>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITour>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITour[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(tour: ITour): ITour {
    const copy: ITour = Object.assign({}, tour, {
      dateCreation: tour.dateCreation && tour.dateCreation.isValid() ? tour.dateCreation.format(DATE_FORMAT) : undefined,
      dateModification: tour.dateModification && tour.dateModification.isValid() ? tour.dateModification.format(DATE_FORMAT) : undefined
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
      res.body.forEach((tour: ITour) => {
        tour.dateCreation = tour.dateCreation ? moment(tour.dateCreation) : undefined;
        tour.dateModification = tour.dateModification ? moment(tour.dateModification) : undefined;
      });
    }
    return res;
  }
}
