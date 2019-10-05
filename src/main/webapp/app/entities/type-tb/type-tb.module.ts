import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { MetarabSharedModule } from 'app/shared';
import {
  TypeTBComponent,
  TypeTBDetailComponent,
  TypeTBUpdateComponent,
  TypeTBDeletePopupComponent,
  TypeTBDeleteDialogComponent,
  typeTBRoute,
  typeTBPopupRoute
} from './';

const ENTITY_STATES = [...typeTBRoute, ...typeTBPopupRoute];

@NgModule({
  imports: [MetarabSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TypeTBComponent, TypeTBDetailComponent, TypeTBUpdateComponent, TypeTBDeleteDialogComponent, TypeTBDeletePopupComponent],
  entryComponents: [TypeTBComponent, TypeTBUpdateComponent, TypeTBDeleteDialogComponent, TypeTBDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MetarabTypeTBModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
