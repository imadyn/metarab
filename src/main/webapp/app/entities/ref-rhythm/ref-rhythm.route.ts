import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IRefRhythm, RefRhythm } from 'app/shared/model/ref-rhythm.model';
import { RefRhythmService } from './ref-rhythm.service';
import { RefRhythmComponent } from './ref-rhythm.component';
import { RefRhythmDetailComponent } from './ref-rhythm-detail.component';
import { RefRhythmUpdateComponent } from './ref-rhythm-update.component';
import { RefRhythmDeletePopupComponent } from './ref-rhythm-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class RefRhythmResolve implements Resolve<IRefRhythm> {
  constructor(private service: RefRhythmService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRefRhythm> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<RefRhythm>) => response.ok),
        map((refRhythm: HttpResponse<RefRhythm>) => refRhythm.body)
      );
    }
    return of(new RefRhythm());
  }
}

export const refRhythmRoute: Routes = [
  {
    path: '',
    component: RefRhythmComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'metarabApp.refRhythm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RefRhythmDetailComponent,
    resolve: {
      refRhythm: RefRhythmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refRhythm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RefRhythmUpdateComponent,
    resolve: {
      refRhythm: RefRhythmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refRhythm.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RefRhythmUpdateComponent,
    resolve: {
      refRhythm: RefRhythmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refRhythm.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const refRhythmPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RefRhythmDeletePopupComponent,
    resolve: {
      refRhythm: RefRhythmResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'metarabApp.refRhythm.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
