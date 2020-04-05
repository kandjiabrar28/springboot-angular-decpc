import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPlageCopie } from 'app/shared/model/plage-copie.model';

type EntityResponseType = HttpResponse<IPlageCopie>;
type EntityArrayResponseType = HttpResponse<IPlageCopie[]>;

@Injectable({ providedIn: 'root' })
export class PlageCopieService {
  public resourceUrl = SERVER_API_URL + 'api/plage-copies';

  constructor(protected http: HttpClient) {}

  create(plageCopie: IPlageCopie): Observable<EntityResponseType> {
    return this.http.post<IPlageCopie>(this.resourceUrl, plageCopie, { observe: 'response' });
  }

  update(plageCopie: IPlageCopie): Observable<EntityResponseType> {
    return this.http.put<IPlageCopie>(this.resourceUrl, plageCopie, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlageCopie>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlageCopie[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
