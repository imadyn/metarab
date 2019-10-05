import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRefBahr, RefBahr } from 'app/shared/model/ref-bahr.model';
import { RefBahrService } from './ref-bahr.service';

@Component({
  selector: 'jhi-ref-bahr-update',
  templateUrl: './ref-bahr-update.component.html'
})
export class RefBahrUpdateComponent implements OnInit {
  isSaving: boolean;

  refbahrs: IRefBahr[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(32)]],
    name: [null, [Validators.required, Validators.maxLength(64)]],
    signature: [null, [Validators.required, Validators.maxLength(10)]],
    style: [null, [Validators.required]],
    parentId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected refBahrService: RefBahrService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ refBahr }) => {
      this.updateForm(refBahr);
    });
    this.refBahrService
      .query({page:0, size:100})
      .pipe(
        filter((mayBeOk: HttpResponse<IRefBahr[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRefBahr[]>) => response.body)
      )
      .subscribe((res: IRefBahr[]) => (this.refbahrs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(refBahr: IRefBahr) {
    this.editForm.patchValue({
      id: refBahr.id,
      code: refBahr.code,
      name: refBahr.name,
      signature: refBahr.signature,
      style: refBahr.style,
      parentId: refBahr.parentId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const refBahr = this.createFromForm();
    if (refBahr.id !== undefined) {
      this.subscribeToSaveResponse(this.refBahrService.update(refBahr));
    } else {
      this.subscribeToSaveResponse(this.refBahrService.create(refBahr));
    }
  }

  private createFromForm(): IRefBahr {
    return {
      ...new RefBahr(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      signature: this.editForm.get(['signature']).value,
      style: this.editForm.get(['style']).value,
      parentId: this.editForm.get(['parentId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRefBahr>>) {
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
}
