import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRefBahr } from 'app/shared/model/ref-bahr.model';

@Component({
  selector: 'jhi-ref-bahr-detail',
  templateUrl: './ref-bahr-detail.component.html'
})
export class RefBahrDetailComponent implements OnInit {
  refBahr: IRefBahr;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ refBahr }) => {
      this.refBahr = refBahr;
    });
  }

  previousState() {
    window.history.back();
  }
}
