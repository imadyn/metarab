import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRefAlphabet, RefAlphabet } from 'app/shared/model/ref-alphabet.model';
import { RefAlphabetService } from './ref-alphabet.service';
import { RefAlphabetComponent } from './ref-alphabet.component';
import { RefAlphabetDetailComponent } from './ref-alphabet-detail.component';
import { RefAlphabetUpdateComponent } from './ref-alphabet-update.component';
import { RefAlphabetDeletePopupComponent } from './ref-alphabet-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class RefAlphabetResolve implements Resolve<IRefAlphabet> {
  constructor(private service: RefAlphabetService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRefAlphabet> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RefAlphabet>) => response.ok),
        map((refAlphabet: HttpResponse<RefAlphabet>) => refAlphabet.body)
      );
    }
    return of(new RefAlphabet());
  }
}

export const refAlphabetRoute: Routes = [
  {
    path: '',
    component: RefAlphabetComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'metarabApp.refAlphabet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RefAlphabetDetailComponent,
    resolve: {
      refAlphabet: RefAlphabetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refAlphabet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RefAlphabetUpdateComponent,
    resolve: {
      refAlphabet: RefAlphabetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refAlphabet.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RefAlphabetUpdateComponent,
    resolve: {
      refAlphabet: RefAlphabetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refAlphabet.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const refAlphabetPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RefAlphabetDeletePopupComponent,
    resolve: {
      refAlphabet: RefAlphabetResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refAlphabet.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
