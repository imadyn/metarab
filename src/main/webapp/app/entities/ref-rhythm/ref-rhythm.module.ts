import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MetarabSharedModule } from 'app/shared';
import {
  RefRhythmComponent,
  RefRhythmDetailComponent,
  RefRhythmUpdateComponent,
  RefRhythmDeletePopupComponent,
  RefRhythmDeleteDialogComponent,
  refRhythmRoute,
  refRhythmPopupRoute
} from './';

const ENTITY_STATES = [...refRhythmRoute, ...refRhythmPopupRoute];

@NgModule({
  imports: [MetarabSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RefRhythmComponent,
    RefRhythmDetailComponent,
    RefRhythmUpdateComponent,
    RefRhythmDeleteDialogComponent,
    RefRhythmDeletePopupComponent
  ],
  entryComponents: [RefRhythmComponent, RefRhythmUpdateComponent, RefRhythmDeleteDialogComponent, RefRhythmDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetarabRefRhythmModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
