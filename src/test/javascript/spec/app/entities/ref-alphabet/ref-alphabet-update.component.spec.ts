/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MetarabTestModule } from '../../../test.module';
import { RefAlphabetUpdateComponent } from 'app/entities/ref-alphabet/ref-alphabet-update.component';
import { RefAlphabetService } from 'app/entities/ref-alphabet/ref-alphabet.service';
import { RefAlphabet } from 'app/shared/model/ref-alphabet.model';

describe('Component Tests', () => {
  describe('RefAlphabet Management Update Component', () => {
    let comp: RefAlphabetUpdateComponent;
    let fixture: ComponentFixture<RefAlphabetUpdateComponent>;
    let service: RefAlphabetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [RefAlphabetUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RefAlphabetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RefAlphabetUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RefAlphabetService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RefAlphabet(123);
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
        const entity = new RefAlphabet();
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
