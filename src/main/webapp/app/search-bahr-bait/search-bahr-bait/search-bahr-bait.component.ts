import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { BahrBaitSearchCriteria, IBahrBaitSearchCriteria, IBahrCombine } from 'app/shared/model/bahr-combine.model';
import { SearchBahrBaitService } from 'app/search-bahr-bait/search-bahr-bait.service';
import { AccountService } from 'app/core';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { Paragraphe } from 'app/shared/model/poeme/caractere.model';

@Component({
  selector: 'jhi-search-bahr-bait',
  templateUrl: './search-bahr-bait.component.html',
  styleUrls: ['./search-bahr-bait.component.scss']
})
export class SearchBahrBaitComponent implements OnInit {
  currentAccount: any;
  links: any;
  totalItems: any;
  bahrCombines: IBahrCombine[];
  codeBahrs: String[];
  isSearching: boolean;

  searchForm = this.fb.group({
    partie1: [null, [Validators.required, Validators.maxLength(64)]],
    partie2: [null, [Validators.maxLength(64)]]
  });

  constructor(
    private fb: FormBuilder,
    protected jhiAlertService: JhiAlertService,
    protected parseLinks: JhiParseLinks,
    protected accountService: AccountService,
    private searchBahrBaitService: SearchBahrBaitService
  ) {
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
  }

  search() {
  }

  loadAll() {
    if (!this.searchForm.valid) {
      return;
    }

    const paragraphe = new Paragraphe(this.searchForm.get('partie1').value, this.searchForm.get('partie2').value);
    paragraphe.transforms();
    console.log('transform : valeurRhythmPart1 = ' + paragraphe.valeurRhythmPart1);
    console.log('transform : valeurRhythmPart2 = ' + paragraphe.valeurRhythmPart2);

    const bahrBaitSearchCriteria: IBahrBaitSearchCriteria = new BahrBaitSearchCriteria(
      paragraphe.valeurRhythmPart1,
      paragraphe.valeurRhythmPart2
    );

    this.searchBahrBaitService
      .search(bahrBaitSearchCriteria)
      .subscribe(
        (res: HttpResponse<String[]>) => this.paginateBahrCombines(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  private paginateBahrCombines(data: String[], headers: HttpHeaders) {
    this.codeBahrs = data;
    console.log('data fetch bahr : = ' + data);
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
