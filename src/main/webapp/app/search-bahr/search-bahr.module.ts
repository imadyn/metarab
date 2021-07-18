import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { MetarabSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { SearchBahrComponent } from './search-bahr/search-bahr.component';
import { bahrCombineRoute } from 'app/search-bahr/search-bahr/search-bahr.route';

const ENTITY_STATES = [...bahrCombineRoute];

@NgModule({
  imports: [MetarabSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SearchBahrComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  exports: [SearchBahrComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SearchBahrModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
