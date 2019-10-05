import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRefAlphabet } from 'app/shared/model/ref-alphabet.model';
import { RefAlphabetService } from './ref-alphabet.service';

@Component({
  selector: 'jhi-ref-alphabet-delete-dialog',
  templateUrl: './ref-alphabet-delete-dialog.component.html'
})
export class RefAlphabetDeleteDialogComponent {
  refAlphabet: IRefAlphabet;

  constructor(
    protected refAlphabetService: RefAlphabetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.refAlphabetService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'refAlphabetListModification',
        content: 'Deleted an refAlphabet'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ref-alphabet-delete-popup',
  template: ''
})
export class RefAlphabetDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ refAlphabet }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RefAlphabetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.refAlphabet = refAlphabet;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ref-alphabet', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ref-alphabet', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
