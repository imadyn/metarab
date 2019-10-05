import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeTB } from 'app/shared/model/type-tb.model';
import { TypeTBService } from './type-tb.service';
import { TypeTBComponent } from './type-tb.component';
import { TypeTBDetailComponent } from './type-tb-detail.component';
import { TypeTBUpdateComponent } from './type-tb-update.component';
import { TypeTBDeletePopupComponent } from './type-tb-delete-dialog.component';
import { ITypeTB } from 'app/shared/model/type-tb.model';

@Injectable({ providedIn: 'root' })
export class TypeTBResolve implements Resolve<ITypeTB> {
  constructor(private service: TypeTBService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeTB> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TypeTB>) => response.ok),
        map((typeTB: HttpResponse<TypeTB>) => typeTB.body)
      );
    }
    return of(new TypeTB());
  }
}

export const typeTBRoute: Routes = [
  {
    path: '',
    component: TypeTBComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'metarabApp.typeTB.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeTBDetailComponent,
    resolve: {
      typeTB: TypeTBResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.typeTB.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeTBUpdateComponent,
    resolve: {
      typeTB: TypeTBResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.typeTB.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeTBUpdateComponent,
    resolve: {
      typeTB: TypeTBResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.typeTB.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const typeTBPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TypeTBDeletePopupComponent,
    resolve: {
      typeTB: TypeTBResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.typeTB.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
