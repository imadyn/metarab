import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { IBahrBaitSearchCriteria } from 'app/shared/model/bahr-combine.model';

type EntityArrayResponseType = HttpResponse<String[]>;

@Injectable({
  providedIn: 'root'
})
export class SearchBahrBaitService {
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/ref-bahrs/baitbykey';

  constructor(private http: HttpClient) {
  }

  search(criteres?: IBahrBaitSearchCriteria): Observable<EntityArrayResponseType> {
    return this.http.post<String[]>(this.resourceSearchUrl, criteres, { observe: 'response' });
  }
}
