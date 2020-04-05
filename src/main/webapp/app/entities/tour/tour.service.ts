import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<ITour>(this.resourceUrl, tour, { observe: 'response' });
  }

  update(tour: ITour): Observable<EntityResponseType> {
    return this.http.put<ITour>(this.resourceUrl, tour, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITour>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITour[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
