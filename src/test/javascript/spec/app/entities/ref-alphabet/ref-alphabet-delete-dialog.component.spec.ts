/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MetarabTestModule } from '../../../test.module';
import { RefAlphabetDeleteDialogComponent } from 'app/entities/ref-alphabet/ref-alphabet-delete-dialog.component';
import { RefAlphabetService } from 'app/entities/ref-alphabet/ref-alphabet.service';

describe('Component Tests', () => {
  describe('RefAlphabet Management Delete Component', () => {
    let comp: RefAlphabetDeleteDialogComponent;
    let fixture: ComponentFixture<RefAlphabetDeleteDialogComponent>;
    let service: RefAlphabetService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [RefAlphabetDeleteDialogComponent]
      })
        .overrideTemplate(RefAlphabetDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RefAlphabetDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RefAlphabetService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
