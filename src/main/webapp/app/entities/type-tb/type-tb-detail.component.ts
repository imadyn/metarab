import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypeTB } from 'app/shared/model/type-tb.model';

@Component({
  selector: 'jhi-type-tb-detail',
  templateUrl: './type-tb-detail.component.html'
})
export class TypeTBDetailComponent implements OnInit {
  typeTB: ITypeTB;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ typeTB }) => {
      this.typeTB = typeTB;
    });
  }

  previousState() {
    window.history.back();
  }
}
