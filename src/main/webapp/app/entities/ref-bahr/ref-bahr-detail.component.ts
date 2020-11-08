import { TypeTBService } from './../type-tb/type-tb.service';
import { ITypeTB, Type } from './../../shared/model/type-tb.model';
import { IRefRhythm } from 'app/shared/model/ref-rhythm.model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRefBahr } from 'app/shared/model/ref-bahr.model';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';

type SelectableEntity = IRefBahr | IRefRhythm;

@Component({
  selector: 'jhi-ref-bahr-detail',
  templateUrl: './ref-bahr-detail.component.html'
})
export class RefBahrDetailComponent implements OnInit {
  groupByName?: Map<string, Array<ITypeTB>> = new Map();
  refBahr: IRefBahr;
  typeTBS: ITypeTB[];
  typesBahrRacine: ITypeTB[];
  aroud: string;
  darb: string;
  links: any;
  totalItems: any;
  itemsPerPage: any = 24;
  page: any = 1;
  previousPage: any;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected typeTBService: TypeTBService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ refBahr }) => {
      this.refBahr = refBahr;
      this.groupBy();
    });
  }

  trackId(index: number, item: SelectableEntity) {
    return item.id;
  }

  previousState() {
    window.history.back();
  }

  groupBy() {
    this.typeTBService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        'refBahrId.equals': this.refBahr.id
      })
      .subscribe(
        (res: HttpResponse<ITypeTB[]>) => this.paginateTypeTBS(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.groupBy();
    }
  }

  protected paginateTypeTBS(data: ITypeTB[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.groupByName = new Map<string, Array<ITypeTB>>();
    data
      .sort((a, b) => (a['ordre'] > b['ordre'] ? 1 : a['ordre'] === b['ordre'] ? 0 : -1))
      .forEach(a => {
        if (!this.groupByName.has(a.code)) {
          this.groupByName.set(a.code, [a]);
        } else {
          const arr: Array<ITypeTB> = this.groupByName.get(a.code);
          arr.push(a);
        }
      });

    this.typesBahrRacine = this.groupByName.get(this.refBahr.code + '-1');
    this.typesBahrRacine.forEach(type => {
      if (type.type === Type.DARB) {
        this.darb = type.refRhythmName;
      }
      if (type.type === Type.AROUD) {
        this.aroud = type.refRhythmName;
      }
    });
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
