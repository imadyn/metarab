import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BahrCombine, IBahrCombine } from 'app/shared/model/bahr-combine.model';
import { SearchBahrBaitComponent } from 'app/search-bahr-bait/search-bahr-bait/search-bahr-bait.component';
import { RefAlphabetService } from 'app/entities/ref-alphabet';

@Injectable({ providedIn: 'root' })
export class RefAlphabetResolve implements Resolve<IBahrCombine> {
  constructor(private service: RefAlphabetService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBahrCombine> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<IBahrCombine>) => response.ok),
        map((bahrCombine: HttpResponse<IBahrCombine>) => bahrCombine.body)
      );
    }
    return of(new BahrCombine());
  }
}

export const bahrCombineRoute: Routes = [
  {
    path: '',
    component: SearchBahrBaitComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      pageTitle: 'metarabApp.bahrCombine.home.title'
    }
  }
];
