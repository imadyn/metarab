import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRefAlphabet } from 'app/shared/model/ref-alphabet.model';

type EntityResponseType = HttpResponse<IRefAlphabet>;
type EntityArrayResponseType = HttpResponse<IRefAlphabet[]>;

@Injectable({ providedIn: 'root' })
export class RefAlphabetService {
  public resourceUrl = SERVER_API_URL + 'api/ref-alphabets';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/ref-alphabets';

  constructor(protected http: HttpClient) {}

  create(refAlphabet: IRefAlphabet): Observable<EntityResponseType> {
    return this.http.post<IRefAlphabet>(this.resourceUrl, refAlphabet, { observe: 'response' });
  }

  update(refAlphabet: IRefAlphabet): Observable<EntityResponseType> {
    return this.http.put<IRefAlphabet>(this.resourceUrl, refAlphabet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRefAlphabet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRefAlphabet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRefAlphabet[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
