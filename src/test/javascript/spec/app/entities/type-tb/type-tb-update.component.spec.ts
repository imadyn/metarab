/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, TestBed, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MetarabTestModule } from '../../../test.module';
import { TypeTBUpdateComponent } from 'app/entities/type-tb/type-tb-update.component';
import { TypeTBService } from 'app/entities/type-tb/type-tb.service';
import { TypeTB } from 'app/shared/model/type-tb.model';

describe('Component Tests', () => {
  describe('TypeTB Management Update Component', () => {
    let comp: TypeTBUpdateComponent;
    let fixture: ComponentFixture<TypeTBUpdateComponent>;
    let service: TypeTBService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [TypeTBUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypeTBUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypeTBUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypeTBService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypeTB(123);
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
        const entity = new TypeTB();
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
