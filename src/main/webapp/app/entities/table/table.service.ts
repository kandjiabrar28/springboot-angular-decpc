import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITable } from 'app/shared/model/table.model';

type EntityResponseType = HttpResponse<ITable>;
type EntityArrayResponseType = HttpResponse<ITable[]>;

@Injectable({ providedIn: 'root' })
export class TableService {
  public resourceUrl = SERVER_API_URL + 'api/tables';

  constructor(protected http: HttpClient) {}

  create(table: ITable): Observable<EntityResponseType> {
    return this.http.post<ITable>(this.resourceUrl, table, { observe: 'response' });
  }

  update(table: ITable): Observable<EntityResponseType> {
    return this.http.put<ITable>(this.resourceUrl, table, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITable>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITable[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
