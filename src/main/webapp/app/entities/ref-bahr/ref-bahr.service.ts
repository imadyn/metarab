import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestMapOption, createRequestOption } from 'app/shared';
import { IRefBahr } from 'app/shared/model/ref-bahr.model';

type EntityResponseType = HttpResponse<IRefBahr>;
type EntityArrayResponseType = HttpResponse<IRefBahr[]>;

@Injectable({ providedIn: 'root' })
export class RefBahrService {
  public resourceUrl = SERVER_API_URL + 'api/ref-bahrs';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/ref-bahrs';
  public resourceSearchAdvancedUrl = SERVER_API_URL + 'api/_searchadvanced/ref-bahrs';

  constructor(protected http: HttpClient) {}

  create(refBahr: IRefBahr): Observable<EntityResponseType> {
    return this.http.post<IRefBahr>(this.resourceUrl, refBahr, { observe: 'response' });
  }

  update(refBahr: IRefBahr): Observable<EntityResponseType> {
    return this.http.put<IRefBahr>(this.resourceUrl, refBahr, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRefBahr>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRefBahr[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRefBahr[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }

  searchAdvanced(req: any, extra?: Map<string, any>): Observable<EntityArrayResponseType> {
    const options = createRequestMapOption(req, extra);
    return this.http.get<IRefBahr[]>(this.resourceSearchAdvancedUrl, { params: options, observe: 'response' });
  }
}
