/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MetarabTestModule } from '../../../test.module';
import { RefRhythmUpdateComponent } from 'app/entities/ref-rhythm/ref-rhythm-update.component';
import { RefRhythmService } from 'app/entities/ref-rhythm/ref-rhythm.service';
import { RefRhythm } from 'app/shared/model/ref-rhythm.model';

describe('Component Tests', () => {
  describe('RefRhythm Management Update Component', () => {
    let comp: RefRhythmUpdateComponent;
    let fixture: ComponentFixture<RefRhythmUpdateComponent>;
    let service: RefRhythmService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [RefRhythmUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RefRhythmUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RefRhythmUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RefRhythmService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RefRhythm(123);
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
        const entity = new RefRhythm();
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
