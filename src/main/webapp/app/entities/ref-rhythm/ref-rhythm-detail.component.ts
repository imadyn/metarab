import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRefRhythm } from 'app/shared/model/ref-rhythm.model';

@Component({
  selector: 'jhi-ref-rhythm-detail',
  templateUrl: './ref-rhythm-detail.component.html'
})
export class RefRhythmDetailComponent implements OnInit {
  refRhythm: IRefRhythm;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ refRhythm }) => {
      this.refRhythm = refRhythm;
    });
  }

  previousState() {
    window.history.back();
  }
}
