import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeTB } from 'app/shared/model/type-tb.model';
import { TypeTBService } from './type-tb.service';

@Component({
  selector: 'jhi-type-tb-delete-dialog',
  templateUrl: './type-tb-delete-dialog.component.html'
})
export class TypeTBDeleteDialogComponent {
  typeTB: ITypeTB;

  constructor(protected typeTBService: TypeTBService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.typeTBService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'typeTBListModification',
        content: 'Deleted an typeTB'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-type-tb-delete-popup',
  template: ''
})
export class TypeTBDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ typeTB }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TypeTBDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.typeTB = typeTB;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/type-tb', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/type-tb', { outlets: { popup: null } }]);
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
