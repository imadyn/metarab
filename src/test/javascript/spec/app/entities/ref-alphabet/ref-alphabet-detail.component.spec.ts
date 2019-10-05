/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MetarabTestModule } from '../../../test.module';
import { RefAlphabetDetailComponent } from 'app/entities/ref-alphabet/ref-alphabet-detail.component';
import { RefAlphabet } from 'app/shared/model/ref-alphabet.model';

describe('Component Tests', () => {
  describe('RefAlphabet Management Detail Component', () => {
    let comp: RefAlphabetDetailComponent;
    let fixture: ComponentFixture<RefAlphabetDetailComponent>;
    const route = ({ data: of({ refAlphabet: new RefAlphabet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MetarabTestModule],
        declarations: [RefAlphabetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RefAlphabetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RefAlphabetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.refAlphabet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
