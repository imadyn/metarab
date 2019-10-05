import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeTB } from 'app/shared/model/type-tb.model';

type EntityResponseType = HttpResponse<ITypeTB>;
type EntityArrayResponseType = HttpResponse<ITypeTB[]>;

@Injectable({ providedIn: 'root' })
export class TypeTBService {
  public resourceUrl = SERVER_API_URL + 'api/type-tbs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/type-tbs';

  constructor(protected http: HttpClient) {}

  create(typeTB: ITypeTB): Observable<EntityResponseType> {
    return this.http.post<ITypeTB>(this.resourceUrl, typeTB, { observe: 'response' });
  }

  update(typeTB: ITypeTB): Observable<EntityResponseType> {
    return this.http.put<ITypeTB>(this.resourceUrl, typeTB, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITypeTB>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeTB[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITypeTB[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
