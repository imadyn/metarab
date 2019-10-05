import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRefBahr } from 'app/shared/model/ref-bahr.model';
import { RefBahrService } from './ref-bahr.service';

@Component({
  selector: 'jhi-ref-bahr-delete-dialog',
  templateUrl: './ref-bahr-delete-dialog.component.html'
})
export class RefBahrDeleteDialogComponent {
  refBahr: IRefBahr;

  constructor(protected refBahrService: RefBahrService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.refBahrService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'refBahrListModification',
        content: 'Deleted an refBahr'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ref-bahr-delete-popup',
  template: ''
})
export class RefBahrDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ refBahr }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(RefBahrDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.refBahr = refBahr;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ref-bahr', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ref-bahr', { outlets: { popup: null } }]);
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
