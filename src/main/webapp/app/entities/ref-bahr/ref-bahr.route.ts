import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RefBahr } from 'app/shared/model/ref-bahr.model';
import { RefBahrService } from './ref-bahr.service';
import { RefBahrComponent } from './ref-bahr.component';
import { RefBahrDetailComponent } from './ref-bahr-detail.component';
import { RefBahrUpdateComponent } from './ref-bahr-update.component';
import { RefBahrDeletePopupComponent } from './ref-bahr-delete-dialog.component';
import { IRefBahr } from 'app/shared/model/ref-bahr.model';

@Injectable({ providedIn: 'root' })
export class RefBahrResolve implements Resolve<IRefBahr> {
  constructor(private service: RefBahrService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRefBahr> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RefBahr>) => response.ok),
        map((refBahr: HttpResponse<RefBahr>) => refBahr.body)
      );
    }
    return of(new RefBahr());
  }
}

export const refBahrRoute: Routes = [
  {
    path: '',
    component: RefBahrComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'metarabApp.refBahr.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RefBahrDetailComponent,
    resolve: {
      refBahr: RefBahrResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refBahr.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RefBahrUpdateComponent,
    resolve: {
      refBahr: RefBahrResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refBahr.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RefBahrUpdateComponent,
    resolve: {
      refBahr: RefBahrResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refBahr.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const refBahrPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RefBahrDeletePopupComponent,
    resolve: {
      refBahr: RefBahrResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refBahr.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
