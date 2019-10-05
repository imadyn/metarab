/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MetarabTestModule } from '../../../test.module';
import { RefBahrDeleteDialogComponent } from 'app/entities/ref-bahr/ref-bahr-delete-dialog.component';
import { RefBahrService } from 'app/entities/ref-bahr/ref-bahr.service';

describe('Component Tests', () => {
  describe('RefBahr Management Delete Component', () => {
    let comp: RefBahrDeleteDialogComponent;
    let fixture: ComponentFixture<RefBahrDeleteDialogComponent>;
    let service: RefBahrService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [RefBahrDeleteDialogComponent]
      })
        .overrideTemplate(RefBahrDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RefBahrDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RefBahrService);
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
