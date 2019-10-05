/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MetarabTestModule } from '../../../test.module';
import { TypeTBDetailComponent } from 'app/entities/type-tb/type-tb-detail.component';
import { TypeTB } from 'app/shared/model/type-tb.model';

describe('Component Tests', () => {
  describe('TypeTB Management Detail Component', () => {
    let comp: TypeTBDetailComponent;
    let fixture: ComponentFixture<TypeTBDetailComponent>;
    const route = ({ data: of({ typeTB: new TypeTB(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [TypeTBDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypeTBDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypeTBDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typeTB).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
