import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IRefBahr } from 'app/shared/model/ref-bahr.model';
import { AccountService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { RefBahrService } from './ref-bahr.service';

@Component({
  selector: 'jhi-ref-bahr',
  templateUrl: './ref-bahr.component.html'
})
export class RefBahrComponent implements OnInit, OnDestroy {
  criteres?: Map<string, any>;
  currentAccount: any;
  refBahrs: IRefBahr[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  currentSearch: string;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected refBahrService: RefBahrService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ? this.activatedRoute.snapshot.params['search'] : '';
  }

  loadAll() {
    /* if (this.currentSearch && this.currentSearch !== '') {
      this.refBahrService
        .search({
          page: this.page - 1,
          query: this.currentSearch,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IRefBahr[]>) => this.paginateRefBahrs(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
      return;
    }
    this.refBahrService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IRefBahr[]>) => this.paginateRefBahrs(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      ); */

    this.searchAdvanced();
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/ref-bahr'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        search: this.currentSearch,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.currentSearch = '';
    this.router.navigate([
      '/ref-bahr',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  search(query) {
    if (!query) {
      return this.clear();
    }
    this.page = 0;
    this.currentSearch = query;
    this.router.navigate([
      '/ref-bahr',
      {
        search: this.currentSearch,
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  searchAdvanced(): void {
    this.criteres = new Map();
    this.criteres.set('isRoot', true);
    this.loadPageAdvanced(1);
  }

  loadPageAdvanced(page?: number): void {
    const pageToLoad: number = page || this.page;
    if (!this.criteres) return;

    this.refBahrService
      .searchAdvanced(
        {
          page: pageToLoad - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        },
        this.criteres
      )
      .subscribe(
        (res: HttpResponse<IRefBahr[]>) => this.paginateRefBahrs(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    //this.loadAll();

    this.searchAdvanced();

    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInRefBahrs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRefBahr) {
    return item.id;
  }

  registerChangeInRefBahrs() {
    this.eventSubscriber = this.eventManager.subscribe('refBahrListModification', response => this.loadAll());
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRefBahrs(data: IRefBahr[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.refBahrs = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
