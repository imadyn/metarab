import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITypeTB, TypeTB } from 'app/shared/model/type-tb.model';
import { TypeTBService } from './type-tb.service';
import { IRefBahr } from 'app/shared/model/ref-bahr.model';
import { RefBahrService } from 'app/entities/ref-bahr';
import { IRefRhythm } from 'app/shared/model/ref-rhythm.model';
import { RefRhythmService } from 'app/entities/ref-rhythm';

@Component({
  selector: 'jhi-type-tb-update',
  templateUrl: './type-tb-update.component.html'
})
export class TypeTBUpdateComponent implements OnInit {
  isSaving: boolean;

  refbahrs: IRefBahr[];

  refrhythms: IRefRhythm[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(32)]],
    ordre: [null, [Validators.required, Validators.maxLength(1)]],
    type: [],
    refBahrId: [],
    refRhythmId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected typeTBService: TypeTBService,
    protected refBahrService: RefBahrService,
    protected refRhythmService: RefRhythmService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ typeTB }) => {
      this.updateForm(typeTB);
    });
    this.refBahrService
      .query({ page: 0, size: 100 })
      .pipe(
        filter((mayBeOk: HttpResponse<IRefBahr[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRefBahr[]>) => response.body)
      )
      .subscribe((res: IRefBahr[]) => (this.refbahrs = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.refRhythmService
      .query({ page: 0, size: 100 })
      .pipe(
        filter((mayBeOk: HttpResponse<IRefRhythm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRefRhythm[]>) => response.body)
      )
      .subscribe((res: IRefRhythm[]) => (this.refrhythms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(typeTB: ITypeTB) {
    this.editForm.patchValue({
      id: typeTB.id,
      code: typeTB.code,
      ordre: typeTB.ordre,
      type: typeTB.type,
      refBahrId: typeTB.refBahrId,
      refRhythmId: typeTB.refRhythmId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const typeTB = this.createFromForm();
    if (typeTB.id !== undefined) {
      this.subscribeToSaveResponse(this.typeTBService.update(typeTB));
    } else {
      this.subscribeToSaveResponse(this.typeTBService.create(typeTB));
    }
  }

  private createFromForm(): ITypeTB {
    return {
      ...new TypeTB(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      ordre: this.editForm.get(['ordre']).value,
      type: this.editForm.get(['type']).value,
      refBahrId: this.editForm.get(['refBahrId']).value,
      refRhythmId: this.editForm.get(['refRhythmId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeTB>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackRefBahrById(index: number, item: IRefBahr) {
    return item.id;
  }

  trackRefRhythmById(index: number, item: IRefRhythm) {
    return item.id;
  }
}
