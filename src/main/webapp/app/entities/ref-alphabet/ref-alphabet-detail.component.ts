import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRefAlphabet } from 'app/shared/model/ref-alphabet.model';

@Component({
  selector: 'jhi-ref-alphabet-detail',
  templateUrl: './ref-alphabet-detail.component.html'
})
export class RefAlphabetDetailComponent implements OnInit {
  refAlphabet: IRefAlphabet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ refAlphabet }) => {
      this.refAlphabet = refAlphabet;
    });
  }

  previousState() {
    window.history.back();
  }
}
