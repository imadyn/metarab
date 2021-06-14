/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { MetarabTestModule } from '../../../test.module';
import { TypeTBDeleteDialogComponent } from 'app/entities/type-tb/type-tb-delete-dialog.component';
import { TypeTBService } from 'app/entities/type-tb/type-tb.service';

describe('Component Tests', () => {
  describe('TypeTB Management Delete Component', () => {
    let comp: TypeTBDeleteDialogComponent;
    let fixture: ComponentFixture<TypeTBDeleteDialogComponent>;
    let service: TypeTBService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [TypeTBDeleteDialogComponent]
      })
        .overrideTemplate(TypeTBDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeTBDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeTBService);
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
