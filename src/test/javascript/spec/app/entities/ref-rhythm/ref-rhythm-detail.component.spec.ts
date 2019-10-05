/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MetarabTestModule } from '../../../test.module';
import { RefRhythmDetailComponent } from 'app/entities/ref-rhythm/ref-rhythm-detail.component';
import { RefRhythm } from 'app/shared/model/ref-rhythm.model';

describe('Component Tests', () => {
  describe('RefRhythm Management Detail Component', () => {
    let comp: RefRhythmDetailComponent;
    let fixture: ComponentFixture<RefRhythmDetailComponent>;
    const route = ({ data: of({ refRhythm: new RefRhythm(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [RefRhythmDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RefRhythmDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RefRhythmDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.refRhythm).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
