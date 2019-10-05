import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRefRhythm } from 'app/shared/model/ref-rhythm.model';

type EntityResponseType = HttpResponse<IRefRhythm>;
type EntityArrayResponseType = HttpResponse<IRefRhythm[]>;

@Injectable({ providedIn: 'root' })
export class RefRhythmService {
  public resourceUrl = SERVER_API_URL + 'api/ref-rhythms';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/ref-rhythms';

  constructor(protected http: HttpClient) {}

  create(refRhythm: IRefRhythm): Observable<EntityResponseType> {
    return this.http.post<IRefRhythm>(this.resourceUrl, refRhythm, { observe: 'response' });
  }

  update(refRhythm: IRefRhythm): Observable<EntityResponseType> {
    return this.http.put<IRefRhythm>(this.resourceUrl, refRhythm, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRefRhythm>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRefRhythm[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRefRhythm[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
