import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BahrBaitSearchCriteria, IBahrBaitSearchCriteria, IBahrCombine } from 'app/shared/model/bahr-combine.model';
import { SearchBahrBaitService } from 'app/search-bahr-bait/search-bahr-bait.service';
import { AccountService } from 'app/core';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { Paragraphe } from 'app/shared/model/poeme/caractere.model';
import { IRefRhythm } from 'app/shared/model/ref-rhythm.model';
import { RefRhythmService } from 'app/entities/ref-rhythm';

@Component({
  selector: 'jhi-search-bahr',
  templateUrl: './search-bahr.component.html',
  styleUrls: ['./search-bahr.component.scss']
})
export class SearchBahrComponent implements OnInit {
  currentAccount: any;
  links: any;
  totalItems: any;
  bahrCombines: IBahrCombine[];
  codeBahrs: String[];
  refRhythms: IRefRhythm[];
  selectedRhythm: IRefRhythm;
  predicate: any;
  reverse: any;
  isSearching: boolean;

  refBahrListMap: Map<number, IRefRhythm> = new Map<number, IRefRhythm>();

  searchForm = this.fb.group({
    pas1: '',
    pas2: '',
    pas3: '',
    pas4: '',
    pas5: '',
    pas6: '',
    pas7: '',
    pas8: ''
  });

  constructor(
    private fb: FormBuilder,
    protected jhiAlertService: JhiAlertService,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    private searchBahrBaitService: SearchBahrBaitService,
    private refRhythmService: RefRhythmService
  ) {}

  ngOnInit() {
    this.loadRhythms();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
  }
  clear() {
    this.codeBahrs = null;
    this.searchForm = this.fb.group({
      pas1: [null],
      pas2: [null],
      pas3: [null],
      pas4: [null],
      pas5: [null],
      pas6: [null],
      pas7: [null],
      pas8: [null]
    });
  }

  onChoise(position: number) {
    console.log('Tafila : valeur Rhythm  = ' + this.selectedRhythm.valeur);
    this.refBahrListMap.set(position, this.selectedRhythm);
    console.log('Tafila : indice Rhythm in map = ' + this.refBahrListMap.get(position));
    this.selectedRhythm = null;
  }

  loadRhythms() {
    this.refRhythmService
      .query({
        page: 0,
        size: 50,
        sort: ['asc', 'id']
      })
      .subscribe(
        (res: HttpResponse<IRefRhythm[]>) => this.paginateRefRhythms(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  private paginateBahrCombines(data: String[], headers: HttpHeaders) {
    this.codeBahrs = data;
    console.log('data fetch bahr : = ' + data);
  }

  private paginateRefRhythms(data: IRefRhythm[], headers: HttpHeaders) {
    this.refRhythms = data;
    console.log('Referential Rhythms : = ' + data);
  }

  searchByRhythms() {
    if (this.refBahrListMap && this.refBahrListMap.size > 1) {
      let rhythmBahr = '';
      this.refBahrListMap.forEach(r => (rhythmBahr = r.valeur.concat(rhythmBahr)));
      console.log('Rhythm Bahr : = ' + rhythmBahr);
      this.searchBahrBaitService
        .searchByBayt(rhythmBahr)
        .subscribe((res: HttpResponse<String[]>) => (this.codeBahrs = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    }
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
