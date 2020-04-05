import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnonymat } from 'app/shared/model/anonymat.model';

type EntityResponseType = HttpResponse<IAnonymat>;
type EntityArrayResponseType = HttpResponse<IAnonymat[]>;

@Injectable({ providedIn: 'root' })
export class AnonymatService {
  public resourceUrl = SERVER_API_URL + 'api/anonymats';

  constructor(protected http: HttpClient) {}

  create(anonymat: IAnonymat): Observable<EntityResponseType> {
    return this.http.post<IAnonymat>(this.resourceUrl, anonymat, { observe: 'response' });
  }

  update(anonymat: IAnonymat): Observable<EntityResponseType> {
    return this.http.put<IAnonymat>(this.resourceUrl, anonymat, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnonymat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnonymat[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
