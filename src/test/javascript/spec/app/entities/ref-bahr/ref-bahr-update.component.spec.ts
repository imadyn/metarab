/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MetarabTestModule } from '../../../test.module';
import { RefBahrUpdateComponent } from 'app/entities/ref-bahr/ref-bahr-update.component';
import { RefBahrService } from 'app/entities/ref-bahr/ref-bahr.service';
import { RefBahr } from 'app/shared/model/ref-bahr.model';

describe('Component Tests', () => {
  describe('RefBahr Management Update Component', () => {
    let comp: RefBahrUpdateComponent;
    let fixture: ComponentFixture<RefBahrUpdateComponent>;
    let service: RefBahrService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [RefBahrUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RefBahrUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RefBahrUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RefBahrService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RefBahr(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RefBahr();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
