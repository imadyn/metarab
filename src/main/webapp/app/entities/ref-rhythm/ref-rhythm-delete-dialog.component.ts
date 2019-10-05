import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRefRhythm } from 'app/shared/model/ref-rhythm.model';
import { RefRhythmService } from './ref-rhythm.service';

@Component({
  selector: 'jhi-ref-rhythm-delete-dialog',
  templateUrl: './ref-rhythm-delete-dialog.component.html'
})
export class RefRhythmDeleteDialogComponent {
  refRhythm: IRefRhythm;

  constructor(protected refRhythmService: RefRhythmService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.refRhythmService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'refRhythmListModification',
        content: 'Deleted an refRhythm'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ref-rhythm-delete-popup',
  template: ''
})
export class RefRhythmDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ refRhythm }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RefRhythmDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.refRhythm = refRhythm;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ref-rhythm', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ref-rhythm', { outlets: { popup: null } }]);
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
