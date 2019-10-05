/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MetarabTestModule } from '../../../test.module';
import { RefBahrDetailComponent } from 'app/entities/ref-bahr/ref-bahr-detail.component';
import { RefBahr } from 'app/shared/model/ref-bahr.model';

describe('Component Tests', () => {
  describe('RefBahr Management Detail Component', () => {
    let comp: RefBahrDetailComponent;
    let fixture: ComponentFixture<RefBahrDetailComponent>;
    const route = ({ data: of({ refBahr: new RefBahr(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [RefBahrDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RefBahrDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RefBahrDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.refBahr).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
