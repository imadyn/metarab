import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRefAlphabet, RefAlphabet } from 'app/shared/model/ref-alphabet.model';
import { RefAlphabetService } from './ref-alphabet.service';

@Component({
  selector: 'jhi-ref-alphabet-update',
  templateUrl: './ref-alphabet-update.component.html'
})
export class RefAlphabetUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(32)]],
    name: [null, [Validators.required, Validators.maxLength(64)]],
    rhythm: [null, [Validators.required, Validators.maxLength(3)]],
    language: []
  });

  constructor(protected refAlphabetService: RefAlphabetService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ refAlphabet }) => {
      this.updateForm(refAlphabet);
    });
  }

  updateForm(refAlphabet: IRefAlphabet) {
    this.editForm.patchValue({
      id: refAlphabet.id,
      code: refAlphabet.code,
      name: refAlphabet.name,
      rhythm: refAlphabet.rhythm,
      language: refAlphabet.language
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const refAlphabet = this.createFromForm();
    if (refAlphabet.id !== undefined) {
      this.subscribeToSaveResponse(this.refAlphabetService.update(refAlphabet));
    } else {
      this.subscribeToSaveResponse(this.refAlphabetService.create(refAlphabet));
    }
  }

  private createFromForm(): IRefAlphabet {
    return {
      ...new RefAlphabet(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      name: this.editForm.get(['name']).value,
      rhythm: this.editForm.get(['rhythm']).value,
      language: this.editForm.get(['language']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRefAlphabet>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
