import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MetarabSharedModule } from 'app/shared';
import {
  RefBahrComponent,
  RefBahrDetailComponent,
  RefBahrUpdateComponent,
  RefBahrDeletePopupComponent,
  RefBahrDeleteDialogComponent,
  refBahrRoute,
  refBahrPopupRoute
} from './';

const ENTITY_STATES = [...refBahrRoute, ...refBahrPopupRoute];

@NgModule({
  imports: [MetarabSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RefBahrComponent,
    RefBahrDetailComponent,
    RefBahrUpdateComponent,
    RefBahrDeleteDialogComponent,
    RefBahrDeletePopupComponent
  ],
  entryComponents: [RefBahrComponent, RefBahrUpdateComponent, RefBahrDeleteDialogComponent, RefBahrDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetarabRefBahrModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
