import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MetarabSharedModule } from 'app/shared';
import {
  RefAlphabetComponent,
  RefAlphabetDetailComponent,
  RefAlphabetUpdateComponent,
  RefAlphabetDeletePopupComponent,
  RefAlphabetDeleteDialogComponent,
  refAlphabetRoute,
  refAlphabetPopupRoute
} from './';

const ENTITY_STATES = [...refAlphabetRoute, ...refAlphabetPopupRoute];

@NgModule({
  imports: [MetarabSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RefAlphabetComponent,
    RefAlphabetDetailComponent,
    RefAlphabetUpdateComponent,
    RefAlphabetDeleteDialogComponent,
    RefAlphabetDeletePopupComponent
  ],
  entryComponents: [RefAlphabetComponent, RefAlphabetUpdateComponent, RefAlphabetDeleteDialogComponent, RefAlphabetDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetarabRefAlphabetModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
