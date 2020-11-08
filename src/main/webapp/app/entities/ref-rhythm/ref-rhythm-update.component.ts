import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRefRhythm, RefRhythm } from 'app/shared/model/ref-rhythm.model';
import { RefRhythmService } from './ref-rhythm.service';

@Component({
  selector: 'jhi-ref-rhythm-update',
  templateUrl: './ref-rhythm-update.component.html'
})
export class RefRhythmUpdateComponent implements OnInit {
  isSaving: boolean;

  refrhythms: IRefRhythm[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(32)]],
    name: [null, [Validators.required, Validators.maxLength(64)]],
    valeur: [null, [Validators.required, Validators.maxLength(100)]],
    transform: [null, [Validators.required]],
    parentId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected refRhythmService: RefRhythmService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ refRhythm }) => {
      this.updateForm(refRhythm);
    });
    this.refRhythmService
      .query({ page: 0, size: 100 })
      .pipe(
        filter((mayBeOk: HttpResponse<IRefRhythm[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRefRhythm[]>) => response.body)
      )
      .subscribe((res: IRefRhythm[]) => (this.refrhythms = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(refRhythm: IRefRhythm) {
    this.editForm.patchValue({
      id: refRhythm.id,
      code: refRhythm.code,
      name: refRhythm.name,
      valeur: refRhythm.valeur,
      transform: refRhythm.transform,
      parentId: refRhythm.parentId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const refRhythm = this.createFromForm();
    if (refRhythm.id !== undefined) {
      this.subscribeToSaveResponse(this.refRhythmService.update(refRhythm));
    } else {
      this.subscribeToSaveResponse(this.refRhythmService.create(refRhythm));
    }
  }

  private createFromForm(): IRefRhythm {
    return {
      ...new RefRhythm(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      valeur: this.editForm.get(['valeur']).value,
      transform: this.editForm.get(['transform']).value,
      parentId: this.editForm.get(['parentId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRefRhythm>>) {
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

  trackRefRhythmById(index: number, item: IRefRhythm) {
    return item.id;
  }
}
