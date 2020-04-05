import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICorrecteur } from 'app/shared/model/correcteur.model';

type EntityResponseType = HttpResponse<ICorrecteur>;
type EntityArrayResponseType = HttpResponse<ICorrecteur[]>;

@Injectable({ providedIn: 'root' })
export class CorrecteurService {
  public resourceUrl = SERVER_API_URL + 'api/correcteurs';

  constructor(protected http: HttpClient) {}

  create(correcteur: ICorrecteur): Observable<EntityResponseType> {
    return this.http.post<ICorrecteur>(this.resourceUrl, correcteur, { observe: 'response' });
  }

  update(correcteur: ICorrecteur): Observable<EntityResponseType> {
    return this.http.put<ICorrecteur>(this.resourceUrl, correcteur, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICorrecteur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICorrecteur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
